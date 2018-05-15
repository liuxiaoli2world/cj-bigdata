package com.lgsc.cjbd.remote.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.ApiParam;

@FeignClient(name = "cjbd-user", fallback = UserClientFallback.class)
public interface UserClient {

	/**
	 * 删除用户图书关系
	 * 
	 * @param bookDirId
	 * @return
	 */
	@RequestMapping(value = "/user/userbook/removeByBookDirId", method = RequestMethod.GET)
	Integer deleteUserBookByBookDirId(@RequestParam("bookDirId") Long bookDirId);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/user/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	Response delete(@PathVariable("id") Long id);

	/**
	 * 根据userId删除用户角色关系
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/userrole/selectRoleByuserId", method = RequestMethod.POST)
	Response deleteRoleByuserId(@RequestParam("userId") Long userId);

	/**
	 * 删除用户图书关系
	 * 
	 * @param isbn
	 * @return
	 */
	@RequestMapping(value = "/user/userbook/removeByIsbn", method = RequestMethod.GET)
	Integer deleteUserBookByIsbn(@RequestParam("isbn") String isbn);

	/**
	 * 删除用户书签
	 * 
	 * @param bookSectionId
	 * @return
	 */
	@RequestMapping(value = "/user/bookmark/removeBySectionId", method = RequestMethod.GET)
	Integer deleteBookMarkByBookSectionId(@RequestParam("bookSectionId") Long bookSectionId);

	/**
	 * 删除用户书签
	 * 
	 * @param isbn
	 * @return
	 */
	@RequestMapping(value = "/user/bookmark/removeByIsbn", method = RequestMethod.GET)
	Integer deleteBookMarkByIsbn(@RequestParam("isbn") String isbn);

	/**
	 * 注册专家学者
	 */
	@RequestMapping(value = "/user/user/registExpert", method = RequestMethod.POST)
	Long registExpert(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("realName") String realName);

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/user/user/selectByUserName", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	Response selectUserByUserName(@RequestParam("username") String username);

	/**
	 * 新增专家学者用户关系
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/userrole/insertExpertRole", method = RequestMethod.POST)
	Response insertExpertRole(@RequestParam("userId") Long userId);

	/**
	 * 修改专家账号信息
	 * 
	 * @param userId
	 * @param username
	 * @param password
	 * @param realName
	 * @return
	 */
	@RequestMapping(value = "/user/user/updateExpert", method = RequestMethod.POST)
	Response updateExpert(@RequestParam("userId") Long userId, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("realName") String realName);

	/**
	 * 查询用户一些信息
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "/user/user/querySome", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Map<String, Object> selectSome(@RequestParam("userId") Long userId);

	/**
	 * 查询会员商品详细
	 */
	@RequestMapping(value = "/user/vip/queryDetail", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Map<String, Object> selectDetail(@RequestParam("vipId") Long vipId);
	/**
	 * 通过用户id查询用户关系
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/userrole/selectUserVipByUserId/{userId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectUserVipByUserId(@PathVariable("userId")Long userId);
	/**
	 * 通过用户id查询对应的权限
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/allow/findByUserId/{userId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response findByUserId(@ApiParam(value = "用户id", required = true) @PathVariable("userId") Long id);
	/**
	 * 查询需要鉴权的api
	 * @return
	 */
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll();
	
	/**
	 * 添加或修改会员到期时间
	 * @param userId 用户id
	 * @param vipId vipId
	 * @param beginDate 开始时间
	 * @param endDate 到期时间
	 * @param duration 购买时长
	 * @return 是否成功
	 */
	@RequestMapping(value = "/user/uservip/addOrModifyUserVip", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Boolean insertOrUpdateUserVip(
			@RequestParam("userId") Long userId, 
			@RequestParam("vipId") Long vipId, 
			@RequestParam("beginDate") String beginDate, 
			@RequestParam("endDate") String endDate, 
			@RequestParam("duration") Integer duration
	);
	
	/**
	 * 批量添加用户图书商品关系
	 */
	@RequestMapping(value = "/user/userbook/batchAddUserBook", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Boolean batchInsertUserBook(@RequestBody List<Map<String, Object>> list);
	
	/**
	 * 查询用户已购买的根目录
	 * 
	 * @param isbn
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/userbook/queryRootDirsOfUser", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectRootDirsOfUser(@RequestParam("isbn") String isbn, @RequestParam("userId") Long userId);
	
	/**
	 * 根据用户id查询用户会员状态
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/uservip/selectVipStatus", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public String selectVipStatus(@RequestParam(value = "userId") Long userId);
	
	/**
	 * 根据用户id查询用户角色状态
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/userrole/selectRoleStatus", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<String> selectRoleStatus(@RequestParam(value = "userId") Long userId);
	

}
