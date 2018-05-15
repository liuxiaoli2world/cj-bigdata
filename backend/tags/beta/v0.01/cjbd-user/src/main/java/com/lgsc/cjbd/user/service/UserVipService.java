package com.lgsc.cjbd.user.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.user.mapper.UserVipMapper;
import com.lgsc.cjbd.user.model.UserVip;

/**
 * 
 */
@Service
public class UserVipService {
	
	private static Logger log = LogManager.getLogger(UserVipService.class);
	
	@Autowired
	private UserVipMapper userVipMapper;

	public int insert(UserVip record) {
		return userVipMapper.insert(record);
	}

	public int insertSelective(UserVip record) {
		return userVipMapper.insertSelective(record);
	}
	
	/**
	 * 根据用户id查询会员状态
	 * @param userId
	 * @return
	 */
	public String selectVipStatus(Long userId) {
		return userVipMapper.selectVipStatus(userId);
	}
	
	/**
	 * 添加或修改会员到期时间
	 * @param userId 用户id
	 * @param vipId vipId
	 * @param beginDate 开始时间
	 * @param endDate 到期时间
	 * @param duration 购买时长
	 * @return 是否成功
	 */
	@Transactional
	public boolean insertOrUpdateSelective(Long userId, Long vipId, Date beginDate, Date endDate, Integer duration) {
		log.info("添加或修改会员到期时间开始，参数：[userId=" + userId + ", vipId=" + vipId + ", beginDate=" + beginDate + ", endDate=" + endDate + ", duration=" + duration + "]");
		boolean result = true;
		
		String vipStatus = userVipMapper.selectVipStatus(userId);
		if ("普通用户".equals(vipStatus)) {// 如果是普通用户，则新增用户会员商品关系
			UserVip record = new UserVip();
			record.setUserId(userId);
			record.setVipId(vipId);
			record.setBeginDate(beginDate);
			record.setEndDate(endDate);
			record.setCreatedAt(new Date());
			userVipMapper.insertSelective(record);
		} else if ("会员".equals(vipStatus)) {// 如果已经是会员，则累加到期时间
			userVipMapper.updateAddUpEndDate(userId, duration);
		} else if ("过期会员".equals(vipStatus)) {// 如果是过期会员，则修改开始时间和到期时间
			userVipMapper.updateEndDate(userId, beginDate, endDate);
		} else {
			result = false;
		}
		
		log.info("添加或修改会员到期时间结束");
		return result;
	}

	public UserVip selectByPrimaryKey(long id) {
		return userVipMapper.selectByPrimaryKey(id);
	}
    
    public List<UserVip> selectAll() {
		return userVipMapper.selectAll();
	}
	
	public PageInfo<UserVip> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserVip> list = userVipMapper.selectAll();
		PageInfo<UserVip> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(UserVip record) {
		return userVipMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(UserVip record) {
		return userVipMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 修改用户会员时长
	 * @param userId 用户id
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public int updateEndDate(Long userId, Date beginDate, Date endDate) {
		return userVipMapper.updateEndDate(userId, beginDate, endDate);
	}
	
	/**
	 * 累加用户会员时长
	 * @param userId 用户id
	 * @param duration 用户购买时长
	 * @return
	 */
	public int updateAddUpEndDate(Long userId, Integer duration) {
		return userVipMapper.updateAddUpEndDate(userId, duration);
	}

	public int deleteByPrimaryKey(long id) {
		return userVipMapper.deleteByPrimaryKey(id);
	}

	public UserVip selectUserVipByUserId(Long userId) {
		UserVip userVip = new UserVip();
		userVip.setUserId(userId);
		return userVipMapper.selectOne(userVip);
	}

}
