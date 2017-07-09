package com.jz.crm.base.email;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.exception.ApplicationException;
import com.jz.crm.base.util.ResourceUitl;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
public class SimpleMailSender {
	public static final Logger LOG = LoggerFactory.getLogger(SimpleMailSender.class);

	/**
	 * 发送邮件,不带附近
	 * 
	 * @param toEmail
	 *            需要发送的邮箱地址
	 * @param fileName
	 *            需要发送邮箱内容的文件地址
	 * @param parameter
	 *            正文和标题要替换的变量
	 * @throws Exception
	 */
	public static void sendMail(String toEmail, String fileName, Map<String, String> parameter) throws Exception {
		sendMail(toEmail, fileName, parameter, null);
	}

	/**
	 * 发送邮件，带附件
	 * 
	 * @param toEmail
	 *            需要发送的邮箱地址
	 * @param fileName
	 *            需要发送邮箱内容的文件地址
	 * @param parameter
	 *            正文和标题要替换的变量
	 * @param attachFileNames
	 *            附件路径
	 * @throws Exception
	 */
	public static void sendMail(String toEmail, String fileName, Map<String, String> parameter, String[] attachPaths)
			throws Exception {
		MailSenderInfo mailInfo = MailSenderInfo.getInstance();
		mailInfo.setToAddress(toEmail);
		String mailContext = ResourceUitl.getMailContent(fileName);
		Assert.hasLength(mailContext, "文件不存在[" + fileName + "]");
		mailContext = mailContext.replaceAll("/n", "\n");
		if (parameter != null) {
			for (Map.Entry<String, String> entry : parameter.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					mailContext = mailContext.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
				}
			}
		}
		String[] fileContext = mailContext.split("\n");
		if (fileContext.length == 1) {
			mailInfo.setSubject(ConstantContext.DEFAULT_COMPANY);
			mailInfo.setContent(fileContext[0]);
		} else {
			mailInfo.setSubject(fileContext[0]);
			StringBuilder content = new StringBuilder();
			for (int i = 1; i < fileContext.length; i++) {
				content.append(fileContext[i] + "\n");
			}
			mailInfo.setContent(content.toString());
		}
		mailInfo.setAttachFileNames(attachPaths);

		boolean isSendSuccess = sendTextMail(mailInfo);// 发送文体格式
		if (!isSendSuccess) {
			throw new ApplicationException(SystemCode.MAIL_SEND_FAILURE, "邮件发送失败");
		}
	}

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 * @throws Exception
	 */
	public static boolean sendTextMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());

		// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
		Multipart multipart = new MimeMultipart();
		// 添加邮件正文
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(mailInfo.getContent(), "text/html;charset=UTF-8");
		multipart.addBodyPart(contentPart);

		if (mailInfo.getAttachFileNames() != null && mailInfo.getAttachFileNames().length > 0) {
			String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
			for (String attachment : mailInfo.getAttachFileNames()) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				String filePath = (attachment.indexOf(ConstantContext.RESOURCE_CLASSPATH) == -1) ? attachment
						: (path + ResourceUitl.extract(attachment));
				File file = new File(filePath);
				DataSource source = new FileDataSource(file);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");

				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}
		}

		// 将multipart对象放到message中
		mailMessage.setContent(multipart);
		// 保存邮件
		mailMessage.saveChanges();

		// 发送邮件
		Transport.send(mailMessage);
		return true;
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件信息
	 * @throws Exception
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	}

	public static void main(String[] args) throws Exception {
		// 这个类主要是设置邮件
		// MailSenderInfo mailInfo = new MailSenderInfo();
		// mailInfo.setMailServerHost("smtp.exmail.qq.com");
		// mailInfo.setMailServerPort("250");
		// mailInfo.setUserName("service@shujujia.org");
		// mailInfo.setPassword("Service@2016");// 您的邮箱密码
		// mailInfo.setFromAddress("service@shujujia.org");
		//
		// mailInfo.setToAddress("lizeyi@shujujia.org");
		// mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");
		// mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
		// // 这个类主要来发送邮件
		// SimpleMailSender sms = new SimpleMailSender();
		// sms.sendTextMail(mailInfo);// 发送文体格式
		// // sms.sendHtmlMail(mailInfo);//发送html格式

	}

}
