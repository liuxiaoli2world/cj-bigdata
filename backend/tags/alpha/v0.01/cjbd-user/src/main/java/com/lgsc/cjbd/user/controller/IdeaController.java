package com.lgsc.cjbd.user.controller;

import java.util.Date;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.lgsc.cjbd.user.model.Idea;
import com.lgsc.cjbd.user.service.IdeaService;
import com.lgsc.cjbd.user.util.MailUtil;
import com.lgsc.cjbd.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 意见箱控制层
 * @author
 */
@Api(tags = "idea", description = "意见箱")
@RestController
@RequestMapping("/user/idea")
public class IdeaController {

	@Autowired
	private IdeaService ideaService;
	@Value("${spring.mail.username}")
	private String sendFrom;

	@Autowired
	private JavaMailSender javaMailSender;
	
	private Logger logger = LogManager.getLogger(IdeaController.class);

	/**
	 * 查询留言详细
	 */
	@ApiOperation(value = "查询留言详细")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response select(@ApiParam(value = "留言的编号", required = true) @PathVariable Long id) {
		return new Response().success(ideaService.selectByPrimaryKey(id));
	}

	/**
	 * 
	 */
	@ApiOperation(value = "查询所有留言")
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response selectAll(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页的数量", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		return new Response().success(ideaService.selectAll(pageNum, pageSize));
	}

	/**
	 * 用户留言
	 */
	@ApiOperation(value = "新增意见")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response insertSelective(@ApiParam(value = "意见", required = true) @RequestBody @Validated Idea record) {
		record.setStatus(Short.parseShort("0"));
		record.setCreatedAt(new Date());
		Response response = new Response();
		int num = ideaService.insertSelective(record);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 回复留言
	 */
	@ApiOperation(value = "回复意见")
	@RequestMapping(value = "/reply", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response updateSelective(
			@ApiParam(value = "留言的编号", required = true) @RequestParam(name = "ideaId") Long idea_id,
			@ApiParam(value = "回复", required = true) @RequestParam(name = "reply") String reply) {
		Response response = new Response();
		Idea idea = ideaService.selectByPrimaryKey(idea_id);
		int num = 0;
		if (idea != null) {
			idea.setStatus(Short.parseShort("1"));
			idea.setReply(reply);
			idea.setUpdatedAt(new Date());
			num = ideaService.updateByPrimaryKeySelective(idea);
			try {
				new MailUtil(sendFrom, javaMailSender).SendMail("回复意见", idea.getReply(), idea.getUserEmail());
			} catch (Exception e) {
				logger.error("发送邮件失败！！！");
			}
		}
		if (num > 0) {
			return response.success();
		} else {
			return response.failure("回复留言失败");
		}
	}

	/**
	 * 发送回复留言邮件的邮件
	 * 
	 * @param record
	 * @return
	 */
	@ApiOperation(value = "发送回复意见的邮件(暂时不使用)")
	@RequestMapping(value = "/sendReplyEmail/{ideaId}", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response sendReplyEmail(
			@ApiParam(value = "需要发送邮件意见的id", required = true) @PathVariable(name = "ideaId") Long ideaId) {
		Response response = new Response();
		Idea record = ideaService.selectByPrimaryKey(ideaId);
		if (record != null) {
			try {
				new MailUtil(sendFrom, javaMailSender).SendMail("回复意见", record.getReply(), record.getUserEmail());
				return response.success();
			} catch (MessagingException e) {
				logger.error("邮箱发送出现异常");
				e.printStackTrace();
				return response.failure();

			}
		} else {

			return response.failure();
		}

	}

	/**
	 * 删除意见
	 * 
	 */
	@ApiOperation(value = "删除意见")
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public Response delete(@ApiParam(value = "意见的编号", required = true) @PathVariable Long id) {
		Response response = new Response();
		int num = ideaService.deleteByPrimaryKey(id);
		if (num > 0) {
			response.success();
		} else {
			response.failure();
		}
		return response;
	}

	/**
	 * 后台查询意见
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param satues
	 *            0 查询全部， 1 未回复留言 2 已回复留言
	 * @param keyWord
	 *            不传代表不按关键字查询，传了代表需要按照关键字查询
	 * @return
	 */
	@ApiOperation(value = "后台查询意见")
	@RequestMapping(value = "/queryIdeaForAdmin", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Response selctMessageForAdmin(
			@ApiParam(value = "当前页", required = true) @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
			@ApiParam(value = "每页多少条", required = true) @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
			@ApiParam(value = "查询条件（0 查询全部， 1 未回复留言 2 已回复留言", required = false) @RequestParam(name = "status", defaultValue = "0") Integer status,
			@ApiParam(value = "关键词(不传就是查询所有)", required = false) @RequestParam(name = "keyWord", defaultValue = "") String keyWord) {
		PageInfo<Idea> pageInfo = ideaService.selectIdeaForAdmin(pageNum, pageSize, status, keyWord);
		if (pageInfo != null) {
			return new Response().success(pageInfo);
		} else {
			return new Response().failure();
		}

	}

}
