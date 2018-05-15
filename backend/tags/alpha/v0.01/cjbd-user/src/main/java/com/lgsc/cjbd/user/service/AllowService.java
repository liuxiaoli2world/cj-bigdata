package com.lgsc.cjbd.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgsc.cjbd.user.dto.AllowDto;
import com.lgsc.cjbd.user.mapper.AllowMapper;
import com.lgsc.cjbd.user.mapper.RoleAllowMapper;
import com.lgsc.cjbd.user.mapper.UserMapper;
import com.lgsc.cjbd.user.mapper.UserRoleMapper;
import com.lgsc.cjbd.user.model.Allow;
import com.lgsc.cjbd.user.model.RoleAllow;
import com.lgsc.cjbd.user.model.User;
import com.lgsc.cjbd.user.model.UserRole;


/**
 * 权限管理服务层
 */
@Service
public class AllowService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AllowMapper allowMapper;

	@Autowired
	private RoleAllowMapper roleAllowMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;

	public int insert(Allow record) {
		return allowMapper.insert(record);
	}

	public int insertSelective(Allow record) {
		return allowMapper.insertSelective(record);
	}

	public Allow selectByPrimaryKey(long id) {
		return allowMapper.selectByPrimaryKey(id);
	}

	public List<Allow> selectAll() {
		List<Allow> list = allowMapper.selectAll();
		return list;
	}

	public int updateByPrimaryKey(Allow record) {
		return allowMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Allow record) {
		return allowMapper.updateByPrimaryKeySelective(record);
	}

	public int deleteByPrimaryKey(long id) {
		return allowMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据角色id查询对应的权限
	 * 
	 * @param id
	 * @return
	 */
	public List<Allow> findByRoleId(Long id) {
		return allowMapper.findByRoleId(id);
	}

	/**
	 * 根据用户id查询对应的权限的值
	 * 
	 * @param id
	 * @return
	 */
	public List<AllowDto> findByUserId(Long id) {
		List<AllowDto> allAllowDto = new ArrayList<>();
		List<Allow> allAllows = allowMapper.selectAll();
		List<Allow> userAllows = allowMapper.findByUserId(id);
		AllowDto allowDto = null;
		for (Allow allow : allAllows) {
			allowDto = new AllowDto();
			allowDto.setAllowId(allow.getAllowId());
			allowDto.setAllowName(allow.getAllowName());
			allowDto.setParentAllowId(allow.getParentAllowId());
			allowDto.setIsAllows(false);
			for (Allow userAllow : userAllows) {
				if (userAllow.getAllowId().equals(allowDto.getAllowId())) {
					allowDto.setIsAllows(true);
					break;
				}
			}
			allAllowDto.add(allowDto);
		}
		return allAllowDto;
	}

	/**
	 * 配置对应角色的权限
	 * 
	 * @param id
	 * @param allows
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean updateAllow(Long id, String allows) {
		String[] ids = allows.split(",");
		List<Long> list = new ArrayList<>();
		for (String temp : ids) {
			list.add(Long.parseLong(temp));
		}
		RoleAllow roleAllow= new RoleAllow();
		roleAllow.setRoleId(id);
		roleAllowMapper.delete(roleAllow); //删除角色的所有权限
		RoleAllow insertRoleAllow = new RoleAllow();
		insertRoleAllow.setRoleId(id);
		UserRole userRole = new UserRole();
		userRole.setRoleId(id);
		List<UserRole> userRoles = userRoleMapper.select(userRole);
		for (UserRole temp : userRoles) {
			 User user = new User();
			 user.setUserId(temp.getUserId());
			 user.setTokenCreatedAt(new Date());
//			 userMapper.updateByPrimaryKey(user); //更新token时间
			 userMapper.updateByPrimaryKeySelective(user);
		}
		for (Long long1 : list) {
			insertRoleAllow.setAllowId(long1);
			roleAllowMapper.insert(insertRoleAllow);
		}
		return true;
	}

}
