package com.sy.tool;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.exception.service.remote.ServiceRequestException;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.Attachment;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

public class ExchangeMailUtil {
	 //用户认证信息
	private ExchangeCredentials credentials =null;
    /**
     * 登陆 
     * */
	public void mailLogin(String user, String password,String domain) {
		try{			
			if (domain == null) {
				credentials = new WebCredentials(user, password);           
			} else {
				credentials = new WebCredentials(user, password, domain);          
			}	
		}catch(Exception e){
			System.out.println(e);
		}

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
    public ArrayList<EmailMessage> receive(int max, SearchFilter searchFilter) throws Exception {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);       
        service.setCredentials(credentials);
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
            // e.g. new SearchFilter.SearchFilterCollection(
            // LogicalOperator.Or, new SearchFilter.ContainsSubstring(ItemSchema.Subject, "EWS"),
            // new SearchFilter.ContainsSubstring(ItemSchema.Subject, "API"))
            findResults = service.findItems(inbox.getId(), searchFilter, view);
        }
        service.loadPropertiesForItems(findResults, PropertySet.FirstClassProperties);
        ArrayList<EmailMessage> result = new ArrayList<>();
        for (Item item : findResults.getItems()) {
            EmailMessage message = (EmailMessage) item;
            result.add(message);
        }
        return result;
    }
    
    
    /**
     * 收取所有邮件
     *
     * @throws Exception
     */
    public ArrayList<EmailMessage> receive(int max) throws Exception {
        return receive(max, null);
    }
    
    /**
     * 收取邮件
     *
     * @throws Exception
     */
    public ArrayList<EmailMessage> receive() throws Exception {
        return receive(0, null);
    }

    /**
     * 发送带附件的mail
     *
     * @param subject         邮件标题
     * @param to              收件人列表
     * @param cc              抄送人列表
     * @param bodyText        邮件内容
     * @param attachmentPaths 附件地址列表
     * @throws Exception
     */
    public void send(String subject, String[] to, String[] cc, String bodyText, String[] attachmentPaths)
            throws Exception {
        ExchangeService service = getExchangeService();

        EmailMessage msg = new EmailMessage(service);
        msg.setSubject(subject);
        MessageBody body = MessageBody.getMessageBodyFromText(bodyText);
        body.setBodyType(BodyType.HTML);
        msg.setBody(body);
        for (String toPerson : to) {
            msg.getToRecipients().add(toPerson);
        }
        if (cc != null) {
            for (String ccPerson : cc) {
                msg.getCcRecipients().add(ccPerson);
            }
        }
        if (attachmentPaths != null) {
            for (String attachmentPath : attachmentPaths) {
                msg.getAttachments().addFileAttachment(attachmentPath);
            }
        }
        msg.send();
    }

    /**
     * 发送不带附件的mail
     *
     * @param subject  邮件标题
     * @param to       收件人列表
     * @param cc       抄送人列表
     * @param bodyText 邮件内容
     * @throws Exception
     */
    public void send(String subject, String[] to, String[] cc, String bodyText) throws Exception {
        send(subject, to, cc, bodyText, null);
    }
    
    public static void main(String args[]) throws Exception{
    	ExchangeMailUtil mailUtil=new ExchangeMailUtil();
    	mailUtil.mailLogin("zhangsan", "123456", null);
    	// 接收邮件
        ArrayList<EmailMessage> mails = mailUtil.receive(5);
        for (EmailMessage mail : mails) {
            System.out.println("邮件标题: " + mail.getSubject());
            System.out.println("接收时间: " + mail.getDateTimeReceived());
            System.out.println("发送人: " + mail.getFrom().getName() + ", 地址: " + mail.getFrom().getAddress());
            System.out.println("已读:" + mail.getIsRead());
            // 更新已读
            if (!mail.getIsRead()) {
                mail.setIsRead(true);
                mail.update(ConflictResolutionMode.AlwaysOverwrite);
            }
            //System.out.println("邮件内容 :" + mail.getBody());

            // 处理附件
            List<Attachment> attachs = mail.getAttachments().getItems();
            try {
                if (mail.getHasAttachments()) {
                    for (Attachment attach : attachs) {
                        if (attach instanceof FileAttachment) {
                            //接收邮件到临时目录
                            System.out.println(attach.getName());
                            //File tempZip = new File("/tmp", attach.getName());
                            //((FileAttachment) attach).load(tempZip.getPath());
                        }
                    }
                    //删除邮件
                    //mail.delete(DeleteMode.HardDelete);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println();
        }
    	// 发送邮件
//        mailUtil.send("testmail", new String[] { "385400188@qq.com","283453282@qq.com" }, null, "content11");
    }
}
