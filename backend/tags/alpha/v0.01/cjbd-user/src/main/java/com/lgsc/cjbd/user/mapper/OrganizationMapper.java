package com.lgsc.cjbd.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.user.dto.OrganizationDto;
import com.lgsc.cjbd.user.model.Organization;

public interface OrganizationMapper extends BaseMapper<Organization> {
	
	public List<OrganizationDto> selectByName(@Param("name")String name);
}