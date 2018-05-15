package com.lgsc.cjbd.user.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 发送邮件工具类
 * @author 罗天宇
 *
 */
public final class MailUtil {

	public String sentFrom;
	public JavaMailSender javaMailSender;
	public static final Logger logger = LogManager.getLogger(MailUtil.class);

	public MailUtil(String sentFrom, JavaMailSender javaMailSender) {
		super();
		this.sentFrom = sentFrom;
		this.javaMailSender = javaMailSender;
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 * @param content
	 * @param sendTo
	 * @throws MessagingException
	 */
	public void SendMail(String subject, String content, String sendTo) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(sentFrom);
		helper.setTo(sendTo);
		helper.setSubject(subject);

		StringBuffer sb = new StringBuffer();
		sb.append("<h1>您好</h1>").append(content);
		helper.setText(sb.toString(), true);
		javaMailSender.send(message);
		logger.info("发送邮件成功");
	}

}
