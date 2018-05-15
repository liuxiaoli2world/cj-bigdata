package com.lgsc.cjbd.expert.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lgsc.cjbd.dao.BaseMapper;
import com.lgsc.cjbd.expert.model.Expert;

public interface ExpertMapper extends BaseMapper<Expert> {
	/**
	 * 根据id查询专家，结果返回著作
	 * 
	 * @param expertId
	 * @return
	 */
	public Expert selectExpertAndComposition(Long expertId);

	/**
	 * 根据id查询专家信息
	 * 
	 * @param expertId
	 * @return
	 */
	public Expert selectExpertById(Long expertId);

	/**
	 * 查询推荐专家和著作
	 * 
	 * @return
	 */
	public Expert selectRecommendExpertAndComposition();

	/**
	 * 模糊查询：根据专家姓名查询专家和著作
	 * 
	 * @param realName
	 * @return
	 */
	public List<Expert> selectExpertAndCompositionByName(String realName);

	/**
	 * 根据用户名查询专家信息
	 * 
	 * @param realName
	 * @return
	 */
	public List<Expert> selectExpertByName(String realName);

	/**
	 * 查询所有专家和著作
	 * 
	 * @return
	 */
	public List<Expert> selectAllExpertAndComposition();

	/**
	 * 查询所有专家姓名
	 * 
	 * @return
	 */
	public List<Map<String,Object>> selectAllExpertName();

	/**
	 * 查询所有的专家信息
	 * 
	 * @return
	 */
	public List<Expert> selectAllExpert();

	/**
	 * 根据专家id查找userId
	 * 
	 * @param expertId
	 * @return
	 */
	public Long selectUserIdByExpertId(Long expertId);

	/**
	 * 根据姓名和分类查询专家信息
	 * 
	 * @param expertClassify
	 * @param realName
	 * @return
	 */
	public List<Expert> selectByClassifyAndRealName(@Param("expertClassify") String expertClassify,
			@Param("realName") String realName);

	/**
	 * 根据userId查找专家
	 * 
	 * @param userId
	 * @return
	 */
	public Expert selectExpertByuserId(Long userId);

}