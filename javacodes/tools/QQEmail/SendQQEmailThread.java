package com.ytwl.cms.themeFund.common.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * @ClassName SendQQEmailThread
 * @Description 发送邮件线程
 * @author hudaqiang
 * @Date 2017年4月13日 下午3:48:14
 * @version 1.0.0
 */
public class SendQQEmailThread implements Runnable {
	
	private String qqUrl;
	private String title;
	private String content;
	public SendQQEmailThread(String qqUrl, String title, String content) {
		super();
		this.qqUrl = qqUrl;
		this.title = title;
		this.content = content;
	}

	@Override
	public void run() {
		//创建属性对象
		Properties props=new Properties();
		props.put("mail.transport.protocol","smtp");
		props.put("mail.smtp.host","smtp.qq.com");
		props.put("mail.smtp.port","587");
		props.put("mail.smtp.auth", "true");
		
		Session session=Session.getInstance(props);
		session.setDebug(true);
		Message mess=new MimeMessage(session);
		try {
			mess.setFrom(new InternetAddress("396877565@qq.com"));
			mess.setRecipient(RecipientType.TO, new InternetAddress(qqUrl));//收件人
			mess.setSubject(title);
			mess.setText(content);
			Transport trans = session.getTransport();
			trans.connect("smtp.qq.com","396877565@qq.com","noilfjpipacxbhjc");//这里是发件人的邮箱用户名和密码（我的密码就先隐藏啦）
			trans.sendMessage(mess,mess.getAllRecipients());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
