package com.lgsc.cjbd.user.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lgsc.cjbd.user.model.User;
import com.lgsc.cjbd.user.service.JwtUserDetailService;
import com.lgsc.cjbd.user.service.UserService;
import com.lgsc.cjbd.user.util.JWTTokeUtil;
import com.lgsc.cjbd.user.util.MailUtil;
import com.lgsc.cjbd.vo.Response;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户
 * 
 * @author
 */
@Api(tags = "user", description = "用户")
@RestController
@RequestMapping("/user/user")
public class UserController {

	public static final Logger logger = LogManager.getLogger(UserController.class);

	@Value("${spring.mail.username}")
	private String sendFrom;         

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserService userService;

	@Autowired
	private JWTTokeUtil jwtTokenUtils;
	
	@Autowired
	private JwtUserDetailService jwtUserDetailService;

	/**
	 * 根据id查询用户详细信息
	 */
	@ApiOperation(value = "查询用户详细信息")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "用户的id", required = true) @PathVariable Long id) {
		return new Response().success(userService.selectById(id));
	}

	/**
	 * 根据id查询用户用户名邮箱(for client)
	 */
	@RequestMapping(value = "/querySome", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Map<String, Object> selectSome(@RequestParam("userId") Long userId) {
		return userService.selectSomeById(userId);
	}

	/**
	 * 根据id查询用户登录详细信息
	 */
	@ApiOperation(value = "查询用户登录详细信息")
	@RequestMapping(value = "/queryLoginRecord/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectLoginRecord(@ApiParam(value = "用户的id", required = true) @PathVariable Long id) {

		return new Response().success(userService.selectUserLoginDto(id));
	}

	/**
	 * 
	 * 分页查询所有用户
	 * 
	 */
	@ApiOperation(value = "后台分页查询所有用户信息")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(userService.selectAll(pageNum, pageSize));
	}

	@ApiOperation(value = "后台条件查询用户")
	@RequestMapping(value = "/queryBy", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectBy(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "账号状态 0=不可用 1=可用", required = false) @RequestParam(name = "enable", required = false) Short enable,
			@ApiParam(value = "会员状态 0=普通用户 1=会员 2=过期会员 ", required = false) @RequestParam(name = "vipStatus", required = false) Short vipStatus,
			@ApiParam(value = "机构名称", required = false) @RequestParam(name = "organization", required = false) String organization,
			@ApiParam(value="用户名",required = false) @RequestParam(name="username",required=false)String username) {
		return new Response().success(userService.selectBy(pageNum, pageSize, enable, vipStatus, organization,username));
	}

	/**
	 * 用户注册
	 */
	@ApiOperation(value = "用户注册")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "用户", required = true) @RequestBody @Validated User user) {
		Response response = new Response();
		Map<String, String> map = userService.insertSelective(user);
		if (!map.containsKey("error")) {
			response.success();

		} else {
			response.failure("注册失败" + map.get("error"));
		}
		return response;
	}

	/**
	 * 专家学者注册
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "专家学者注册")
	@RequestMapping(value = "/registExpert", method = RequestMethod.POST)
	public Long registExpert(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("realName") String realName) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRealName(realName);
		userService.insertSelective(user);
		Long userId = user.getUserId();
		return userId;
	}

	/**
	 * 发邮件(暂时不使用)
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "发邮件(暂时不使用)")
	@RequestMapping(value = "/sendEmail/{id}", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response sendEmail(@PathVariable("userId") Long id) {
		User user = userService.selectByPrimaryKey(id);
		if (user != null) {
			String content = "您可以点击一下链接激活您的账户<br/>http://localost:7106/user/user/activeUser/" + id;
			try {
				new MailUtil(sendFrom, javaMailSender).SendMail("用户激活", content, user.getEmail());
				return new Response().success();
			} catch (MessagingException e) {
				e.printStackTrace();
				return new Response().failure();
			}

		} else {
			return new Response().failure();
		}

	}

	/**
	 * 修改用户信息
	 */
	@ApiOperation(value = "修改用户信息(暂时不使用)")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(@ApiParam(value = "用户", required = true) @RequestBody @Validated User user) {
		Response response = new Response();
		Map<String, String> map = userService.updateByPrimaryKeySelective(user);
		if (map.containsKey("error")) {
			response.failure(map.get("error"));
		} else {
			response.success();
		}
		return response;
	}

	/**
	 * 修改专家账号信息
	 */
	@ApiOperation(value = "修改专家账号信息")
	@RequestMapping(value = "/updateExpert", method = RequestMethod.POST)
	public Response updateExpert(@RequestParam("userId") Long userId, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("realName") String realName) {
		Response response = new Response();
		int num = userService.updateExpert(userId, username, password, realName);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 删除用户
	 */
	@ApiOperation(value = "删除用户")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "用户编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = userService.deleteByPrimaryKey(id);
		if (num > 0) {
			return response.success();
		}
		return response.failure();

	}

	@ApiOperation(value = "激活用户")
	@RequestMapping(value = "/activeUser/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response activeUser(@ApiParam(value = "用户的编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = userService.enableUser(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	@ApiOperation(value = "封禁用户")
	@RequestMapping(value = "/unactiveUser/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response unActiveUser(@ApiParam(value = "用户的编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = userService.disableUser(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	@ApiOperation(value = "用户登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces = "application/json; charset=UTF-8")
	public Response login(@ApiParam(value = "用户名或者邮箱", required = true) @RequestParam("searchStr") String searchStr,
			@ApiParam(value = "密码", required = true) @RequestParam("password") String password,
			@ApiParam(value = "前台(index)或者后台(admin)", required = true) @RequestParam("from") String from) {
		Response response = new Response();
		Map<String, String> map = userService.login(searchStr, password, from);
		if (!map.containsKey("error")) {
			if(from.equals("index")){
				return response.success(map.get("index_accsess_token"));
			}else{
				return response.success(map.get("bg_accsess_token"));
			}
			
		} else {
			return response.failure(map.get("error"));
		}

	}

	@ApiOperation(value = "判断用户名是否不存在")
	@RequestMapping(value = "/selectByUserName", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectUserByUserName(@RequestParam("username") String username) {
		Response response = new Response();
		User user = userService.selectByUserName(username);
		if (user != null) {
			return response.failure("用户名存在");
		}
		return response.success();
	}

	@ApiOperation(value = "判断邮箱是否存在")
	@RequestMapping(value = "/selectByEmail", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectUserByEmail(@RequestParam("email") String email) {
		Response response = new Response();
		User user = userService.selectByEmail(email);
		if (user != null) {
			return response.failure("邮箱存在");
		}
		return response.success();
	}

	@ApiOperation(value = "判断手机是否存在")
	@RequestMapping(value = "/selectByphone", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectUserByPhone(@RequestParam("phone") Long phone) {
		Response response = new Response();
		User user = userService.selectByPhone(phone);
		if (user != null) {
			return response.failure("手机已经存在");
		}
		return response.success();
	}

	@ApiOperation(value = "生成验证码")
	@RequestMapping(value = "/getcaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getCaptcha(HttpServletResponse response) {
		// 生成验证码
		GradiatedBackgroundProducer gbp = new GradiatedBackgroundProducer();
		gbp.setFromColor(Color.BLUE);
		gbp.setToColor(Color.WHITE);
		Captcha captcha = new Captcha.Builder(120, 60).addBorder().addText().addBackground(gbp).build();
		// 将验证码key，及验证码的图片返回
		Cookie cookie = new Cookie("CaptchaCode", captcha.getAnswer());
		response.addCookie(cookie);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			ImageIO.write(captcha.getImage(), "png", bao);
			return bao.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	@ApiOperation(value = "验证验证码是否正确")
	@RequestMapping(value = "/checkcode", method = RequestMethod.GET, produces = "application/json")
	public Response CheckCode(HttpServletRequest request, String code) {
		Response response = new Response();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("CaptchaCode")) {
				if (cookie.getValue().equals(code)) {
					return response.success();
				}
			}
		}
		return response.failure();
	}

	/**
	 * 取消或设置质检员
	 * 
	 * @return
	 */
	@ApiOperation(value = "取消或设置质检员，返回值为更改后是否为质检员 0=否 1=是")
	@RequestMapping(value = "/modifyInspector", method = RequestMethod.POST, produces = "application/json")
	public Response setInspector(@ApiParam(value = "用户id", required = true) @RequestParam("id") Long id) {
		return new Response().success(userService.setInspector(id));
	}

	/**
	 * 首页展示一周粉丝增长
	 * 
	 * @return
	 */
	@ApiOperation(value = "首页展示一周粉丝增长")
	@RequestMapping(value = "/selectUserForIndex", method = RequestMethod.GET, produces = "application/json")
	public Response selectUserForIndex() {
		return new Response().success(userService.selectUserForIndex());
	}

	/**
	 * 用户个人中心
	 * 
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "用户个人中心")
	@RequestMapping(value = "/selectPersonalCenter/{userId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectPersonalCenter(@ApiParam(value = "用户的id", required = true) @PathVariable Long userId) {
		return new Response().success(userService.selectPersonalCenter(userId));
	}

	/**
	 * 查询机构用户详细
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询机构用户详细")
	@RequestMapping(value = "/selectUserOrganization", method = RequestMethod.GET, produces = "application/json")
	public Response selectUserOrganization(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "机构代号", required = true) @RequestParam("sqeNum") Integer sqe_num) {
		return new Response().success(userService.selectUserOrganization(pageNum, pageSize, sqe_num));
	}

	/**
	 * 批量设置会员
	 * 
	 * @return
	 */
	@ApiOperation(value = "批量设置会员")
	@RequestMapping(value = "/insertVipBatch", method = RequestMethod.GET, produces = "application/json")
	public Response insertVipBatch(@ApiParam("month") @RequestParam Integer month,
			@ApiParam(value = "用户id数组用逗号分开(1,2,3)", required = true) @RequestParam String ids) {
		String[] id = ids.split(",");
		for (String string : id) {
			Long userId = Long.parseLong(string);
			userService.insertVipBatch(userId, month);
		}
		return new Response().success();
	}

	/**
	 * 个人中心修改密码
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@ApiOperation(value = "个人中心修改密码")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET, produces = "application/json")
	public Response updatePassword(
			@ApiParam(value = "用户编号", required = true) @RequestParam(name = "userId", required = true) Long userId,
			@ApiParam(value = "旧密码", required = true) @RequestParam(name = "oldPassword", required = true) String oldPassword,
			@ApiParam(value = "新密码", required = true) @RequestParam(name = "newPassword") String newPassword) {
		Map<String, String> map = userService.updatepassword(userId, oldPassword, newPassword);
		if (map.containsKey("error")) {
			return new Response().failure(map.get("error"));
		} else {
			return new Response().success();
		}
	}

	/**
	 * 修改用户名或者邮箱
	 * 
	 * @return
	 */
	@ApiOperation(value = "个人中心修改用户信息")
	@RequestMapping(value = "/updateUserNameorEmail", method = RequestMethod.GET, produces = "application/json")
	public Response updateUserNameorEmail(
			@ApiParam(value = "用户编号", required = true) @RequestParam(name = "userId", required = true) Long userId,
			@ApiParam(value = "用户名", required = true) @RequestParam(name = "username", required = true) String username,
			@ApiParam(value = "邮箱", required = true) @RequestParam(name = "email") String email) {
		Map<String, String> map = userService.updateUserNameOrEmail(userId, username, email);
		if (map.containsKey("error")) {
			return new Response().failure(map.get("error"));
		} else {
			return new Response().success();
		}

	}

	/**
	 * 查询机构前缀
	 * 
	 * @param SeqNum
	 * @return
	 */
	@ApiOperation(value = "查询机构前缀")
	@RequestMapping(value = "/selectPreNumById", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectPreNumById(
			@ApiParam(value = "机构编号", required = true) @RequestParam(value = "sqeNum") Integer id) {
		Response response = new Response();
		List<User> user = userService.selectBySeqNum(id);
		if (user != null && user.size() != 0) {
			String userName = user.get(0).getUsername();
			String[] temps = userName.split("_");
			return response.success(temps[0]);
		} else {
			return response.failure();

		}
	}
    /**
     * 判断token 是否可用
     * @param token
     * @param id
     * @return
     */
	@ApiOperation(value="判断Token是否可用")
	@RequestMapping(value = "/valiToken", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response  valiToken(@ApiParam(value = "来自", required = true) @RequestParam(value = "from") String from,@ApiParam(value = "token", required = true) @RequestParam(value = "token") String token,@ApiParam(value = "用户id", required = true) @RequestParam(value = "id") Long id){
		User user = userService.selectByPrimaryKey(id);
		boolean isSuccess =  jwtTokenUtils.validateToken(from,token, jwtUserDetailService.loadUserByUsername(user.getUsername()));
		if(isSuccess){
			return new Response().success();
		}else{
			return new Response().failure();
		}
	}
	@RequestMapping(value = "/selectTokenCreatedAtByUserId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Date selectTokenCreatedAtByUserId(@RequestParam(value = "id") Long id){
		User user = userService.selectByPrimaryKey(id);
		return user.getTokenCreatedAt();
	}
	
	
	
}
