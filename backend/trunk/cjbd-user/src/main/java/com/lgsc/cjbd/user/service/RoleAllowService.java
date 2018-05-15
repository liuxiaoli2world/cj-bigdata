package com.lgsc.cjbd.user.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.lgsc.cjbd.user.mapper.RoleAllowMapper;
import com.lgsc.cjbd.user.model.RoleAllow;

/**
 * 
 */
@Service
public class RoleAllowService {
	
	@Autowired
	private RoleAllowMapper roleAllowMapper;

	public int insert(RoleAllow record) {
		return roleAllowMapper.insert(record);
	}
    @Transactional(rollbackFor=Exception.class)
	public int insertSelective(RoleAllow record) {
   
		record.setCreatedAt(new Date());
		record.setUpdatedAt(new Date());
		return roleAllowMapper.insertSelective(record);
	}

	public RoleAllow selectByPrimaryKey(long id) {
		return roleAllowMapper.selectByPrimaryKey(id);
	}
    
    public List<RoleAllow> selectAll() {
		return roleAllowMapper.selectAll();
	}
	
	public PageInfo<RoleAllow> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RoleAllow> list = roleAllowMapper.selectAll();
		PageInfo<RoleAllow> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public int updateByPrimaryKey(RoleAllow record) {
		return roleAllowMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(RoleAllow record) {
		return roleAllowMapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 通过角色id删除权限角色关系
	 * @return
	 */
	public int deleteByRoleId(Long roleId){
		RoleAllow roleAllow = new RoleAllow();
		roleAllow.setRoleId(roleId);
		return roleAllowMapper.delete(roleAllow);
	}

	public int deleteByPrimaryKey(long id) {
		return roleAllowMapper.deleteByPrimaryKey(id);
	}

}
