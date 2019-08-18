package com.technicalinterest.group.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
/**
 * @package: com.technicalinterest.group.service
 * @className: MailService
 * @description: 邮箱发送
 * @author: Shuyu.Wang
 * @date: 2019-08-04 20:16
 * @since: 0.1
 **/

@Service
@Slf4j
public class MailService {


	@Autowired
	private JavaMailSender sender;

	@Value("${spring.mail.username}")
	private String from;

	/**
	 * 发送纯文本的简单邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	public void sendSimpleMail(String to, String subject, String content){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		try {
			sender.send(message);
			log.info("测试邮件。");
		} catch (Exception e) {
			log.error("测试邮件发生异常！", e);
		}
	}

	/**
	 * 发送html格式的邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	@Async
	public void sendHtmlMail(String to, String subject, String content){
		MimeMessage message = sender.createMimeMessage();
		try {
			//true表示需要创建一个multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(getMtml(to, subject, content), true);
			sender.send(message);
			log.info("html邮件已经发送。");
		} catch (Exception e) {
			log.error("发送html邮件时发生异常！", e);
		}
	}

	/**
	 * 发送带附件的邮件
	 * @param to
	 * @param subject
	 * @param content
	 * @param filePath
	 */
	public void sendAttachmentsMail(String to, String subject, String content, String filePath){
		MimeMessage message = sender.createMimeMessage();

		try {
			//true表示需要创建一个multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(getMtml(to, subject, content), true);

			FileSystemResource file = new FileSystemResource(new File(filePath));
			String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
			helper.addAttachment(fileName, file);

			sender.send(message);
			log.info("带附件的邮件已经发送。");
		} catch (Exception e) {
			log.error("发送带附件的邮件时发生异常！", e);
		}
	}

	/**
	 * 发送嵌入静态资源（一般是图片）的邮件
	 * @param to
	 * @param subject
	 * @param content 邮件内容，需要包括一个静态资源的id，
	 * @param rscPath 静态资源路径和文件名
	 * @param rscId 静态资源id
	 */
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
		MimeMessage message = sender.createMimeMessage();

		try {
			//true表示需要创建一个multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			FileSystemResource res = new FileSystemResource(new File(rscPath));
			helper.addInline(rscId, res);

			sender.send(message);
			log.info("嵌入静态资源的邮件已经发送。");
		} catch (Exception e) {
			log.error("发送嵌入静态资源的邮件时发生异常！", e);
		}
	}

	private String getMtml(String eamil, String title, String content){
		StringBuffer sb = new StringBuffer();
		sb.append("<div style=\"position:relative;width:400px;margin:0 auto; background:#f7f7f7;color:#999999; font-size:14px;line-height:36px;\">");
		sb.append("<div style=\"height:60px; border-bottom:2px solid #6f5499;padding:10px;\" >");
		sb.append("<div style=\"float:left;margin-left:10px; line-height:60px;font-size:18px;font-weight:bold;color:#555;width:360px;height:60px;overflow:hidden;text-align:left;\">");
		sb.append( title );
		sb.append("</div></div><div style=\"padding:20px;min-height:260px;white-space: pre-wrap;word-wrap: break-word;\">");
		sb.append(content);
		sb.append("</div><div style=\"padding:20px;text-align:right;margin-top:30px;\">");
		sb.append("<a style=\"color:#6f5499;\" href=\"http://www.littletree.xyz/api/?sj="
				+ System.currentTimeMillis() + "\">本网站由技术兴趣小组Api提供技术与支持</a>");
		sb.append("<br></div></div>");
		return sb.toString();
	}
}
