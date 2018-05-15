package com.lgsc.cjbd.book.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.book.dto.MultifileDTO;
import com.lgsc.cjbd.book.model.Multifile;
import com.lgsc.cjbd.book.service.MultifileService;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 多媒体
 * 
 * @author Pengmeiyan
 *
 */
@Api(tags = "multifile", description = "多媒体")
@RestController
@RequestMapping("/book/multifile")
public class MultifileController {

	@Autowired
	private MultifileService multifileService;

	/**
	 * 根据id查询多媒体
	 */
	@ApiOperation(value = "根据id查询多媒体")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "多媒体编号", required = true) @PathVariable Long id) {
		return new Response().success(multifileService.selectById(id));
	}

	/**
	 * 查询多媒体
	 */
	@ApiOperation(value = "查询多媒体")
	@RequestMapping(value = "/queryBy", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectBy(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "父级菜单编号", required = false) @RequestParam(name = "parentMenuId", required = false) Long parentMenuId,
			@ApiParam(value = "子级菜单编号", required = false) @RequestParam(name = "childMenuId", required = false) Long childMenuId,
			@ApiParam(value = "关键词", required = false) @RequestParam(name = "keyword", required = false) String keyword,
			@ApiParam(value = "资源类型", required = false) @RequestParam(name = "multiType", required = false) String multiType) {
		PageInfo<Map<String, Object>> selectBy = multifileService.selectBy(pageNum, pageSize, parentMenuId, childMenuId,
				keyword, multiType);
		if (selectBy == null) {
			return new Response().failure();
		}
		return new Response().success(selectBy);
	}
	
	/**
	 * 查询视频看点
	 */
	@ApiOperation(value = "查询视频看点")
	@RequestMapping(value = "/queryIndexVideo", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectIndexVideo() {
		Map<String, Object> selectBy = multifileService.selectIndexVideo();
		if (selectBy == null) {
			return new Response().failure();
		}
		return new Response().success(selectBy);
	}

	/**
	 * 查询所有多媒体
	 */
	@ApiOperation(value = "查询所有多媒体")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(multifileService.selectAll(pageNum, pageSize));
	}

	/**
	 * 根据菜单id分页查询所有的图片文件夹
	 * 
	 * @param menuPId
	 * @param menuId
	 * @return
	 */
	@ApiOperation(value = "根据菜单id分页查询所有的图片文件夹")
	@RequestMapping(value = "/selectPicByMeauId", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectPicByMeauId(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "一级菜单", required = true) @RequestParam(name = "menuPId", required = true) Long menuPId,
			@ApiParam(value = "二级菜单", required = false) @RequestParam(name = "menuId", required = false) Long menuId) {
		return new Response().success(multifileService.selectPicByMeauId(pageNum, pageSize, menuPId, menuId));
	}

	/**
	 * 新增多媒体
	 * @throws UnsupportedEncodingException 
	 */
	@ApiOperation(value = "新增多媒体")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertMultifile(@ApiParam(value = "多媒体", required = true) @RequestBody @Validated MultifileDTO multifileDTO) throws UnsupportedEncodingException {
		Response response = new Response();
		Multifile multifile = new Multifile();
		BeanUtils.copyProperties(multifileDTO, multifile, "path","menuId");
		int num = multifileService.insertMultifile(multifile, multifileDTO.getMenuId(), multifileDTO.getPath());
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 修改多媒体
	 */
	@ApiOperation(value = "修改多媒体")
	@RequestMapping(value = "/modify", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateMultifile(@ApiParam(value = "多媒体", required = true) @RequestBody @Validated MultifileDTO multifileDTO) {
		Response response = new Response();
		Multifile multifile = new Multifile();
		BeanUtils.copyProperties(multifileDTO, multifile, "path","menuId");
		int num = multifileService.updateMultifile(multifile, multifileDTO.getMenuId(), multifileDTO.getPath());
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 根据id删除多媒体
	 */
	@ApiOperation(value = "根据id删除多媒体")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "多媒体id", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = multifileService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 查询所有相册内某个相册的前一相册和后一相册
	 * 
	 * @param menuPId
	 * @param menuId
	 * @param multifileId
	 * @return
	 */
	@ApiOperation(value = "查询所有相册内某个相册的前一相册和后一相册")
	@RequestMapping(value = "/selectFirstAndLastPic", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectFirstAndLastPic(
			@ApiParam(value = "菜单menuId", required = false) @RequestParam(name = "menuId", required = false) Long menuId,
			@ApiParam(value = "相册id", required = true) @RequestParam(name = "multifileId", required = true) Long multifileId) {
		return new Response().success(multifileService.selectFirstAndLastMultifile(menuId ,multifileId));
	}
}
