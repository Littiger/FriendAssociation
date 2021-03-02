package com.quifeng.utils.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

/**
 * @desc   邮件发送的工具类
 * @author JZH
 * @time   2020-12-15
 */
public class EmailUtils {
	
	// 发件人邮箱
	private static String sendEmailAccount="1147981135@qq.com";
	// 发件人邮箱授权码
	private static String sendEmailPwd="efjzehhqudcfgiih";
	
//    /**
//     * 初始化配置文件
//     */
//    static{
//    	try {
//    		//读取配置文件
//			Map mail = PropertyUtils.getyInfoOfPropert("/com/quifeng/utils/email/mail.properties");
//			sendEmailAccount = (String) mail.get("emailFrom");
//			sendEmailPwd = (String) mail.get("emialFromAuthorization");
//			
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
//    }
    
    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {
		EmailUtils.createMimeMessage("1147981135@qq.com", "1234", new Date());
	}
    
	/**
	 * 发送包含验证码的邮件
	 * @param receiveMail 收件人
	 * @param code 验证码
	 * @param date 发送时间
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public static void createMimeMessage(String receiveMail,String code,Date date) throws UnsupportedEncodingException, MessagingException{
		//获取session
		Session session = getSession();
	  	
	  	// 3. 创建一封邮件
	  	MimeMessage message=new MimeMessage(session);
	   
	  	//4.发件人
	  	message.setFrom(new InternetAddress(sendEmailAccount, "验证码发布者 ", "UTF-8"));
	   
	  	//5.收件人
	  	message.setRecipient(RecipientType.TO, new InternetAddress(receiveMail, "接受者：xx用户", "UTF-8"));
	  	//message.setRecipient(RecipientType.CC, new InternetAddress(sendEmailAccount, "抄送给自己的", "utf-8"));
	  	//6.设定主题
	  	message.setSubject("【校友汇】验证码小助手","UTF-8");
	   
	  	//7.设定正文
	  	message.setContent("您正在使用邮箱注册【校友汇】，验证码为： "+code+"  若非本人操作，请忽视此邮件。", "text/html;charset=utf-8");
	  	
	  	
	  	//发送邮件
	  	setTimeSaveSendEmail(session, message, date);
		
	}
	
	/**
	 * 发送文本+图片/附件邮件
	 * @param receiveMail 收件人邮箱
	 * @param emailSubject 邮件主题
	 * @param url 附带文件url
	 * @param date 发送时间
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public static void sendTxtAndImgOrFile(String receiveMail,String emailSubject, String emailText, String url ,Date date) throws UnsupportedEncodingException, MessagingException{
		//获取session
		Session session = getSession();
		
	  	//判断后缀
	  	String houZhui = url.substring(url.lastIndexOf(".")+1);
	  	System.out.println(houZhui);
	  	
	  	//创建一封邮件
	  	MimeMessage message = new MimeMessage(session);
	  	//如果是图片
	  	if("bmp".equals(houZhui) || "gif".equals(houZhui) || "jpeg".equals(houZhui) || "jpg".equals(houZhui) || "png".equals(houZhui) || "x-pcx".equals(houZhui)){
	  		//发送图片
	  	    sendImage(receiveMail, emailSubject, emailText, url, message);
		}
	  	//非图片就是附件
	  	else{
	  		//发送附件
	  	    sendFile(receiveMail, emailSubject, emailText, url, message); 
	  	}
	  	
	  	//发送邮件
	  	setTimeSaveSendEmail(session, message, date);
		
	}
	
	/**
	 * 发送图片
	 * @param receiveMail
	 * @param emailSubject
	 * @param emailText
	 * @param url
	 * @param message
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private static void sendImage(String receiveMail, String emailSubject, String emailText, String url, MimeMessage message) throws MessagingException, UnsupportedEncodingException {
		//发件人
		message.setFrom(new InternetAddress(sendEmailAccount, "发件人的昵称", "UTF-8"));
		//To: 收件人（可以增加多个收件人、抄送、密送）
		//CC:抄送人，BCC:密送
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
		//邮件主题
		message.setSubject(emailSubject, "UTF-8");
		//Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
		MimeBodyPart txt = new MimeBodyPart();
		//要显示图片就用content不要用text   datahandler指向的资源 要加上cid:前缀 不然不显示
		txt.setContent(""+emailText+":这是一张图片\n<img src='cid:c.png' />","text/html;charset=UTF-8");
		
		MimeBodyPart img = new MimeBodyPart();
		DataHandler dh = new DataHandler(new FileDataSource(url));
		img.setDataHandler(dh);
		img.setContentID("c.png");
		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(txt);
		multipart.addBodyPart(img);
		multipart.setSubType("related");
		message.setContent(multipart);
	}
	/**
	 * 发送附件
	 * @param receiveMail
	 * @param emailSubject
	 * @param emailText
	 * @param url
	 * @param message
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	private static void sendFile(String receiveMail, String emailSubject, String emailText, String url, MimeMessage message) throws MessagingException, UnsupportedEncodingException {
		//From: 发件人
		message.setFrom(new InternetAddress(sendEmailAccount, "发件人的昵称", "UTF-8"));
		//To: 收件人
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
		//Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
		message.setSubject(emailSubject, "UTF-8");
		//处理附件（文本+附件）
		//先将文本的内容加入到mimebodypart中
		MimeBodyPart txt = new MimeBodyPart();
		txt.setContent(emailText, "text/html;charset=UTF-8");
		//设定附件
		MimeBodyPart attachment = new MimeBodyPart();
		//读取本地文件
		DataHandler dh2 = new DataHandler(new FileDataSource(url));
		//将附件数据添加到"节点"
		attachment.setDataHandler(dh2);
		//设置附件的文件名（需要编码）
		attachment.setFileName(MimeUtility.encodeText(dh2.getName()));       
		//设置正文为：（文本/文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(txt);
		mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
		mm.setSubType("mixed");         // 混合关系
		message.setContent(mm);
	}
	
	/**
	 * 8-13步封装，发送邮件
	 * @param session
	 * @param message
	 * @throws MessagingException
	 * @throws NoSuchProviderException
	 */
	private static void setTimeSaveSendEmail(Session session, MimeMessage message , Date date) throws MessagingException, NoSuchProviderException {
		//设定发送的时间
	  	message.setSentDate(date);
	  	//保存设置
	  	message.saveChanges();
	  	//根据session获取邮件发送对象transport
	    Transport transport = session.getTransport();
	    //连接上SMTP邮件发送的服务器基站
	    transport.connect(sendEmailAccount, sendEmailPwd);
	    //发送邮件
	    transport.sendMessage(message, message.getAllRecipients());
	    //关闭连接
	    transport.close();
	}
	
	/**
	 * 封装前两步,获取session用于发邮件
	 * @return
	 */
	private static Session getSession() {
		//创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties();
	  	props.setProperty("mail.transport.protocol","smtp");// 使用的协议（JavaMail规范要求）
	  	props.setProperty("mail.smtp.host","smtp.qq.com"); // 发件人的邮箱的 SMTP 服务器地址
	  	props.setProperty("mail.smtp.auth", "true");    // 需要请求认证
	  	//根据配置创建会话对象, 用于和邮件服务器交互
	  	Session session=Session.getInstance(props);
	  	//session.setDebug(true);  // 设置为debug模式, 可以查看详细的发送 log
		return session;
	}
	
	
}
