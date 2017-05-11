package com.ytwl.cms.themeFund.common.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName SendQQEmailUtil
 * @Description 发送邮件
 * @author hudaqiang
 * @Date 2017年4月13日 下午3:44:57
 * @version 1.0.0
 */
public class SendQQEmailUtil {
private final static Logger logger = LoggerFactory.getLogger(SendQQEmailUtil.class);
	
	static Set<String> emailSet = new LinkedHashSet<>();
    static{
    	emailSet.add("396877565@qq.com");
    }
	
	public static void sendEmail(String title,String content){
		try{
			for (String qqUrl : emailSet) {
				sendEmail(qqUrl,title,content);
			}
		} catch (Exception e){
			logger.error("邮件发送异常");
		}
	}
	
	public static void sendEmail(String title,String content,Set<String> emailSet){
		try{
			for (String qqUrl : emailSet) {
				sendEmail(qqUrl,title,content);
			}
		} catch (Exception e){
			logger.error("邮件发送异常");
		}
	}
	
	public  static String sendEmail(String qqUrl,String title,String content){
		new Thread(new SendQQEmailThread(qqUrl, title, content)).start();
		return "已经执行发送邮件命令";
	}
	
	
	public static void main(String[] args) {
		sendEmail("396877565@qq.com", "qq邮箱测试", "yeah,you succeed!");
	}
}
