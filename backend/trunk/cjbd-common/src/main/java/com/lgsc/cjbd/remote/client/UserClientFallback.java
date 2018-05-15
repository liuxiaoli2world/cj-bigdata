package com.lgsc.cjbd.remote.client;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.lgsc.cjbd.vo.Response;

/**
 * 失败回调
 * 
 * @author yeqj
 */
@Component
public class UserClientFallback implements UserClient {

	private static Logger log = LogManager.getLogger(UserClientFallback.class);

	@Override
	public Integer deleteUserBookByBookDirId(Long bookDirId) {
		log.error("远程调用失败，删除用户图书关系失败，参数：[bookDirId=" + bookDirId + "]");
		return null;
	}

	@Override
	public Integer deleteBookMarkByBookSectionId(Long bookSectionId) {
		log.error("远程调用失败，删除用户书签失败，参数：[bookSectionId=" + bookSectionId + "]");
		return null;
	}

	@Override
	public Integer deleteUserBookByIsbn(String isbn) {
		log.error("远程调用失败，删除用户图书关系失败，参数：[isbn=" + isbn + "]");
		return null;
	}

	@Override
	public Integer deleteBookMarkByIsbn(String isbn) {
		log.error("远程调用失败，删除用户书签失败，参数：[isbn=" + isbn + "]");
		return null;
	}

	@Override
	public Long registExpert(String username, String password, String realName) {
		log.error("远程调用失败，注册专家学者失败，参数：[username=" + username + ",password=" + password + ",realName=" + realName + "]");
		return null;
	}

	@Override
	public Response selectUserByUserName(String username) {
		log.error("远程调用失败，根据用户名查找专家学者失败，参数：[username=" + username + "]");
		return new Response().failure();
	}

	@Override
	public Response updateExpert(Long userId, String username, String password, String realName) {
		log.error("远程调用失败，修改专家学者账号信息失败，参数：[userId=" + userId + ",username=" + username + ",password=" + password
				+ ",realName=" + realName + "]");
		return new Response().failure();
	}

	@Override
	public Map<String, Object> selectSome(Long userId) {
		log.error("远程调用失败，查询用户一些信息失败，参数：[id=" + userId + "]");
		return null;
	}

	@Override
	public Map<String, Object> selectDetail(Long vipId) {
		log.error("远程调用失败，查询会员商品详细失败，参数：[id=" + vipId + "]");
		return null;
	}

	@Override
	public Response insertExpertRole(Long userId) {
		log.error("远程调用失败，新增专家学者用户关系失败，参数：[userId=" + userId + "]");
		return null;
	}

	@Override
	public Response delete(Long id) {
		log.error("远程调用失败，删除用户失败，参数：[id=" + id + "]");
		return null;
	}

	@Override
	public Response deleteRoleByuserId(Long userId) {
		log.error("远程调用失败，根据userId查询用户角色关系失败，参数：[userId=" + userId + "]");
		return null;
	}

	@Override
	public Response selectUserVipByUserId(Long userId) {
		log.error("远程调用失败，根据userId查询用户会员关系失败，参数：[userId=" + userId + "]");
		return null;
	}

	@Override
	public Response findByUserId(Long id) {
		log.error("远程调用失败，根据userId查询用户会员关系失败，参数：[userId=" + id + "]");
		return null;
	}

	@Override
	public Response selectAll() {
		log.error("远程调用失败，查询需要鉴权的api失败");
		return null;
	}

	@Override
	public Boolean insertOrUpdateUserVip(Long userId, Long vipId, String beginDate, String endDate, Integer duration) {
		log.error("远程调用失败，添加或修改会员到期时间失败");
		return null;
	}

	@Override
	public Boolean batchInsertUserBook(List<Map<String, Object>> list) {
		log.error("远程调用失败，批量添加用户图书商品关系失败");
		return null;
	}

	@Override
	public Response selectRootDirsOfUser(String isbn, Long userId) {
		log.error("远程调用失败,查询用户购买的图书目录失败！");
		return null;
	}

	@Override
	public String selectVipStatus(Long userId) {
		log.error("远程调用失败,查询用户会员状态失败！");
		return null;
	}

	@Override
	public List<String> selectRoleStatus(Long userId) {
		log.error("远程调用失败,查询用户觉角色状态失败！");
		return null;
	}

}
