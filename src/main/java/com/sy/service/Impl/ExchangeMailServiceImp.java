package com.sy.service.Impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

import com.sy.dao.MailDao;
import com.sy.service.ExchangeMailService;
import com.sy.tool.Constant;
import com.sy.tool.ReadFromFile;

@Service
public class ExchangeMailServiceImp implements ExchangeMailService{
	private Logger log = LoggerFactory.getLogger(ExchangeMailServiceImp.class);
	 //用户认证信息
	private ExchangeCredentials credentials =null;
		
	@Autowired
	private MailDao mailDao;
	
	/**
	 * @param user
	 * @param	password
	 * 用户认证
	 * */

	public void Certified(String user, String password) {
		log.info("发件人："+user);	
		log.info("发件人密码："+password);
		try{			
			credentials = new WebCredentials(user, password);		
			log.info("帐号认证成功");
		}catch(Exception e){
			log.info("帐号认证失败");
		}
		
	}

	/**
	 * @param max
	 * @return ArrayList<EmailMessage>
	 * 收取部分邮件
	 * */
	public ArrayList<EmailMessage> receive(int max) {
		log.info("收取"+max+"封邮件");
		return receive(max,null);
	}

	/**
	 * 收取所有邮件
	 * @return ArrayList<EmailMessage>
	 * */
	public ArrayList<EmailMessage> receive() {
		log.info("收取所有邮件");
		 return receive(0, null);
	}

	/**
	 * @param	subject	邮件标题
	 * @param	to	收件人列表(如果包含多个人，用","号分开)
	 * @param	cc	抄送人列表(如果包含多个人，用","号分开)
	 * @param	bodyText 邮件内容
	 * @param	attachmentPaths 附件路径(例：E:abc.txt)
	 * 发送带附件邮件
	 * */
	public Integer send(String subject, String to_mail, String cc_mail, String content,String[] attachmentPaths) {
		int count=0;
		log.info("******邮件发送中******");
		log.info("邮件标题："+subject);	
		log.info("收件人列表："+to_mail);
		log.info("抄送人列表："+cc_mail);
		log.info("邮件内容："+content);
		try {
			ExchangeService service = getExchangeService();
			EmailMessage msg = new EmailMessage(service);
			msg.setSubject(subject);
	        MessageBody body = MessageBody.getMessageBodyFromText(content);
	        body.setBodyType(BodyType.HTML);
	        msg.setBody(body);
	        //收件人
	        if(to_mail.contains(";")){
	        	String [] to=to_mail.split(";");
		        for (String toPerson : to) {
		        	msg.getToRecipients().add(toPerson);
		        }
	        }else{
	        	msg.getToRecipients().add(to_mail);
	        }
	        //抄送人
	        if (cc_mail != null) {
	        	if(cc_mail.contains(";")){
	        		String [] cc=cc_mail.split(";");
	        		for (String ccPerson : cc) {
	        			msg.getCcRecipients().add(ccPerson);
	        		}
	        	}else{
	        		msg.getCcRecipients().add(cc_mail);
	        	}
	        }
	        if (attachmentPaths != null) {
	            for (String attachmentPath : attachmentPaths) {
	                msg.getAttachments().addFileAttachment(attachmentPath);
	            }
	        }
	        msg.send();
	        count=mailDao.insertMail(subject, to_mail, cc_mail, content);
	        log.info("******邮件发送完毕******");
		} catch (Exception e) {
			log.error("邮件发送异常");
			e.printStackTrace();
		}
        return count;
	}

	/**
	 * @param	subject	邮件标题
	 * @param	to	收件人列表(如果包含多个人，用","号分开)
	 * @param	cc	抄送人列表(如果包含多个人，用","号分开)
	 * @param	bodyText 邮件内容
	 * 发送不带附件邮件
	 * */
	public Integer send(String subject, String to_mail, String cc_mail, String content){
		log.info("发送不带附件的邮件：");
		int count=send(subject, to_mail, cc_mail, content, null);
		return count;
	}
	

	/**
     * 创建邮件服务
     *
     * @return 邮件服务
     */
    private ExchangeService getExchangeService() {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI(Constant.mailServer));
        } catch (URISyntaxException e) {
        	log.error("创建邮件服务异常");
            e.printStackTrace();
        }
        return service;
    }
    /**
     * 收取邮件
     *
     * @param max          最大收取邮件数
     * @param searchFilter 收取邮件过滤规则
     * @return
     * @throws Exception
     */
    private ArrayList<EmailMessage> receive(int max, SearchFilter searchFilter) {
    	ArrayList<EmailMessage> result = new ArrayList<>();
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);       
        service.setCredentials(credentials);
        try {
			service.setUrl(new URI(Constant.mailServer));
			//绑定收件箱,同样可以绑定发件箱
	        Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
	        //获取文件总数量
	        int count = inbox.getTotalCount();
	        if (max > 0) {
	            count = count > max ? max : count;
	        }
	        //循环获取邮箱邮件
	        ItemView view = new ItemView(count);
	        //按照时间顺序收取
	        view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
	        FindItemsResults<Item> findResults;
	        if (searchFilter == null) {
	            findResults = service.findItems(inbox.getId(), view);
	        } else {
	            findResults = service.findItems(inbox.getId(), searchFilter, view);
	        }
	        service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
	        
	        for (Item item : findResults.getItems()) {
	            EmailMessage message = (EmailMessage) item;
	            result.add(message);
	        }
		}catch (Exception e) {
			log.error("收取邮件异常");
			e.printStackTrace();
		}
        return result;
        
    }

	
    public  static void main(String args[]) throws ServiceLocalException{    	
    	String file="E:\\Users\\test-output\\TestNGReport.html";
    	ExchangeMailServiceImp em= new ExchangeMailServiceImp();
    	em.Certified("zhangsan", "Luo20180704");
    	//发送邮件
    	String content=ReadFromFile.readTxtFile(file);
    	String[] ss = new String[]{file};
//    	em.send("testaaaa","zhangsan@163.com", "zhangsan@163.com", content);
    	em.send("testaaaa","zhangsan@163.com", "zhangsan@163.com", content,ss);
    	// 接收邮件
//        ArrayList<EmailMessage> mails = em.receive(1);
//        for (EmailMessage mail : mails) {
//        	System.out.println("邮件标题: " + mail.getSubject());
//            System.out.println("接收时间: " + mail.getDateTimeReceived());
//            System.out.println("发送人: " + mail.getFrom().getName() + ", 地址: " + mail.getFrom().getAddress());
//            System.out.println("已读:" + mail.getIsRead());
//          System.out.println("邮件内容 :" + mail.getBody());
//        }
    }
}

