package test;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sy.dao.MailDao;
import com.sy.dao.MockDao;
import com.sy.dao.PublishDao;
import com.sy.dao.UserDao;
import com.sy.entity.MessageResult;
import com.sy.entity.MockInfo;
import com.sy.entity.PublishInfo;
import com.sy.entity.UserInfo;
import com.sy.entity.data;
import com.sy.tool.Constant;
import com.sy.tool.MapKeyComparator;
import com.sy.tool.ReadFromFile;
import com.sy.tool.SvnUtil;

/**
 * 测试DAO层
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class Daotest {

	@Autowired
	private MailDao mailDao;
	
	@Autowired
	private PublishDao publishDao;
	
	@Autowired
	private MockDao mockDao;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void getversion(){
		 String deliver_match=null;
		 String deliver_type=null;
		 String status=null;
		
		PublishInfo publishInfo=new PublishInfo();
		String userName="luojuwang";
//		List<String> ll=SvnUtil.repositoryDir(userName, userName);
//		for(String content:ll){
		String content="2018-09-11";
			System.out.println("待发布目录 :"+content);
			String versionInfo=SvnUtil.getVersioninfo(Constant.svn_base, userName, userName, content.replace("-", ""));
			String deliverlist []=versionInfo.split("\n");
			for(String deliver:deliverlist){	
				System.out.println("发布包："+deliver);
				publishInfo.setDeliver_list(deliver);
				List<PublishInfo> lbp=publishDao.getPublishInfo(publishInfo);
				if(lbp.size()!=0){
					System.out.println("此版本已打包或已部署:"+deliver);
				}else{
					if(publishInfo.getDeliver_list().startsWith("share-nfs")){
						deliver_match=deliver.substring(0,deliver.indexOf("_"));
						deliver_type="nfs";
					}else if(publishInfo.getDeliver_list().startsWith("app")){
						deliver_match=deliver.substring(0,deliver.indexOf(":"));
						deliver_type="docker.app";
					}else if(publishInfo.getDeliver_list().startsWith("web")){
						deliver_match=deliver.substring(0,deliver.indexOf(":"));
						deliver_type="docker.web";
					}
					status="draft";
					publishDao.insertPublish("dev", deliver, "", content, deliver_match, deliver_type, status);
				}
			}
			
//		}
	}
	
	
	@Test
	public void usertest(){
		UserInfo userinfo=userDao.findByUsername("luojuwang","123456");
		System.out.println(userinfo.getUser_code());
		System.out.println(userinfo.getUser_name());
		System.out.println(userinfo.getUser_ip());
		System.out.println(userinfo.getEmail());
		
	}
	
	
	@Test
	public void insertUser(){
		
		Integer aa=userDao.registeredUser("zhangyi", "456789", "张一", "192.168.11.1", "zhangyi@abc.com");
		System.out.println(aa);
	}
	
	@Test
	public void mocktest(){
		List<MockInfo> mockInfo=mockDao.getMockRecord("get");
		for(MockInfo cc:mockInfo){
			System.out.println("name:"+cc.getName());
			System.out.println("method:"+cc.getMethod());
		}
	}
	
	@Test
	public void mocktestid(){
		Integer a=mockDao.updateStatus("1", "0");
		System.out.println(a);
		
	}
	
	@Test
	public void test(){
		String file="E:\\Users\\xiaoniu_loan\\test-output\\TestNGReport.html";
		String content=ReadFromFile.readTxtFile(file);
		mailDao.insertMail("qqqq", "aazzzza", "CCsdfsdfC", content);
	}
	
	@Test
	public void jiemi() throws UnsupportedEncodingException{
		 Base64.Decoder decoder = Base64.getDecoder();
		 String name = new String (decoder.decode("MTcyLjIwLjE3LjExMg=="), "UTF-8");
		 
		 System.out.println(name);
	}
	
	@Test
	public void compareString(){
		String title="share-nfs-mobile_201805041604.zip";
		String title2="share-nfs-mobile_201804281356.zip";
		//如果2个字符相等，则为0
		System.out.println("都有数据："+title.compareTo(title2));
		String title3="";
		String title4="share-nfs-mobile_201804281356.zip";
		//如果2个字符相等，则为0
		System.out.println("title4有数据："+title3.compareTo(title4));
		String title5="share-nfs-mobile_201805041604.zip";
		String title6="";
		//如果2个字符相等，则为0
		System.out.println("title5有数据："+title5.compareTo(title6));
		
		String title7="share-nfs-mobile_201805041604.zip";
		String title8="share-nfs-mobile_201805041604.zip";
		//如果2个字符相等，则为0
		System.out.println("2个值相等："+title7.compareTo(title8));
	}
	
	@Test
	public void comparedata(){
		List<PublishInfo> collectionNFSconfirmlist=publishDao.collectionBymatch("2018-04-03","nfs","confirm","");	
		List<PublishInfo> collectionNFSlist=publishDao.collectionBymatch("2018-04-03","nfs","confirm","publish");		
		
		HashMap<String, String> confirmMap=new HashMap<String, String>();
		for(PublishInfo confirm:collectionNFSconfirmlist){
			confirmMap.put(confirm.getDeliver_match(), confirm.getDeliver_list());
		}
		
		HashMap<String, String> allMap=new HashMap<String, String>();
		for(PublishInfo all:collectionNFSlist){
			allMap.put(all.getDeliver_match(), all.getDeliver_list());
		}
		
		for(String key : confirmMap.keySet()) {  
			  String confirm=confirmMap.get(key);
			  String aa=allMap.get(key);
			  int compare=aa.compareToIgnoreCase(confirm);
			  if(compare>=0){
				  for(PublishInfo cc:collectionNFSlist){
					  if(cc.getDeliver_match().equals(key)){						  
						  cc.setDeliver_list(aa);
					  }
				  }				  
			  }
		}
		
		for(PublishInfo cc:collectionNFSlist){
			System.out.println("Deliver_list:"+cc.getDeliver_list());
		}
	}
	
	@Test
	public void testuserinfo(){
		String user_ip="172.20.17.76";
		String UserName="未获取到用户名";
		String Email="";
		
		UserInfo userInfo=publishDao.getUserInfo(user_ip);
		if(null !=userInfo){
			UserName=userInfo.getUser_name();
			Email=userInfo.getEmail()+";";
		}
		System.out.println("UserName:"+UserName);
		System.out.println("Email:"+Email);
	}
	
	

	
	
	private String output(String deliver_type,String online_date){
		String svnurl="http://172.20.21.1/svn/releases/"+online_date+"/web/";
		
		StringBuffer sb= new StringBuffer();			
		sb.append("<p style='font-weight:bold;'>部署类型："+deliver_type+" </p>");
			List<PublishInfo> alllist=publishDao.collectionBymatch(online_date, deliver_type, "confirm","publish");
			List<PublishInfo> publishlist=publishDao.collectionBymatch(online_date, deliver_type, "publish",null);
			
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
				
				 Map<String, String> allMap = sortMapByKey(resultMap);  //按Key进行排序
				
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
						System.out.println("为空");
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
					List<PublishInfo> abc=publishDao.getPublishInfo(ab);
					//通过    版本2--Deliver_list 找对应的发布人
					sb.append("<td style='border:solid 1px #999999;'>" + abc.get(0).getUser_name() + "</td>");
					if("nfs"==deliver_type){
						if(!all.equals("")){							
							all=svnurl+key+"/"+all;
						}
						publish=svnurl+key+"/"+publish;
					}
					//当前批次
					sb.append("<td style='border:solid 1px #999999; font-weight:bold;'>" + all + "</td>");
					//最终批次
					sb.append("<td style='border:solid 1px #999999;'>" + publish + "</td>");
					sb.append("<td style='border:solid 1px #999999;'>");
					if("nfs"==deliver_type){
						publish=publish.replace(svnurl+key+"/", "");
					}
					//历史批次
					sb.append("<p>" + publish + "</p>");
					List<PublishInfo> Deliverlist=publishDao.getDeliverlist(online_date, key);
					for(PublishInfo zz : Deliverlist){
						if(!publish.equals(zz.getDeliver_list())){							
							sb.append("<p>" + zz.getDeliver_list() + "</p>");
						}
					}
					sb.append("</td>");
				}
				sb.append("</tr>");
				sb.append("</table>");
//				sb.append("<p style='height:20px;'>&nbsp;</p>");
			}
			
			
			return sb.toString();
	
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

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
	@Test
	public void testpublish(){
		PublishInfo abc=new PublishInfo();
		abc.setOnline_date("2018-04-03");
		abc.setStatus("publish");
		abc.setDeliver_match("share-nfs-dist");;
		List<PublishInfo> info=publishDao.getPublishInfo(abc);
		for(PublishInfo cc:info){
			System.out.println("Deliver_list:"+cc.getDeliver_list());
//			System.out.println("User_name:"+cc.getUser_name());
//			System.out.println("Updatetime:"+cc.getUpdatetime().replace(".0", ""));
		}
	}
	
	@Test
	public void testgetmatch(){
//		List<PublishInfo> info=publishDao.collection("2018-05-10", "docker.web", "confirm", "publish");
//		for(PublishInfo cc:info){
//			System.out.println("Deliver_list:"+cc.getDeliver_list());			
//		}
		System.out.println("----------");
		
		List<PublishInfo> info2=publishDao.collectionBymatch("2018-05-10", "docker.web", "confirm", "publish");
		for(PublishInfo cc:info2){
			System.out.println("Deliver_list:"+cc.getDeliver_list());			
		}
	}
	@Test
	public void testDeploy(){
//		publishDao.insertDeploy("lisi", "app-6038-loan:1804181100", "172.20.20.175", "2018-05-10");
		PublishInfo abc=new PublishInfo();
		abc.setOnline_date("2018-05-15");
		abc.setDeliver_list("app-6041-pcts:1805071444");
		List<PublishInfo> info=publishDao.getDeployInfo(abc);
		for(PublishInfo cc:info){
			System.out.println("User_name:"+cc.getUser_name());			
		}
	}
	
	@Test
	public void testcollectOnline(){

//		List<PublishInfo> info=publishDao.collectionBymatch("2018-04-03","docker.web","confirm","publish");
		List<PublishInfo> info=publishDao.getDeliverlist("2018-04-03","share-nfs-dist");
		for(PublishInfo cc:info){
			System.out.println("Deliver_match:"+cc.getDeliver_match());
			System.out.println("Deliver_type:"+cc.getDeliver_type());
			System.out.println("Deliver_list:"+cc.getDeliver_list());
//			System.out.println("Createtime:"+cc.getCreatetime().replace(".0", ""));
		}
		
		
	}
		
	@Test
	public void Stringsub(){
		String deliver_list="app-6031-activity:1804201628";
		System.out.println(deliver_list.substring(0,deliver_list.indexOf(":")));
		String deliver_list2="web-8077-mobile:1802021514";
		System.out.println(deliver_list2.substring(0,deliver_list2.indexOf(":")));
		String deliver_list3="share-nfs-mobile_201802071134.zip";
		System.out.println(deliver_list3.substring(0,deliver_list3.indexOf("_")));
		
	}
	
	
	@Test
	public void uuidtest(){
		UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        System.out.println(str);
	}
	
	public static void main(String args []){
		//解析泛型数据
		String s1="{\"responseMessage\":null,\"responseCode\":null,\"status\":\"SUCCESS\",\"data\":{\"createTime\":\"2018-05-16 22:42:41\",\"assetLevel\":3,\"serialId\":\"NA0115afc4360815b720038421c5f\",\"riskStatus\":1,\"repaySource\":\"工资收入\",\"signZone\":0,\"purpose\":\"个人消费\",\"type\":0,\"submitLendTime\":null,\"amount\":200000.0,\"id\":177042,\"principal\":null,\"interest\":null,\"userId\":248880,\"totalAmount\":null,\"contractId\":\"00109071600000164\",\"lstModTime\":\"2018-05-17 15:17:21\",\"merchantCode\":\"100001\",\"dueDate\":null,\"auditOpinion\":\"无\",\"valueDate\":null,\"repayBankCardId\":246941,\"status\":6,\"lendDate\":null,\"period\":24,\"sourceCompany\":\"普惠金融\",\"productId\":73,\"fee\":null,\"lendBankCardId\":246941,\"signTime\":\"2018-05-16 00:00:00\",\"repaySafeguards\":\"该笔借款由深圳市小牛普惠信息资讯有限公司提供担保。（其中小牛普惠与小牛在线为同一控制人控制下的关联方）\",\"transId\":\"605873578562303488\",\"periodType\":1,\"riskDetail\":null,\"lendAmout\":200000.0}}";
		String s2="{\"responseMessage\":\"成功\",\"responseCode\":\"success\",\"status\":\"SUCCESS\",\"data\":1} ";
		
		MessageResult<data> messageResult=JSON.parseObject(s1, new TypeReference<MessageResult<data>>(){});
		String Serialid=messageResult.getData().getSerialId();
				System.out.println("Serialid:"+Serialid);
		MessageResult<Integer> messageResult2=JSON.parseObject(s2, new TypeReference<MessageResult<Integer>>(){});	
		int data=messageResult2.getData().intValue();
		System.out.println("data:"+data);
	}
}
