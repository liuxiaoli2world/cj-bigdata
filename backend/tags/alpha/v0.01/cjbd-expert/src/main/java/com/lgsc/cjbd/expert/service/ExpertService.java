package com.lgsc.cjbd.expert.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.expert.dto.ExpertDto;
import com.lgsc.cjbd.expert.mapper.CompositionMapper;
import com.lgsc.cjbd.expert.mapper.DomainMapper;
import com.lgsc.cjbd.expert.mapper.ExpertDomainMapper;
import com.lgsc.cjbd.expert.mapper.ExpertMapper;
import com.lgsc.cjbd.expert.model.Composition;
import com.lgsc.cjbd.expert.model.Expert;
import com.lgsc.cjbd.expert.model.ExpertDomain;
import com.lgsc.cjbd.remote.client.UserClient;

/**
 * 专家Service
 * 
 * @author Pengmeiyan
 *
 */
@Service
public class ExpertService {

	@Autowired
	private ExpertMapper expertMapper;
	@Autowired
	private DomainMapper domainMapper;
	@Autowired
	private ExpertDomainMapper expertDomainMapper;
	@Autowired
	private CompositionMapper compositionMapper;
	@Autowired
	private UserClient userClient;

	/**
	 * 新增专家（判断推荐专家；新增用户表以及用户角色关系表；新增专家和专家分类关系表）
	 * 
	 * @param record
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, String> insertExpert(Expert expert) {
		Map<String, String> map = new HashMap<String, String>();
		Long preRecommendId = null;// 原推荐专家id
		Long userId = null;// 用户表中专家学者id
		int result = 0;
		try {
			// 如果是推荐专家
			if (expert.getIsRecommend() == 1) {
				Expert preExpert = expertMapper.selectRecommendExpertAndComposition();
				// 如果已有推荐专家
				if (preExpert != null) {
					preRecommendId = preExpert.getExpertId();
					preExpert.setIsRecommend((short) 0);
					// 更新原有推荐专家信息
					expertMapper.updateByPrimaryKeySelective(preExpert);
				}
			}
			String username = expert.getLoginName();
			String password = expert.getPassword();
			String realName = expert.getRealName();
			if (userClient.selectUserByUserName(username).getMeta().isSuccess()) {// 如果用户名不重复
				// 新增用户表，并获得userId
				userId = userClient.registExpert(username, password, realName);
				// 新增用户角色关系表
				if (userId > 0 && userClient.insertExpertRole(userId).getMeta().isSuccess()) {
					expert.setUserId(userId);
					expert.setCreatedAt(new Date());
					expert.setUpdatedAt(new Date());
					// 如果新增专家成功
					if (expertMapper.insertSelective(expert) == 1) {
						Long domainId = domainMapper.selectByClassifyName(expert.getExpertClassify());
						ExpertDomain expertDomain = new ExpertDomain();
						expertDomain.setDomainId(domainId);
						expertDomain.setExpertId(expert.getExpertId());
						// 新增专家分类关系
						result = expertDomainMapper.insertSelective(expertDomain);
					}
				}
			} else {
				map.put("error", "该用户名已存在！");
			}
			if (result != 1) {// 如果没有全部插入成功，还原
				rollbackExpert(preRecommendId, userId);
			}
			return map;
		} catch (Exception e) {// 如遇上异常，还原
			rollbackExpert(preRecommendId, userId);
			map.put("error", "新增异常！");
			return map;
		}
	}

	private void rollbackExpert(Long preRecommendId, Long userId) {
		if (preRecommendId != null) {
			// 还原推荐专家
			Expert preExpert = expertMapper.selectByPrimaryKey(preRecommendId);
			preExpert.setIsRecommend((short) 1);
			expertMapper.updateByPrimaryKeySelective(preExpert);
		}
		if (userId != null) {
			// 删除用户
			userClient.delete(userId);
			// 删除用户角色关系
			userClient.deleteRoleByuserId(userId);
			Expert tempExpert = expertMapper.selectExpertByuserId(userId);
			// 删除专家分类关系
			expertDomainMapper.deleteByExpertId(tempExpert.getExpertId());
			// 删除专家
			expertMapper.deleteByPrimaryKey(tempExpert);
		}
	}

	/**
	 * 根据id删除专家（同时应该删除分类关系以及专家著作）
	 * 
	 * @param id
	 * @return
	 */
	public int deleteExpert(long expertId) {
		int flag = expertMapper.deleteByPrimaryKey(expertId);
		// 如果专家删除成功
		if (flag == 1) {
			// 删除该专家的所有著作
			List<Composition> compositions = compositionMapper.selectByExpertId(expertId);
			if (compositions != null) {
				for (Composition composition : compositions) {
					compositionMapper.deleteByPrimaryKey(composition);
				}
				// 删除专家分类关系
				ExpertDomain expertDomain = expertDomainMapper.selectByExceptId(expertId);
				if (expertDomain != null)
					expertDomainMapper.deleteByPrimaryKey(expertDomain);
			}
		}
		return flag;
	}

	/**
	 * 修改专家（同时应该修改分类关系）
	 * 
	 * @param record
	 * @return
	 */
	@Transactional
	public int updateExpert(Expert expert) {
		if (expert.getIsRecommend() == 1) {
			Expert preExpert = expertMapper.selectRecommendExpertAndComposition();
			if (preExpert != null) {
				preExpert.setIsRecommend((short) 0);
				expertMapper.updateByPrimaryKeySelective(preExpert);
			}
		}
		expert.setUpdatedAt(new Date());
		if (expertMapper.updateByPrimaryKeySelective(expert) == 1) {// 如果修改专家成功
			long expertId = expert.getExpertId();
			// 获区到更新的分类名称
			String expertClassify = expert.getExpertClassify();
			// 根据更新的分类名称在专家分类表中查询到专家分类编号
			long domainId1 = domainMapper.selectByClassifyName(expertClassify);
			// 根据专家id在专家分类关系表中查询到专家分类编号
			long domainId2 = expertDomainMapper.selectByExceptId(expertId).getDomainId();
			// 如果更新的分类关系和原来的分类关系不一样，更新专家分类关系表
			if (domainId1 != domainId2) {
				ExpertDomain expertDomain = expertDomainMapper.selectByExceptId(expertId);
				expertDomain.setDomainId(domainId1);
				return expertDomainMapper.updateByPrimaryKeySelective(expertDomain);
			} else
				return 1;
		}
		return 0;
	}

	/**
	 * 根据id查询专家
	 * 
	 * @param id
	 * @return
	 */
	public ExpertDto selectByPrimaryKey(long expertId) {
		Expert source = expertMapper.selectByPrimaryKey(expertId);
		ExpertDto target = new ExpertDto();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	/**
	 * 根据id查询专家，结果返回著作
	 * 
	 * @param expertId
	 * @return
	 */
	public ExpertDto selectExpertAndComposition(Long expertId) {

		Expert source = expertMapper.selectExpertAndComposition(expertId);
		ExpertDto target = new ExpertDto();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	/**
	 * 查询所有的专家和著作
	 * 
	 * @return
	 */
	public List<ExpertDto> selectAllExpertAndComposition() {
		List<Expert> source = expertMapper.selectAllExpertAndComposition();
		List<ExpertDto> target = new ArrayList<ExpertDto>();
		for (Expert expert : source) {
			ExpertDto expertDto = new ExpertDto();
			BeanUtils.copyProperties(expert, expertDto);
			target.add(expertDto);
		}
		return target;
	}

	/**
	 * 查询前20个专家姓名
	 * 
	 * @return
	 */
	public List<Map<Long, String>> selectAllExpertName() {
		List<Map<Long, String>> result = new ArrayList<>();
		Map<Long, String> map = new LinkedHashMap<Long, String>();
		;
		List<Map<String, Object>> lists = expertMapper.selectAllExpertName();
		if (lists != null) {
			for (Map<String, Object> map2 : lists) {
				Set<String> keySet = map2.keySet();
				Long expertId = null;
				String realName = null;
				for (String key : keySet) {
					if ("expertId".equals(key)) {
						expertId = (Long) map2.get(key);
					}
					if ("realName".equals(key)) {
						realName = (String) map2.get(key);
					}
				}
				map.put(expertId, realName);
			}
		}
		result.add(map);
		return result;
	}

	/**
	 * 查询所有专家信息
	 * 
	 * @return
	 */
	public List<ExpertDto> selectAllExpert() {

		List<Expert> source = expertMapper.selectAllExpert();
		List<ExpertDto> target = new ArrayList<ExpertDto>();
		for (Expert expert : source) {
			ExpertDto expertDto = new ExpertDto();
			BeanUtils.copyProperties(expert, expertDto);
			target.add(expertDto);
		}

		return target;

	}

	/**
	 * 分页查询所有专家和著作
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<ExpertDto> selectAllExpertAndComposition(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Expert> list = expertMapper.selectAllExpertAndComposition();
		List<ExpertDto> listDto = new ArrayList<ExpertDto>();
		for (Expert expert : list) {
			ExpertDto expertDto = new ExpertDto();
			BeanUtils.copyProperties(expert, expertDto);
			listDto.add(expertDto);
		}
		PageInfo<ExpertDto> pageInfo = new PageInfo<>(listDto);
		return pageInfo;
	}

	/**
	 * 查询推荐专家和著作
	 * 
	 * @return
	 */
	public ExpertDto selectRecommendExpertAndComposition() {
		Expert source = expertMapper.selectRecommendExpertAndComposition();
		ExpertDto target = new ExpertDto();
		if (source != null) {
			BeanUtils.copyProperties(source, target);
		}
		return target;
	}

	/**
	 * 模糊查询：根据姓名查询专家和著作
	 * 
	 * @param realName
	 * @return
	 */
	public PageInfo<ExpertDto> selectExpertAndCompositionByName(int pageNum, int pageSize, String realName) {
		PageHelper.startPage(pageNum, pageSize);
		List<Expert> list = null;
		if (realName == null)
			list = expertMapper.selectAllExpertAndComposition();
		else
			list = expertMapper.selectExpertAndCompositionByName(realName);

		List<ExpertDto> listDto = new ArrayList<ExpertDto>();
		for (Expert expert : list) {
			ExpertDto expertDto = new ExpertDto();
			BeanUtils.copyProperties(expert, expertDto);
			listDto.add(expertDto);
		}
		PageInfo<ExpertDto> pageInfo = new PageInfo<>(listDto);

		return pageInfo;
	}

	/**
	 * 条件查询:根据分类，姓名查询专家
	 * 
	 * @param expertClassify
	 * @param realName
	 * @return
	 */
	public PageInfo<Expert> selectByClassifyAndRealName(Integer pageNum, Integer pageSize, String expertClassify,
			String realName) {
		PageHelper.startPage(pageNum, pageSize);
		List<Expert> list = expertMapper.selectByClassifyAndRealName(expertClassify, realName);
		PageInfo<Expert> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public int deleteExpertByUserId(Long userId) {
		Expert expert = new Expert();
		expert.setUserId(userId);
		return expertMapper.delete(expert);
	}

}
