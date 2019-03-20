package com.sy.controller;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.entity.PublishInfo;
import com.sy.service.PublishService;

@Controller
public class PublishController {
	private Logger log = LoggerFactory.getLogger(PublishController.class);

	private String deliver_match=null;
	private String deliver_type=null;
	private String status=null;
	
	@Autowired
	private PublishService publishService;
	
	
	/**
	 * 部署记录
	 * @param	publishInfo
	 * */
	@RequestMapping("/publish/deploy")
	public String deploylist(PublishInfo publishInfo,Model model){
		List<PublishInfo> deployInfolist=publishService.getDeployInfo(publishInfo);
		for(PublishInfo cc :deployInfolist){
			cc.setCreatetime(cc.getCreatetime().replace(".0", ""));
		}
		model.addAttribute("deployInfolist",deployInfolist);
		return "/Mail/deploy";
	}
	/**
	 * 操作记录
	 * @param	publishInfo
	 * */
	@RequestMapping("/publish/operate")
	public String operatelist(PublishInfo publishInfo,Model model){
		List<PublishInfo> operatelist=publishService.getOperatorRecord(publishInfo.getDeliver_list());
		for(PublishInfo cc :operatelist){
			cc.setCreatetime(cc.getCreatetime().replace(".0", ""));
		}
		model.addAttribute("operatelist",operatelist);
		return "/Mail/operate";
	}
	/**
	 * 部署交付列表
	 * @param	publishInfo		
	 * @param	pagenum
	 * @param	model
	 * */
	@RequestMapping(value="/publish/list")
	public String publishlist(PublishInfo publishInfo,@RequestParam(value="pagenum",defaultValue="1") Integer pagenum,Model model){
		PageHelper.startPage(pagenum, 15);
		List<PublishInfo> publishInfolist=publishService.getPublishInfo(publishInfo);
		for(PublishInfo cc :publishInfolist){
			if(cc.getStatus()== null || cc.getStatus().equals("")){
				cc.setStatus("无");
			}else if(cc.getStatus().equals("publish")){
				cc.setStatus("已发送");
			}else if(cc.getStatus().equals("draft")){
				cc.setStatus("草稿");
			}else if(cc.getStatus().equals("confirm")){
				cc.setStatus("已确认");
			}else if(cc.getStatus().equals("abandoned")){
				cc.setStatus("已废弃");
			}
			if(cc.getDeliver_type()== null || cc.getDeliver_type().equals("")){
				cc.setDeliver_type("无");
			}else if(cc.getDeliver_type().equals("docker.web")){
				cc.setDeliver_type("操作系统(WEB)");
			}else if(cc.getDeliver_type().equals("docker.app")){
				cc.setDeliver_type("操作系统(APP)");
			}else if(cc.getDeliver_type().equals("nfs")){
				cc.setDeliver_type("操作系统(NFS)");
			}
			if(cc.getDeliver_match()==null){
				cc.setDeliver_match("无");
			}
			cc.setCreatetime(cc.getCreatetime().replace(".0", ""));
		}
		PageInfo<PublishInfo> pageinfo=new PageInfo<PublishInfo>(publishInfolist);
		//使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
		model.addAttribute("deliver_list",publishInfo.getDeliver_list());
		model.addAttribute("online_date",publishInfo.getOnline_date());
		model.addAttribute("deliver_match",publishInfo.getDeliver_match());
		model.addAttribute("status",publishInfo.getStatus());
		model.addAttribute("publishInfolist",pageinfo);
		return "/Mail/publish";
	}
	/**
	 * 汇总测试部署
	 * @param	online_date	上线日期
	 * @param	model
	 * */
	@RequestMapping(value="/publish/collection")
	public String collectonOnline(String online_date,Model model){
		//已确认和已发布列表
		List<PublishInfo> collectionNFSlist=collectionMatch(online_date, "nfs");
		List<PublishInfo> collectionWEBlist=collectionMatch(online_date, "docker.web");
		List<PublishInfo> collectionAPPlist=collectionMatch(online_date, "docker.app");
		//draft列表
		List<PublishInfo> draftNFSlist=collectiondraft(online_date, "nfs");
		List<PublishInfo> draftWEBlist=collectiondraft(online_date, "docker.web");
		List<PublishInfo> draftAPPlist=collectiondraft(online_date, "docker.app");
		
		if(collectionNFSlist.size()>0){			
			model.addAttribute("collectionNFSlist",collectionNFSlist);
		}		
		if(collectionWEBlist.size()>0){			
			model.addAttribute("collectionWEBlist",collectionWEBlist);
		}		
		if(collectionAPPlist.size()>0){			
			model.addAttribute("collectionAPPlist",collectionAPPlist);
		}	
		if(draftNFSlist.size()>0){			
			model.addAttribute("draftNFSlist",draftNFSlist);
		}		
		if(draftWEBlist.size()>0){			
			model.addAttribute("draftWEBlist",draftWEBlist);
		}		
		if(draftAPPlist.size()>0){			
			model.addAttribute("draftAPPlist",draftAPPlist);
		}	
		model.addAttribute("online_date",online_date);
		return "/Mail/collection";
	}
		
	/**
	 * 获取jenkins部署记录
	 * @param	publishInfo
	 * */
	@RequestMapping(value="/getMessage",method=RequestMethod.POST)
	public String getMessage(PublishInfo publishInfo,Model model){
		String user_code=publishInfo.getUser_code();
		String deliver_list=publishInfo.getDeliver_list();
		String server_list=publishInfo.getServer_list();
		String online_date=publishInfo.getOnline_date();
		log.info("发布用户："+user_code);
		log.info("发布版本："+deliver_list);
		log.info("发布服务器："+server_list);
		log.info("发布日期："+online_date);
		
		if(user_code.equals("admin")){
			log.info("报告主人,admin用户部署版本包,"+"online_date="+online_date+",deliver_list="+deliver_list+",server_list="+server_list);
		}else{		
			List<PublishInfo> publishInfolist=publishService.getPublishInfo(publishInfo);
			if(0!=publishInfolist.size()){
				status=publishInfolist.get(0).getStatus();
				if(status.equals("confirm")){					
					log.info("此版本已经发布过:"+deliver_list);
				}else if(status.equals("draft")){
					log.info("此版本已打包未部署:"+deliver_list);
					publishInfo.setStatus("confirm");
					publishService.updateDeliverInfo(publishInfo);
				}
			}else{
				if(publishInfo.getDeliver_list().startsWith("share-nfs")){
					deliver_match=deliver_list.substring(0,deliver_list.indexOf("_"));
					deliver_type="nfs";
				}else if(publishInfo.getDeliver_list().startsWith("app")){
					deliver_match=deliver_list.substring(0,deliver_list.indexOf(":"));
					deliver_type="docker.app";
				}else if(publishInfo.getDeliver_list().startsWith("web")){
					deliver_match=deliver_list.substring(0,deliver_list.indexOf(":"));
					deliver_type="docker.web";
				}
				publishService.Publish(user_code, deliver_list, server_list, online_date,deliver_match,deliver_type,"confirm");
			}
		}
		publishService.Deploy(user_code, deliver_list, server_list, online_date);		
		model.addAttribute("message","获取部署数据成功");
		
		return "/getMessage";
	}
	/**
	 * 获取打包记录
	 * @param	publishInfo
	 * */
	@RequestMapping(value="/getPackRecord",method=RequestMethod.POST)
	public String getPackRecord(PublishInfo publishInfo,Model model){
		String user_code=publishInfo.getUser_code();
		String deliver_list=publishInfo.getDeliver_list();
		String server_list=publishInfo.getServer_list();
		String online_date=publishInfo.getOnline_date();
		log.info("打包用户："+user_code);
		log.info("打包版本："+deliver_list);
		log.info("打包服务器："+server_list);
		log.info("发布日期："+online_date);
				
		if(publishInfo.getDeliver_list().startsWith("share-nfs")){
			deliver_match=deliver_list.substring(0,deliver_list.indexOf("_"));
			deliver_type="nfs";
		}else if(publishInfo.getDeliver_list().startsWith("app")){
			deliver_match=deliver_list.substring(0,deliver_list.indexOf(":"));
			deliver_type="docker.app";
		}else if(publishInfo.getDeliver_list().startsWith("web")){
			deliver_match=deliver_list.substring(0,deliver_list.indexOf(":"));
			deliver_type="docker.web";
		}
		publishService.Publish(user_code, deliver_list, server_list, online_date,deliver_match,deliver_type,"draft");

		model.addAttribute("message","获取发布记录成功");
		
		return "/getPackRecord";
	}
	/**
	 * 比较confirm 和 "confirm、publish"的数据，取最新的一条
	 * @param	online_date		上线日期
	 * @param	deliver_type	类型
	 * */
	private List<PublishInfo> collectionMatch(String online_date,String deliver_type) {
		List<PublishInfo> confirmList=publishService.collectionBymatch(online_date,deliver_type,"confirm","");	
		List<PublishInfo> allList=publishService.collectionBymatch(online_date,deliver_type,"confirm","publish");		
		
		HashMap<String, String> confirmMap=new HashMap<String, String>();
		for(PublishInfo confirm:confirmList){
			confirmMap.put(confirm.getDeliver_match(), confirm.getDeliver_list());
		}
		
		HashMap<String, String> allMap=new HashMap<String, String>();
		for(PublishInfo all:allList){
			allMap.put(all.getDeliver_match(), all.getDeliver_list());
		}
		//同一个服务获取最新的发布包
		for(String key : confirmMap.keySet()) {  
			String confirm=confirmMap.get(key);
			String aa=allMap.get(key);
			int compare=aa.compareToIgnoreCase(confirm);
			if(compare>=0){
				for(PublishInfo cc:allList){
					if(cc.getDeliver_match().equals(key)){						  
						cc.setDeliver_list(aa);
					}
				}				  
			}
		}	
		return allList;
	}
	
	/**
	 * 获取已打包的版本
	 * */
	private List<PublishInfo> collectiondraft(String online_date,String deliver_type){
		List<PublishInfo> draftList=publishService.collectionBymatch(online_date,deliver_type,"draft","");	
		 return draftList;
	}
}


