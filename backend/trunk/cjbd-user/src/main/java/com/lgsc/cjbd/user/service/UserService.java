package com.lgsc.cjbd.user.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.remote.client.ExpertClient;
import com.lgsc.cjbd.user.dto.UserIndexDto;
import com.lgsc.cjbd.user.dto.UserLoginDto;
import com.lgsc.cjbd.user.dto.UserManagementDto;
import com.lgsc.cjbd.user.dto.UserOrganizationDto;
import com.lgsc.cjbd.user.dto.UserResponseDto;
import com.lgsc.cjbd.user.mapper.BookmarkMapper;
import com.lgsc.cjbd.user.mapper.LoginRecordMapper;
import com.lgsc.cjbd.user.mapper.RoleMapper;
import com.lgsc.cjbd.user.mapper.UserBookMapper;
import com.lgsc.cjbd.user.mapper.UserMapper;
import com.lgsc.cjbd.user.mapper.UserRoleMapper;
import com.lgsc.cjbd.user.mapper.UserVipMapper;
import com.lgsc.cjbd.user.model.Bookmark;
import com.lgsc.cjbd.user.model.LoginRecord;
import com.lgsc.cjbd.user.model.Role;
import com.lgsc.cjbd.user.model.User;
import com.lgsc.cjbd.user.model.UserBook;
import com.lgsc.cjbd.user.model.UserRole;
import com.lgsc.cjbd.user.model.UserVip;
import com.lgsc.cjbd.user.util.AddressUtils;
import com.lgsc.cjbd.user.util.JWTTokeUtil;
import com.lgsc.cjbd.user.util.MD5Util;
import com.lgsc.cjbd.user.util.MailUtil;

/**
 * 用户服务
 */
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JWTTokeUtil jwtTokeUtil;

	@Autowired
	private LoginRecordMapper loginRecordMapper;

	@Autowired
	private UserBookMapper userBookMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private UserVipMapper userVipMapper;

	@Autowired
	private BookmarkMapper bookmarkMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private JwtUserDetailService jwtUserDetailService;

	@Autowired
	private ExceptionLoginService exceptionLoginService;

	@Autowired
	private ExpertClient expertClient;

	@Value("${spring.mail.username}")
	private String sendFrom;
	
	@Value("${reset.password.url}")
	private String resetPasswordUrl;

	@Autowired
	private JavaMailSender javaMailSender;

	private static final Logger logger = LogManager.getLogger(UserService.class);

	private static final Integer IS_INSPECTOER = 1;
	private static final Integer IS_NOT_INSPECTOER = 0;

	public int insert(User record) {
		return userMapper.insert(record);
	}

	/**
	 * 用户注册的方法
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> insertSelective(User user) {
		User record = userMapper.findUserByTag(user.getUsername());
		Map<String, String> map = new HashMap<>();
		if (record != null && record.getUsername().equals(user.getUsername())) {
			map.put("error", "用户名存在了哦！");
			return map;
		} else {
			if (user.getEmail() != null && user.getEmail() != " ") {
				record = userMapper.findUserByTag(user.getEmail());
				if (record != null) {
					map.put("error", "邮箱存在了");
					return map;
				}
			}
			user.setEnable(Short.parseShort("1"));
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
			user.setSalt(MD5Util.MD5By32(salt));
			user.setPassword(MD5Util.MD5By32(user.getPassword() + user.getSalt()));
			userMapper.insertSelective(user);
			return map;
		}

	}

	public UserResponseDto selectById(long id) {

		return userMapper.selectById(id);
	}

	public PageInfo<UserManagementDto> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserManagementDto> list = userMapper.selectAllUserManagementDTO();
		PageInfo<UserManagementDto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public UserLoginDto selectUserLoginDto(Long userId) {
		return userMapper.selectUserLoginDto(userId);
	}

	public int updateSelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 禁用用户
	 */
	@Transactional(rollbackFor = Exception.class)
	public int disableUser(long userId) {
		if (exceptionLoginService.IfSendEmail() > 0) {
			User record = userMapper.selectByPrimaryKey(userId);
			try {
				new MailUtil(sendFrom, javaMailSender).SendMail("长江大数据", "您异常登录的ip过多账号被封了", record.getEmail());
			} catch (Exception e) {
				logger.error(e);
			}
		}

		User user = new User();
		user.setUserId(userId);
		user.setEnable((short) 0);
		user.setUpdatedAt(new Date());
		return userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 激活用户
	 */
	@Transactional(rollbackFor = Exception.class)
	public int enableUser(long userId) {
		if (exceptionLoginService.IfSendEmail() > 0) {
			User record = userMapper.selectByPrimaryKey(userId);
			try {
				new MailUtil(sendFrom, javaMailSender).SendMail("长江大数据", "您的账号已被激活", record.getEmail());
			} catch (Exception e) {
				logger.error(e);
			}
		}

		User user = new User();
		user.setUserId(userId);
		user.setEnable((short) 1);
		user.setUpdatedAt(new Date());
		return userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 找回密码
	 * 
	 * @param email
	 * @return boolean
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean findPassword(String email) {
		boolean result = false;
		User user = selectByEmail(email);
		if (user != null) {
			// 生成新密码
			User user2 = new User();
			user2.setUserId(user.getUserId());
			user2.setUpdatedAt(new Date());
			String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
			user2.setSalt(MD5Util.MD5By32(salt));
			String password = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
			user2.setPassword(MD5Util.MD5By32(password + user2.getSalt()));

			// 修改用户密码
			userMapper.updateByPrimaryKeySelective(user2);

			try {
				new MailUtil(sendFrom, javaMailSender).SendMail("长江大数据", "您的新密码为" + password, email);
			} catch (Exception e) {
				logger.error(e);
			}

			result = true;
		}
		return result;
	}
	
	/**
	 * 发送重置密码地址到邮箱
	 * @param email
	 * @return boolean
	 */
	public boolean sendPassLink(String email) {
		boolean result = false;
		User user = selectByEmail(email);
		if (user != null) {
			// Base64加密
			String emailcipher = Base64.encodeBase64String(email.getBytes());
			try {
				new MailUtil(sendFrom, javaMailSender).SendMail("长江大数据", "请您点击以下连接重置密码 " + resetPasswordUrl + "?ec=" + emailcipher, email);
			} catch (Exception e) {
				logger.error(e);
			}
			result = true;
		}
		return result;
	}
	
	/**
	 * 重置密码
	 * @param emailcipher 邮箱密文
	 * @param password 密码
	 * @return boolean
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean restPass(String emailcipher, String password) {
		boolean result = false;
		// Base64解密
		String email = new String(Base64.decodeBase64(emailcipher));
		User user = selectByEmail(email);
		if (user != null) {
			User user2 = new User();
			user2.setUserId(user.getUserId());
			user2.setUpdatedAt(new Date());
			String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
			user2.setSalt(MD5Util.MD5By32(salt));
			user2.setPassword(MD5Util.MD5By32(password + user2.getSalt()));
			// 修改用户密码
			userMapper.updateByPrimaryKeySelective(user2);
			result = true;
		}
		return result;
	}

	/**
	 * 更新用户信息的方法(需要修改密码时)
	 * 
	 * @param record
	 * @return
	 */
	public Map<String, String> updateByPrimaryKeySelective(User record) {
		record.setUpdatedAt(new Date());
		User user = userMapper.selectByPrimaryKey(record.getUserId());
		Map<String, String> map = new HashMap<>();
		if (!user.getPassword().equals(MD5Util.MD5By32(record.getPassword() + record.getSalt()))) {
			String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
			record.setSalt(MD5Util.MD5By32(salt));
			record.setPassword(MD5Util.MD5By32(record.getPassword() + record.getSalt()));
		}
		if (user.getUsername().equals(record.getUsername())) {
			userMapper.updateByPrimaryKeySelective(record);
			return map;
		} else {
			// 用户修改了用户名
			if (this.selectByUserName(record.getUsername()) != null) {
				map.put("error", "用户名存在");
			} else {
				userMapper.updateByPrimaryKeySelective(record);
			}
		}

		return map;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public Map<String, String> updatepassword(Long userId, String oldPassword, String newPassword) {
		Map<String, String> map = new HashMap<>();
		User user = userMapper.selectByPrimaryKey(userId);
		user.setTokenCreatedAt(new Date());
		if (user.getPassword().equals(MD5Util.MD5By32(oldPassword + user.getSalt()))) {
			user.setPassword(newPassword);
			this.updateByPrimaryKeySelective(user);
		} else {
			map.put("error", "输入的密码错误");
		}
		return map;

	}

	/**
	 * 修改专家学者账号信息
	 * 
	 * @param username
	 * @param password
	 * @param realName
	 * @return
	 */
	public int updateExpert(Long userId, String username, String password, String realName) {
		int result = 1;
		User user = userMapper.selectByPrimaryKey(userId);
		user.setUsername(username);
		user.setPassword(password);
		user.setRealName(realName);
		user.setUpdatedAt(new Date());
		String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		user.setSalt(MD5Util.MD5By32(salt));
		user.setPassword(MD5Util.MD5By32(user.getPassword() + user.getSalt()));
		if (user.getUsername().equals(user.getUsername())) {
			result = userMapper.updateByPrimaryKeySelective(user);
		} else {
			// 用户修改的用户名已经存在
			if (this.selectByUserName(user.getUsername()) != null)
				return 0;
			else
				result = userMapper.updateByPrimaryKeySelective(user);
		}
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteByPrimaryKey(long id) {
		// 远程调用 删除专家学者
		expertClient.deleteExpertByUserId(id);
		UserBook userBook = new UserBook();
		userBook.setUserId(id);
		userBookMapper.delete(userBook);
		LoginRecord loginRecord = new LoginRecord();
		loginRecord.setUserId(id);
		loginRecordMapper.delete(loginRecord);
		UserRole userRole = new UserRole();
		userRole.setUserId(id);
		userRoleMapper.delete(userRole);
		UserVip userVip = new UserVip();
		userVip.setUserId(id);
		userVipMapper.delete(userVip);
		Bookmark bookmark = new Bookmark();
		bookmark.setUserId(id);
		bookmarkMapper.delete(bookmark);

		return userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 
	 * 通过用户名或者邮箱或者手机号获取用户
	 * 
	 * @return
	 */
	public User findUserByTag(String Tag) {

		return userMapper.findUserByTag(Tag);

	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	public Map<String, String> login(String searchStr, String password, String from) {
		Map<String, String> map = new HashMap<>();
		User user = this.findUserByTag(searchStr);
		if (user != null) {
			if (user.getEnable().equals(Short.parseShort("1"))) {
				if ((MD5Util.MD5By32(password + user.getSalt()).equals(user.getPassword()))) {
					List<Role> roles = roleMapper.selectRoleByUserId(user.getUserId());
					if (from.equals("index")) {
						if (roles.size() != 0 && !(roles.get(0).getRoleName().equals("ROLE_INSPECTOR"))) {
							map.put("error", "您的账号无法登录门户");
							return map;
						} else {
							// 普通用户 或者质检员
							HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
									.getRequestAttributes()).getRequest();
							String realIp = "";
							try {
								realIp = this.getIpAddr(request);// 获取当前用户ip
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							logger.info("----------------->Ip" + realIp);
							LoginRecord record = null;
							boolean isNewIp = true;
							String address = getAdressFromIp(realIp);// 获取ip归属地
							List<LoginRecord> records = loginRecordMapper.selectByUserId(user.getUserId());// 登录记录
							if (records == null || records.size() == 0) { // 登录记录为空
								record = new LoginRecord(user.getUserId(), new Date(), realIp, address);
								this.insertLoginRecord(record);
							} else {
								for (LoginRecord loginRecord : records) {
									if (realIp.equals(loginRecord.getLoginIp())) {
										loginRecord.setLoginTime(new Date());
										this.updateLoginRecord(loginRecord);
										isNewIp = false;
										break;
									}
								}
								if (isNewIp) {
									record = new LoginRecord(user.getUserId(), new Date(), realIp, address);
									this.insertLoginRecord(record);
								}
							}
							logger.info("登录成功下发Token");
							String accsess_token = jwtTokeUtil
									.generateAccessToken(jwtUserDetailService.loadUserByUsername(user.getUsername()));
							Date tokenCreateAt = jwtTokeUtil.getCreatedDateFromToken(accsess_token);
							user.setTokenCreatedAt(tokenCreateAt);// 首页登录时下发token更新首页
																	// 登录Token的下发时间
							userMapper.updateByPrimaryKeySelective(user);
							map.put("index_accsess_token", accsess_token);
							return map;
						}
					} else {
						// 从后台登录
						if (roles.size() == 0 || roles.get(0).getRoleName().equals("ROLE_INSPECTOR")) { // 普通用户或质检员不能登录后台
							map.put("error", "您的账号无法登录后台");
							return map;
						} else {
							// 其他角色
							HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
									.getRequestAttributes()).getRequest();
							String realIp = "";
							try {
								realIp = this.getIpAddr(request);// 获取当前ip
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							logger.info("----------------->Ip" + realIp);
							LoginRecord record = null;
							boolean isNewIp = true;
							List<LoginRecord> records = loginRecordMapper.selectByUserId(user.getUserId());// 登录记录
							if (records == null || records.size() == 0) {
								String address = getAdressFromIp(realIp);
								record = new LoginRecord(user.getUserId(), new Date(), realIp, address);
								this.insertLoginRecord(record);

							} else {
								for (LoginRecord loginRecord : records) {
									if (realIp.equals(loginRecord.getLoginIp())) {
										loginRecord.setLoginTime(new Date());
										this.updateLoginRecord(loginRecord);
										isNewIp = false;
										break;
									}
								}
								if (isNewIp) {
									String address = getAdressFromIp(realIp);
									record = new LoginRecord(user.getUserId(), new Date(), realIp, address);
									this.insertLoginRecord(record);
								}

							}
							logger.info("登录成功下发Token");
							String accsess_token = jwtTokeUtil
									.generateAccessToken(jwtUserDetailService.loadUserByUsername(user.getUsername()));
							Date tokenCreateAt = jwtTokeUtil.getCreatedDateFromToken(accsess_token);
							user.setBgTokenCreatedAt(tokenCreateAt); // 后台token的下发时间
							userMapper.updateByPrimaryKeySelective(user);
							map.put("bg_accsess_token", accsess_token);
							return map;
						}
					}

				} else {
					logger.info("密码错误");
					map.put("error", "密码错误");
					return map;
				}
			} else {
				logger.info("登录异常");
				map.put("error", "您的异常ip登录过多,账号已经被封");
				// if (exceptionLoginService.IfSendEmail() > 0) {
				// try {
				// new MailUtil(sendFrom, javaMailSender).SendMail("长江大数据", "您异常登录的ip过多账号被封了",
				// user.getEmail());
				// } catch (Exception e) {
				// return map;
				// }
				// }
				return map;
			}

		} else {
			logger.info("用户不存在");
			map.put("error", "账号不存在");
			return map;
		}

	}

	/**
	 * 通过用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public User selectByUserName(String username) {
		User user = new User();
		user.setUsername(username);
		return userMapper.selectOne(user);
	}

	/**
	 * 通过邮箱查询用户
	 * 
	 * @param username
	 * @return
	 */
	public User selectByEmail(String email) {
		User user = new User();
		user.setEmail(email);
		return userMapper.selectOne(user);
	}

	/**
	 * 通过手机号
	 * 
	 * @param username
	 * @return
	 */
	public User selectByPhone(Long Phone) {
		User user = new User();
		user.setPhone(Phone);
		return userMapper.selectOne(user);
	}

	/**
	 * 获取机构会员总数
	 * 
	 * @return
	 */
	public int selectCountBySeqNum(Integer seqNum) {

		return userMapper.selectCountBySeqNum(seqNum);
	}

	public User selectByPrimaryKey(Long id) {

		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据id查询用户用户名邮箱
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectSomeById(Long id) {
		User user = userMapper.selectByPrimaryKey(id);
		Map<String, Object> map = new HashMap<>();
		if (user != null) {
			map.put("username", user.getUsername());
			map.put("email", user.getEmail());
		}
		return map;

	}

	/**
	 * 取消或设置质检员
	 * 
	 * @param id
	 *            用户id
	 * @return 是否为质检员 0=否 1=是
	 */
	@Transactional
	public int setInspector(Long id) {
		Role role = new Role();
		role.setRoleName("ROLE_INSPECTOR");
		Role record = roleMapper.selectOne(role);// 查询质检员ID
		UserRole useRole = new UserRole();
		useRole.setUserId(id);
		useRole.setRoleId(record.getRoleId());
		UserRole ur = userRoleMapper.selectOne(useRole);
		if (ur == null) {
			/* 用户不是质检员，设为质检员 */
			Date date = new Date();
			useRole.setCreatedAt(date);
			useRole.setUpdatedAt(date);
			userRoleMapper.insertSelective(useRole);
			return IS_INSPECTOER;
		} else {
			/* 用户是质检员，取消质检员 */
			userRoleMapper.delete(useRole);
			return IS_NOT_INSPECTOER;
		}
	}

	/**
	 * 用户个人中心
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> selectPersonalCenter(long userId) {
		String vipStatus = userVipMapper.selectVipStatus(userId);
		User user = userMapper.selectByPrimaryKey(userId);
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (user != null) {
			if ("会员".equals(vipStatus)) {
				// 会员到期时间
				Date endDate = userVipMapper.selectEndDate(userId);
				if (endDate != null) {
					map.put("endDate", sdFormat.format(endDate));
				} else {
					map.put("endDate", null);
				}
				map.put("vipStatus", vipStatus);
				map.put("username", user.getUsername());
				map.put("email", user.getEmail());
				map.put("createdAt", sdFormat.format(user.getCreatedAt()));
			} else {
				Date endDate = userVipMapper.selectEndDate(userId);
				if (endDate != null) {
					map.put("endDate", sdFormat.format(endDate));
				} else {
					map.put("endDate", null);
				}
				map.put("vipStatus", vipStatus);
				map.put("username", user.getUsername());
				map.put("email", user.getEmail());
				if (user.getCreatedAt() != null) {
					map.put("createdAt", sdFormat.format(user.getCreatedAt()));
				}

			}
		}
		return map;
	}

	public List<UserIndexDto> selectUserForIndex() {
		List<UserIndexDto> list = new ArrayList<>();
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -7);// 把日期直接设定为七天前
		for (int i = 1; i <= 7; i++) {
			calendar.add(calendar.DATE, +1);// 把日期每次往后增加一天
			date = calendar.getTime();
			String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
			UserIndexDto userIndexDto = userMapper.selectUserForIndex(dateFormat);
			userIndexDto.setDate(dateFormat);
			list.add(userIndexDto);
		}
		return list;
	}

	public PageInfo<UserOrganizationDto> selectUserOrganization(Integer pageNum, Integer pageSize, Integer sqe_num) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserOrganizationDto> list = userMapper.selectUserOrganization(sqe_num);
		PageInfo<UserOrganizationDto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 设置用户会员
	 * 
	 * @param userId
	 * @param month
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertVipBatch(@Param("userId") Long userId, @Param("month") Integer month) {
		UserVip record = new UserVip();
		record.setUserId(userId);
		UserVip temp = userVipMapper.selectOne(record);
		Date date = new Date();
		if (temp != null && date.before(temp.getEndDate())) {
			// 已经是vip了 且没有过期
			Date endDate;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(temp.getEndDate());
			calendar.add(calendar.MONTH, month);
			endDate = calendar.getTime();
			UserVip userVip = new UserVip();
			userVip.setUserVipId(temp.getUserVipId());
			userVip.setEndDate(endDate);
			userVip.setUpdatedAt(new Date());
			return userVipMapper.updateByPrimaryKeySelective(userVip);
		} else if (temp == null) {
			// 不是vip
			Date endDate;
			record.setBeginDate(new Date());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(record.getBeginDate());
			calendar.add(calendar.MONTH, month);
			endDate = calendar.getTime();
			record.setCreatedAt(new Date());
			record.setEndDate(endDate);
			return userVipMapper.insertSelective(record);
		} else {
			// 过期的会员
			Date endDate;
			temp.setBeginDate(new Date());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(record.getBeginDate());
			calendar.add(calendar.MONTH, month);
			endDate = calendar.getTime();
			temp.setUpdatedAt(new Date());
			temp.setEndDate(endDate);
			return userVipMapper.updateByPrimaryKeySelective(temp);
		}

	}

	public Map<String, String> updateUserNameOrEmail(Long userId, String username, String email) {
		Map<String, String> map = new HashMap<>();
		User user = userMapper.selectByPrimaryKey(userId);
		if (user.getEmail() != null) {
			if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
				// 用户没有修改
				map.put("error", "你改了啥玩意？？？");
			} else {
				if (!user.getUsername().equals(username)) { // 用户修改了用户名
					User temp = this.selectByUserName(username);
					if (temp != null) {
						map.put("error", "修改的用户名存在了");
					} else if (!user.getEmail().equals(email)) { // 用户修改了邮箱
						temp = this.selectByEmail(email);
						if (temp != null) {
							map.put("error", "修改的邮箱存在了");
						} else {
							user.setUsername(username);
							user.setEmail(email);
							userMapper.updateByPrimaryKeySelective(user);
						}
					} else {
						user.setUsername(username);
						user.setEmail(email);
						userMapper.updateByPrimaryKeySelective(user);
					}
				} else {
					// 用户没有修改用户名
					if (!user.getEmail().equals(email)) {
						User temp = this.selectByEmail(email);
						if (temp != null) {
							map.put("error", "修改的邮箱存在了");
						} else {
							user.setEmail(email);
							userMapper.updateByPrimaryKeySelective(user);
						}
					} else {
						map.put("error", "你改了啥玩意？？？");
						return map;
					}
				}
			}
			return map;
		} else {
			User temp0 = this.selectByEmail(email);
			if ( temp0 != null) {
				map.put("error", "邮箱已存在！");
			} else {
				user.setEmail(email);
				if (!user.getUsername().equals(username)) { // 用户修改了用户名
					User temp = this.selectByUserName(username);
					if (temp != null) {
						map.put("error", "修改的用户名存在了");
					} else {
						user.setUsername(username);
						userMapper.updateByPrimaryKeySelective(user);
					}
				} else {
					// 用户没有修改用户名
					userMapper.updateByPrimaryKeySelective(user);
				}
			}
			return map;
		}
	}

	/**
	 * 后台条件查询用户
	 * 
	 * @param pageNum
	 *            当前页
	 * @param pageSize
	 *            每页的数量
	 * @param enable
	 *            账号状态 0=不可用 1=可用
	 * @param vipStatus
	 *            会员状态 0=普通用户 1=会员 2=过期会员
	 * @param organization
	 *            机构名称
	 * @return
	 */
	public PageInfo<UserOrganizationDto> selectBy(Integer pageNum, Integer pageSize, Short enable, Short vipStatus,
			String organization, String username) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserOrganizationDto> list = userMapper.selectBy(enable, vipStatus, organization, username);
		PageInfo<UserOrganizationDto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 根据机构前缀查询用户
	 * 
	 * @param id
	 * @return
	 */
	public List<User> selectBySeqNum(Integer id) {
		User record = new User();
		record.setSeqNum(id);
		return userMapper.select(record);
	}

	/**
	 * 通过ip获取归属地
	 * 
	 * @param ipAddress
	 * @return
	 */
	public String getAdressFromIp(String ipAddress) {
		AddressUtils addressUtils = new AddressUtils();
		String address = " ";
		try {
			address = addressUtils.getAddresses("ip=" + ipAddress, "utf-8");
		} catch (UnsupportedEncodingException e) {
			address = "未知的IP地址";
			logger.error("没有获取的ip归属地");
			e.printStackTrace();
		}
		return address;
	}

	/**
	 * 插入登录记录
	 * 
	 * @return
	 */
	public int insertLoginRecord(LoginRecord record) {

		int num = loginRecordMapper.insertSelective(record);
		if (num > 0) {
			logger.info("插入登录记录成功");
			return 1;
		} else {
			logger.info("插入登录记录失败");
			return 0;
		}
	}

	/**
	 * 更新登录记录
	 * 
	 * @param record
	 * @return
	 */
	public int updateLoginRecord(LoginRecord record) {
		int num = loginRecordMapper.updateByPrimaryKey(record);
		if (num > 0) {
			logger.info("更新登录记录成功");
			return 1;
		} else {
			logger.info("更新登录记录失败");
			return 0;
		}
	}

	/**
	 * 获取真实的ip地址
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static final String getIpAddr(final HttpServletRequest request) throws Exception {
		if (request == null) {
			throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ipString.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ipString = str;
				break;
			}
		}

		return ipString;
	}

}
