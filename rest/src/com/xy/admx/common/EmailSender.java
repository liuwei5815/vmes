package com.xy.admx.common;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class EmailSender {
	public static final String charset = "GBK";
	public static final String defaultMimetype = "text/html";

	private String smtphost = SysConfig.getStrValue("mail.server");
	private String username = SysConfig.getStrValue("mail.from.username");
	private String password = SysConfig.getStrValue("mail.from.password");
	private String from = SysConfig.getStrValue("mail.from");

	public EmailSender(){
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param receiver
	 *            收件人
	 * @param subject
	 *            标题
	 * @param mailContent
	 *            邮件内容
	 * @param mimetype
	 *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
	 */
	public void send(String receiver, String subject, String mailContent) {
		send(new String[] { receiver }, subject, mailContent);
	}

	/**
	 * 发送邮件
	 * 
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            标题
	 * @param mailContent
	 *            邮件内容
	 * @param mimetype
	 *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
	 */
	public void send(String[] receivers, String subject, String mailContent) {
		send(receivers, subject, mailContent, null, defaultMimetype);
	}
	
	public void send(String[] receivers, String subject) {
		send(receivers, subject, subject, null, defaultMimetype);
	}

	/**
	 * 发送邮件
	 * 
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            标题
	 * @param mailContent
	 *            邮件内容
	 * @param attachements
	 *            附件
	 * @param mimetype
	 *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
	 */
	public void send(String[] receivers, String subject, String mailContent,
			File[] attachements, String mimetype) {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtphost);// smtp服务器地址
		props.put("mail.smtp.auth", "true");// 需要校验
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);// 登录用户名/密码
					}
				});
		session.setDebug(false);
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(from));// 发件人邮件

			InternetAddress[] toAddress = new InternetAddress[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				toAddress[i] = new InternetAddress(receivers[i]);
			}
			mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);// 收件人邮件
			mimeMessage.setSubject(subject, charset);

			Multipart multipart = new MimeMultipart();
			// 正文
			MimeBodyPart body = new MimeBodyPart();
			// body.setText(message, charset);不支持html
			body.setContent(mailContent,(mimetype != null && !"".equals(mimetype) ? mimetype: defaultMimetype) + ";charset=" + charset);
			multipart.addBodyPart(body);// 发件内容
			// 附件
			if (attachements != null) {
				for (File attachement : attachements) {
					MimeBodyPart attache = new MimeBodyPart();
					// ByteArrayDataSource bads = new
					// ByteArrayDataSource(byte[],"application/x-any");
					attache.setDataHandler(new DataHandler(new FileDataSource(
							attachement)));
					String fileName = getLastName(attachement.getName());
					attache.setFileName(MimeUtility.encodeText(fileName,
							charset, null));
					multipart.addBodyPart(attache);
				}
			}
			mimeMessage.setContent(multipart);
			// SimpleDateFormat formcat = new SimpleDateFormat("yyyy-MM-dd");
			mimeMessage.setSentDate(new Date());// formcat.parse("2010-5-23")
			Transport.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  String getLastName(String fileName) {
		int pos = fileName.lastIndexOf("\\");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		pos = fileName.lastIndexOf("/");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		return fileName;
	}

	public String getSmtphost() {
		return smtphost;
	}

	public void setSmtphost(String smtphost) {
		this.smtphost = smtphost;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public static void main(String[] args) {
		String mailContent;
		try {
			mailContent = FileUtil.readFile2String("C:/workspace/IFundIT/hj/WebContent/WEB-INF/email_template/notification.html","UTF-8");
			String title = mailContent.split("<title>")[1].split("</title>")[0];
			title = title.replace("${name}", "张三");
			mailContent = mailContent.replace("${detail}", "<pre>ssssfsfwfwwew</pre>");
			
			EmailSender sender = new EmailSender();
			sender.send("365917878@qq.com", title, mailContent);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
