package com.lgsc.cjbd.expert.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.expert.dto.ExpertDto;
import com.lgsc.cjbd.expert.mapper.CompositionMapper;
import com.lgsc.cjbd.expert.model.Composition;
import com.lgsc.cjbd.remote.client.BookClient;

/**
 * 学术著作
 * 
 * @author Pengmeiyan
 *
 */
@Service
public class CompositionService {

	@Autowired
	private CompositionMapper compositionMapper;
	@Autowired
	private ExpertService expertService;
	@Autowired
	private BookClient bookClient;

	/**
	 * 将期刊导入到著作库 新增/修改专家期刊/删除专家著作
	 * 
	 * @param composition
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertComposition(Long expertId, List<Long> contentIds) {
		int flag = 0;// 1为将期刊导入到著作库
		int delFlag = 1;// 没有著作默认删除成功
		// 在著作表中查询该专家是否有著作，全部删掉
		List<Composition> compositions = compositionMapper.selectByExpertId(expertId);
		if (compositions != null) {
			for (Composition composition : compositions)
				delFlag = compositionMapper.deleteByPrimaryKey(composition.getCompositionId());
		}
		// 如果勾选期刊
		if (contentIds != null && contentIds.size() > 0) {
			// 根据专家id查出所有的期刊
			ExpertDto expert = expertService.selectByPrimaryKey(expertId);// 根据专家id查询专家姓名
			String author = expert != null ? expert.getRealName() : "";
			List<Map<String, Object>> lists = bookClient.selectPeriodicalByAuthor(author);

			if (lists != null && lists.size() > 0) {
				// 根据期刊id筛选出选择的期刊
				List<Map<String, Object>> periodicals = new ArrayList<Map<String, Object>>();
				if (contentIds != null && contentIds.size() > 0) {
					for (int i = 0; i < contentIds.size(); i++) {
						for (Map<String, Object> map : lists) {
							long periodicalId = Long.valueOf(String.valueOf(map.get("contentId")));
							if (contentIds.get(i) == periodicalId)
								periodicals.add(map);
						}
					}
				}
				if (periodicals != null) {
					// 把筛选出的期刊插入
					for (Map<String, Object> periodical : periodicals) {
						String title = (String) periodical.get("title");
						String contentId = String.valueOf(periodical.get("contentId"));
						Composition composition = new Composition();
						composition.setExpertId(expertId);
						composition.setCompositionName(title);
						composition.setCompositionLink(contentId);
						flag = compositionMapper.insertSelective(composition);
					}
				}
			}
			return flag;
		} else // 如果没有勾选期刊，默认删除专家著作
			return delFlag;
	}

	/**
	 * 根据id删除学术著作
	 * 
	 * @param compositionId
	 * @return
	 */
	public int deleteByPrimaryKey(long compositionId) {
		return compositionMapper.deleteByPrimaryKey(compositionId);
	}

	/**
	 * 根据著作id查询著作
	 * 
	 * @param compositionId
	 * @return
	 */
	public Composition selectByPrimaryKey(long compositionId) {
		return compositionMapper.selectByPrimaryKey(compositionId);
	}

	/**
	 * 根据专家编号查询著作
	 * 
	 * @param expertId
	 * @return
	 */
	public List<Composition> selectByExpertId(long expertId) {
		return compositionMapper.selectByExpertId(expertId);
	}

	/**
	 * 根据专家id查询期刊
	 * 
	 * @param expertId
	 * @return
	 */
	public List<Map<String, Object>> selectPeriodicalByExpertId(long expertId) {
		// 根据专家id查询专家姓名
		ExpertDto expert = expertService.selectByPrimaryKey(expertId);
		String author = expert.getRealName();
		// 根据专家姓名查询期刊
		List<Map<String, Object>> lists = bookClient.selectPeriodicalByAuthor(author);
		for (Map<String, Object> list : lists)
			list.put("expertId", expertId);
		return lists;
	}

	/**
	 * 查询所有著作
	 * 
	 * @return
	 */
	public List<Composition> selectAll() {
		return compositionMapper.selectAll();
	}

	/**
	 * 分页查询所有著作
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Composition> selectAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Composition> list = compositionMapper.selectAll();
		PageInfo<Composition> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

}
