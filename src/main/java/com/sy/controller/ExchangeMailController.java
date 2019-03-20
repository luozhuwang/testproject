package com.sy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sy.entity.Mail;
import com.sy.entity.PublishInfo;
import com.sy.entity.UserInfo;
import com.sy.service.Config;
import com.sy.service.ExchangeMailService;
import com.sy.service.PublishService;
import com.sy.tool.Constant;
import com.sy.tool.MapKeyComparator;
import com.sy.tool.RecordIP;
import com.sy.tool.SvnUtil;

@Controller
public class ExchangeMailController {
	private Logger log = LoggerFactory.getLogger(ExchangeMailController.class);
	
	@Autowired
	ExchangeMailService mailService;
	
	@Autowired
	private PublishService publishService;
	
	@Autowired
	private Config config;
	
	@RequestMapping(value="/publish/sendmail")
	public String sendmail(String online_date,HttpServletRequest request,Model model){
		
		log.info("上线日期:"+online_date);	
		String UserName="用户IP未绑定";
		String Email="";
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		String user_ip=RecordIP.getIpAddress(request);
		log.info("user_Ip:"+user_ip);		
		UserInfo userInfo=publishService.getUserInfo(user_ip);
		if(null !=userInfo){
			UserName=userInfo.getUser_name();
			Email=userInfo.getEmail()+";";
		}
		String visit_url=basePath+"publish/collection?online_date="+online_date;
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String subject=online_date+"待上线版本申请部署准生产环境_"+df.format(day);
		StringBuffer content=new StringBuffer();
		content.append("<p>HI ALL：</p>");
		content.append("<p>本批次<span style='color:#ff0000; font-weight:bold;'>" + online_date
				+ "</span>待上线版本已测试通过，请运维人员部署准生产环境以便让产品进行验收。如有疑问请与上线测试负责人(<span style='color:#ff0000;font-weight:bold;'>" + UserName + "</span>)联系~</p>");
		content.append("<p>SVN地址如下：<a href='" + Constant.svn_base+ online_date.replace("-", "")+ "'>" + Constant.svn_base+ online_date.replace("-", "") + "</a></p>");
		content.append("<p>更多汇总信息请访问：<a href='" + visit_url + "'>" + visit_url + "</a></p>");
		content.append("<p style='height:20px;'>&nbsp;</p>");
		content.append("<p style='font-weight:bold;'>需求列表：</p>");
		content.append("<p>请在此处自行填写本次上线需求...</p>");
		content.append("<p style='height:20px;'>&nbsp;</p>");
		//部署类型所有数据：包含nfs/docker.app/docker.web
		content.append(deployInfo("nfs", online_date));
		content.append(deployInfo("docker.app", online_date));
		content.append(deployInfo("docker.web", online_date));
		//读取SVN中配置文件目录
		Map<String,List<String>> fileDir=getSVNFile(online_date);
		content.append("<p style='font-weight:bold;'>部署类型：SQL</p>");
		List<String> sqlFiles=fileDir.get("sqlFileList");
		if(0==sqlFiles.size()){
			content.append("无");
		}else{
			for(String sqlFile:sqlFiles){
				content.append("<p><a href='" + sqlFile + "'>" + sqlFile + "</a></p>");
			}
		}
		content.append("<p style='height:20px;'>&nbsp;</p>");
		content.append("<p style='font-weight:bold;'>部署类型：配置中心</p>");
		List<String> configFiles=fileDir.get("configFileList");
		if(0==configFiles.size()){
			content.append("无");
		}else{			
			for(String configFile:configFiles){
				content.append("<p><a href='" + configFile + "'>" + configFile + "</a></p>");
			}
		}
		content.append("<p style='height:20px;'>&nbsp;</p>");
		content.append("<p style='font-weight:bold;'>部署类型：其他</p>");
		content.append("<p>参见svn提交内容</p>");
		content.append("<p style='height:20px;'>&nbsp;</p>");
		String to_mail=config.mail_to;
		String cc_mail=config.mail_cc;
		model.addAttribute("to_mail",to_mail+Email);
		model.addAttribute("cc_mail",cc_mail);
		model.addAttribute("subject",subject);
		model.addAttribute("richtext",content.toString());
		return "/Mail/sendmail";
	}
	
	@RequestMapping("/publish/sendmailAction")
	public String sendmailAction(Mail mail,HttpServletRequest request,Model model){
		String ip=RecordIP.getIpAddress(request);
		log.info("发送邮件IP:"+ip);
		String mail_user=config.mail_user;
		String mail_pwd=config.mail_pwd;
		if(mail !=null){			
			mailService.Certified(mail_user, mail_pwd);
			int count=mailService.send(mail.getSubject(), mail.getTo_mail(), mail.getCc_mail(), mail.getContent());
			if(count>0){
				log.info("邮件发送成功");
				//邮件发送成功，将confirm状态改为publish
				List<String> Deliverlist=currentVersion(mail.getContent());				
				updateDeliver(Deliverlist, ip);
			}
		}
		model.addAttribute("message", "邮件发送成功");
		return "/getMessage";
	}
	
	/**
	 * 拼接部署类型的数据
	 * @param	deliver_type:nfs、docker.app、docker.web
	 * @param	online_date	上线日期
	 * **/
	private String deployInfo(String deliver_type,String online_date){
		String svnurl=Constant.svn_base+online_date.replace("-", "")+"/web/";	
		StringBuffer sb= new StringBuffer();			
		sb.append("<p style='font-weight:bold;'>部署类型："+deliver_type+" </p>");
			List<PublishInfo> alllist=publishService.collectionBymatch(online_date, deliver_type, "confirm","publish");
			List<PublishInfo> publishlist=publishService.collectionBymatch(online_date, deliver_type, "publish",null);
			if(0==alllist.size()){
				sb.append("<p>无</p>");
			}else{
				sb.append("<table width='90%' cellspacing='0' style='border:solid 1px #999999; table-layout:fixed;'>");
				sb.append("<tr style='height:25px;'>");
				sb.append("<td width='15%' style='border:solid 1px #999999; font-weight:bold;'>交付类型</td>");
				sb.append("<td width='6%' style='border:solid 1px #999999; font-weight:bold;'>测试确认</td>");
				sb.append("<td width='23%' style='border:solid 1px #999999; font-weight:bold;'>当前批次</td>");
				sb.append("<td width='23%' style='border:solid 1px #999999; font-weight:bold;'>最终批次</td>");
				sb.append("<td width='23%' style='border:solid 1px #999999; font-weight:bold;'>历史批次</td>");
				sb.append("</tr>");
				
				HashMap<String, String> resultMap=new HashMap<String, String>();
				for(PublishInfo all:alllist){
					resultMap.put(all.getDeliver_match(), all.getDeliver_list());
				}
				//按Key进行排序
				Map<String, String> allMap = sortMapByKey(resultMap);  
				
				HashMap<String, String> publishMap=new HashMap<String, String>();
				for(PublishInfo publish:publishlist){
					publishMap.put(publish.getDeliver_match(), publish.getDeliver_list());
				}
				//同一个服务获取最新的发布包
				for(String key : allMap.keySet()) {  
					sb.append("<tr style='height:40px;'>");
					sb.append("<td style='border:solid 1px #999999;'>" + key + "</td>");
//					通过服务名遍历日期'confirm','publish' 的数据 	A1--版本1
//					通过服务名遍历日期'publish' 的数据				A2--版本2
//					如果 版本1=版本2	版本1为空
//					如果版本2<版本1		版本2=版本1
					String all=allMap.get(key);
					String publish=publishMap.get(key);	
					
					if(null==publish){
						publish="";
					}					
					int compare=publish.compareToIgnoreCase(all);
					if(compare<0){					
						publish=all;
					}else if (0==compare){
						all="";
					}
					PublishInfo ab=new PublishInfo();
					ab.setDeliver_list(publish);
					List<PublishInfo> abc=publishService.getPublishInfo(ab);
					//通过    版本2--Deliver_list 找对应的发布人
					sb.append("<td style='border:solid 1px #999999;'>" + abc.get(0).getUser_name() + "</td>");
					if("nfs"==deliver_type){
						if(!all.equals("")){							
							all=svnurl+key+"/"+all;
						}
						publish=svnurl+key+"/"+publish;
					}
					//当前批次
					sb.append("<td class='CurrentVersion' style='border:solid 1px #999999;'>" + all + "</td>");
					//最终批次
					sb.append("<td class='LastVersion' style='border:solid 1px #999999;'>" + publish + "</td>");
					sb.append("<td style='border:solid 1px #999999;'>");
					if("nfs"==deliver_type){
						publish=publish.replace(svnurl+key+"/", "");
					}
					//历史批次
					sb.append("<p>" + publish + "</p>");
					List<PublishInfo> Deliverlist=publishService.getDeliverlist(online_date, key);
					for(PublishInfo zz : Deliverlist){
						if(!publish.equals(zz.getDeliver_list())){							
							sb.append("<p>" + zz.getDeliver_list() + "</p>");
						}
					}
					sb.append("</td>");
				}
				sb.append("</tr>");
				sb.append("</table>");
			}	
			return sb.toString();	
	}
	/**
	 * 更新DeliverName的数据为已发布,添加操作日志
	 * @param Deliverlist
	 * @param	user_ip
	 * */
	private void updateDeliver(List<String> Deliverlist,String user_ip){
		for(String DeliverName:Deliverlist){
			if(!DeliverName.equals("")){				
				log.info("当前批次"+DeliverName);
				publishService.insertlog(DeliverName, user_ip, "发邮件部署准生产");
				publishService.updateDeliverStatus(DeliverName);			
			}
		}
	}
	/**
	 * 从SVN中读取sql 和 config 文件列表
	 * @param	online_date
	 * */
	private Map<String,List<String>> getSVNFile(String online_date){
		Map<String, List<String>> mapList=new HashMap<String, List<String>>();
		String SVNdir=online_date.replace("-", "");
		List<String>  sqlFileList=new ArrayList<String>();
		List<String>  configFileList=new ArrayList<String>();
		String svn_user=config.svn_user;
		String svc_passwd=config.svn_passwd;
		//读取SVN下面的所有目录
		List<String> ALLdir= SvnUtil.repositoryDir(Constant.svn_base, svn_user, svc_passwd);
		for(String content:ALLdir){
			if(content.contains(SVNdir)){
				//读取SVN下面的当前日期的目录
				List<String> contentDir= SvnUtil.repositoryDir(Constant.svn_base+content, svn_user, svc_passwd);
				for(String sqllist:contentDir){
					String path=Constant.svn_base+content+"/"+sqllist+"/";
					if(sqllist.equals("sql")){
						List<String> sqldir=SvnUtil.repositoryDir(path, svn_user, svc_passwd);
						for(String sqlfile:sqldir){
							//拼接完整SQL链接
							sqlFileList.add(path+sqlfile);
						}
					}else if (sqllist.equals("config")){
						List<String> configdir=SvnUtil.repositoryDir(path, svn_user, svc_passwd);
						for(String configfile:configdir){
							//拼接完整config链接
							configFileList.add(path+configfile);							
						}
					}					
				}
			}
		}
		mapList.put("sqlFileList", sqlFileList);
		mapList.put("configFileList", configFileList);
		return mapList;
	}
	/**
	 * 解析html,将当前批次保存下来
	 * @param	content
	 * */
	private List<String> currentVersion(String content){
		List<String> Versionlist=new ArrayList<String>();
		Document doc = Jsoup.parse(content);
		Elements elements=doc.getElementsByClass("CurrentVersion");
		for(Element ss:elements){			
			String deliver=ss.text();
			if(deliver!=null){				
				if(deliver.contains("svn/releases")){
					String [] cc=deliver.split("/");				
					Versionlist.add((cc[cc.length-1]));				
				}else{
					Versionlist.add(deliver);
				}
			}			
		}
		return Versionlist;
	}
	
	 /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    private  Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
        	return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
}
