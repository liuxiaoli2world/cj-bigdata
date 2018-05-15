package com.lgsc.cjbd.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.user.dto.OrganizationDto;
import com.lgsc.cjbd.user.mapper.OrganizationMapper;
import com.lgsc.cjbd.user.mapper.UserMapper;
import com.lgsc.cjbd.user.model.Organization;
import com.lgsc.cjbd.user.model.User;

/**
 * 
 */
@Service
public class OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	public int insert(Organization record) {
		return organizationMapper.insert(record);
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertSelective(Organization organization) {
		organization.setCreatedAt(new Date());
		organization.setUpdatedAt(new Date());
		int num = organizationMapper.insertSelective(organization);
		if (num > 0) {
			return num;
		}
		return 0;
	}

	public Organization selectByPrimaryKey(long id) {
		return organizationMapper.selectByPrimaryKey(id);
	}

	public List<Organization> selectAll() {
		return organizationMapper.selectAll();
	}

	public PageInfo<OrganizationDto> selectAll(int pageNum, int pageSize, String name) {
		PageHelper.startPage(pageNum, pageSize);
		List<OrganizationDto> list = organizationMapper.selectByName(name);
		PageInfo<OrganizationDto> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Transactional(rollbackFor = Exception.class)
	public int insertBatch(String preName, Integer num, Long id) {
		User record = new User();
		Organization organization = this.selectByPrimaryKey(id);
		record.setSeqNum(organization.getSeqNum());
		record.setPassword("123456");
		boolean isSuccess = true;
		String username = preName;
		int count = userService.selectCountBySeqNum(organization.getSeqNum());
		for (int i = count + 1; i <= num + count; i++) {
			record.setUserId(null);
			record.setSeqNum(organization.getSeqNum());
			username = preName + "_" + String.valueOf(record.getSeqNum()) + "_" + String.valueOf(i);
			record.setUsername(username);
			record.setPassword("123456");
			Map<String, String> map = userService.insertSelective(record);
			if (map.containsKey("error")) {
				isSuccess = false;
				break;
			}
		}
		if (isSuccess) {
			return 1;
		} else {
			return 0;
		}

	}

	public int updateByPrimaryKey(Organization record) {
		return organizationMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(Organization record) {
		return organizationMapper.updateByPrimaryKeySelective(record);
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteByPrimaryKey(long id) {
		Organization organization = this.selectByPrimaryKey(id);
		if (organization != null) {
			userMapper.deleteBySqeNum(organization.getSeqNum());
		}
		return organizationMapper.deleteByPrimaryKey(id);
	}

	public Organization selectByName(String name) {
		Organization organization = new Organization();
		organization.setName(name);
		return organizationMapper.selectOne(organization);
	}

	public Organization selectBySqeNum(Integer seqNum) {
		Organization organization = new Organization();
		organization.setSeqNum(seqNum);
		return organizationMapper.selectOne(organization);

	}


}
