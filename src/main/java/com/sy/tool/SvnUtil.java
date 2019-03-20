package com.sy.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNRevisionProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * svn工具类
 * 
 * @author jkp
 *
 */
public class SvnUtil {

	private static final Logger logger = LoggerFactory.getLogger(SvnUtil.class);

	/**
	 * 获取仓库下面所有目录
	 * @param	url			svn仓库路径
	 * @param	userName	svn用户名
	 * @param	passWord	svn密码
	 * */
	@SuppressWarnings("deprecation")
	public static List<String> repositoryDir(String url, String userName, String passWord){	
		List<String> ls=new ArrayList<String>();
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(url);
			SVNRepository repository = SVNRepositoryFactory.create(repositoryURL);			
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, passWord.toCharArray());
			repository.setAuthenticationManager(authManager);
			//-1代表最新版本
			Collection entries = repository.getDir("",-1, null, (Collection) null);

			Iterator iterator = entries.iterator();
			while (iterator.hasNext()) {
				SVNDirEntry entry = (SVNDirEntry) iterator.next();
				ls.add(entry.getName());
			}
		} catch (SVNException e) {
			logger.error("获取仓库目录异常"+e.getMessage());		
		}
		return ls;
	}
	
	/**
	 * 获取未发布的版本目录
	 * @param	userName	svn用户名
	 * @param	passWord	svn密码
	 * */
	public static List<String> repositoryDir(String userName, String passWord){
		List<String> releases=new ArrayList<String>();
		MapKeyComparator compare=new MapKeyComparator();
		List<String> ll=repositoryDir(Constant.svn_base, userName, passWord);
		Date d=new Date();//获取时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");//转换格式
		String today=sdf.format(d);
		logger.info("今天日期："+today);
		for(String content:ll){
			int result=compare.compare(content, today);
			if(result>=0 && !content.equals("version.txt")){
				releases.add(content);
			}
		}
		
		return releases;
		
	}
	
	/**
	 * 签出指定svn仓库的最新版
	 * 
	 * @param url
	 *            svn仓库路径
	 * @param revision
	 *            版本号
	 * @param userName
	 *            svn用户名
	 * @param password
	 *            svn密码
	 * @param targetPath
	 *            保存路径
	 * @return
	 */
	public static long checkout(String url, String userName, String password, String targetPath) {
		return checkout(url, 0, userName, password, targetPath);
	}

	/**
	 * 签出指定svn仓库
	 * 
	 * @param url
	 *            svn仓库路径
	 * @param revision
	 *            版本号(<=0表示签出最新的)
	 * @param userName
	 *            svn用户名
	 * @param password
	 *            svn密码
	 * @param targetPath
	 *            保存路径
	 * @return
	 */
	public static long checkout(String url, long revision, String userName, String password, String targetPath) {
		File checkoutPath = new File(targetPath);
		if (checkoutPath.exists()) {
			logger.error("xxxxxx 签出{}到{}时发现指定路径已存在！", url, targetPath);
			return -1;
		}
		try {
			DefaultSVNOptions options = new DefaultSVNOptions();
			SVNClientManager sm = SVNClientManager.newInstance(options, userName, password);
			SVNUpdateClient suc = sm.getUpdateClient();
			suc.setIgnoreExternals(true);
			SVNRevision version = revision > 0 ? SVNRevision.create(revision) : SVNRevision.HEAD;
			long rid = suc.doCheckout(SVNURL.parseURIEncoded(url), checkoutPath, version, version, SVNDepth.INFINITY, true);
			logger.info("签出{}到{}，签出的版本号为：{}", new Object[] { url, targetPath, rid });
			return rid;
		} catch (SVNException e) {
			logger.error(e.getMessage(), e);
		}
		return -1;
	}

	/**
	 * 更新svn库到最新版
	 * 
	 * @param url
	 * @param repoPath
	 * @param userName
	 * @param password
	 * @return
	 */
	public static long update(String repoPath, String userName, String password) {
		File checkoutPath = new File(repoPath);
		if (!checkoutPath.exists()) {
			logger.error("xxxxxx 更新{}时发现指定路径不存在！", repoPath);
			return -1;
		}
		try {
			DefaultSVNOptions options = new DefaultSVNOptions();
			SVNClientManager sm = SVNClientManager.newInstance(options, userName, password);
			SVNUpdateClient suc = sm.getUpdateClient();
			suc.setIgnoreExternals(true);
			long rid = suc.doUpdate(new File[] { checkoutPath }, SVNRevision.HEAD, SVNDepth.INFINITY, true, true)[0];
			logger.info("更新{}，最新的版本号为：{}", new Object[] { repoPath, rid });
			return rid;
		} catch (SVNException e) {
			logger.error(e.getMessage(), e);
		}
		return -1;
	}

	/***
	 * 获取库所有版本的日志
	 * 
	 * @param url
	 *            ：库的url
	 * @param userName
	 *            ：用户名
	 * @param password
	 *            ：密码
	 * @return 库所有版本的日志集合
	 */
	public static Collection<SVNLogEntry> getAllRevisionLogs(String url, String userName, String password) {
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(url);
			SVNRepository repository = SVNRepositoryFactory.create(repositoryURL);			
			@SuppressWarnings("deprecation")
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password.toCharArray());
			repository.setAuthenticationManager(authManager);

			int startRevision = 0;
			int endRevision = -1; // 最新记录
			return repository.log(new String[] { "" }, null, startRevision, endRevision, false, true);
		} catch (SVNException e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	/**
	 * 获取库的提交日志
	 * 
	 * @param url
	 *            svn库URL
	 * @param userName
	 *            svn库用户名
	 * @param password
	 *            svn库密码
	 * @param startRevision
	 *            起始版本(0表示最新版本)
	 * @param maxFetch
	 *            获取条数
	 * @param backward
	 *            true：向前取(取指定版本之前的) false：向后取(取指定版本之后的)
	 * @param discoverChangedPaths
	 *            是否获取变更的路径信息
	 */
	public static List<SVNLogEntry> getRevisionLogs(String url, String userName, String password, long startRevision, int maxFetch, boolean backward, boolean discoverChangedPaths) {
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(url);
			SVNRepository repository = SVNRepositoryFactory.create(repositoryURL);
			@SuppressWarnings("deprecation")
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password.toCharArray());
			repository.setAuthenticationManager(authManager);

			final List<SVNLogEntry> logs = new ArrayList<>();
			SVNClientManager manager = SVNClientManager.newInstance();
			manager.setAuthenticationManager(authManager);
			manager.getLogClient().doLog(repositoryURL, new String[] { "" }, SVNRevision.HEAD, startRevision > 0 ? SVNRevision.create(startRevision) : SVNRevision.HEAD,
					backward ? SVNRevision.create(1) : SVNRevision.HEAD, false, discoverChangedPaths, false, maxFetch,
					new String[] { SVNRevisionProperty.AUTHOR, SVNRevisionProperty.DATE, SVNRevisionProperty.LOG }, new ISVNLogEntryHandler() {

						@Override
						public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
							logs.add(logEntry);
						}
					});

			// System.out.println(logs.size());
			// for (SVNLogEntry entry : logs) {
			// System.out.println(String.format("%s commit with \"%s\" on
			// %s@%d", entry.getAuthor(), entry.getMessage(), entry.getDate(),
			// entry.getRevision()));
			// }
			if(!backward) {
				Collections.reverse(logs);
			}
			return logs;
		} catch (SVNException e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	/**
	 * 获取指定路径的提交日志
	 * 
	 * @param url
	 *            svn库URL
	 * @param userName
	 *            svn库用户名
	 * @param password
	 *            svn库密码
	 * @param path
	 *            指定路径
	 * @param startRevision
	 *            起始版本(0表示最新版本)
	 * @param maxFetch
	 *            获取条数
	 * @param discoverChangedPaths
	 *            是否获取变更的路径信息
	 */
	public static List<SVNLogEntry> getRevisionLogs(String url, String userName, String password, String path, int startRevision, int maxFetch, boolean discoverChangedPaths) {
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(url);
			SVNRepository repository = SVNRepositoryFactory.create(repositoryURL);
			@SuppressWarnings("deprecation")
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password.toCharArray());
			repository.setAuthenticationManager(authManager);

			final List<SVNLogEntry> logs = new ArrayList<>();
			SVNClientManager manager = SVNClientManager.newInstance();
			manager.setAuthenticationManager(authManager);
			manager.getLogClient().doLog(repositoryURL, new String[] { path }, SVNRevision.HEAD, startRevision > 0 ? SVNRevision.create(startRevision) : SVNRevision.HEAD, SVNRevision.create(137),
					false, discoverChangedPaths, false, maxFetch, new String[] { SVNRevisionProperty.AUTHOR, SVNRevisionProperty.DATE, SVNRevisionProperty.LOG }, new ISVNLogEntryHandler() {

						@Override
						public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
							logs.add(logEntry);
						}
					});

			System.out.println(logs.size());
			for (SVNLogEntry entry : logs) {
				System.out.println(String.format("%s commit with \"%s\" on %s@%d", entry.getAuthor(), entry.getMessage(), entry.getDate(), entry.getRevision()));
			}
			return logs;
		} catch (SVNException e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	/**
	 * 获取指定路径文件在某个版本的内容
	 * 
	 * @param url
	 *            svn库URL
	 * @param userName
	 *            svn库用户名
	 * @param password
	 *            svn库密码
	 * @param path
	 *            指定路径
	 * @param revision
	 *            起始版本(<0表示最新版本)
	 * @return null:指定路径不存在或文件已删除或非文本文件
	 */
	@SuppressWarnings("deprecation")
	public static String getFileContent(String url, String userName, String password, String path, long revision) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			SVNURL repositoryURL = SVNURL.parseURIEncoded(url);
			SVNRepository repository = SVNRepositoryFactory.create(repositoryURL);
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password.toCharArray());
			repository.setAuthenticationManager(authManager);
			SVNNodeKind nodeKind = repository.checkPath(path, revision);
			if (nodeKind != SVNNodeKind.FILE) {
				return null;
			}
			Map<String, String> fileProperties = new HashMap<>();
			repository.getFile(path, revision, SVNProperties.wrap(fileProperties), baos);
			if (!SVNProperty.isTextMimeType(fileProperties.get(SVNProperty.MIME_TYPE))) {
				return null;
			}

			return baos.toString();
		} catch (SVNException e) {
			logger.error("读取SVN文件内容异常:"+e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 读取制定路径文件下面的version.txt文件
	 * @param url 		svn库URL
	 * @param userName 	svn库用户名
	 * @param password 	svn库密码
	 * @param dir 		指定目录
	 * */
	public static String getVersioninfo(String url, String userName, String password,String dir){
		return getFileContent(url, userName, password, dir+Constant.svn_version, -1);
	}
	
	
	public static Map<String,List<String>> getSVNFile(String online_date){
		Map<String, List<String>> mapList=new HashMap<String, List<String>>();
		String SVNdir=online_date.replace("-", "");
		List<String>  sqlFileList=new ArrayList<String>();
		List<String>  configFileList=new ArrayList<String>();
		String svn_user="luojuwang";
		String svc_passwd="luojuwang";
		List<String> ALLdir= SvnUtil.repositoryDir(Constant.svn_base, svn_user, svc_passwd);
		for(String content:ALLdir){
			if(content.contains(SVNdir)){				
				List<String> contentDir= SvnUtil.repositoryDir(Constant.svn_base+content, svn_user, svc_passwd);
				for(String sqllist:contentDir){
					String path=Constant.svn_base+content+"/"+sqllist+"/";
					if(sqllist.equals("sql")){
						List<String> sqldir=SvnUtil.repositoryDir(path, svn_user, svc_passwd);
						for(String sqlfile:sqldir){
							System.out.println("SQL文件："+path+sqlfile);
							sqlFileList.add(path+sqlfile);
						}
					}else if (sqllist.equals("config")){
						List<String> configdir=SvnUtil.repositoryDir(path, svn_user, svc_passwd);
						for(String configfile:configdir){
							System.out.println("配置文件："+path+configfile);
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
	
	public static void main(String args[]){
		Map<String, List<String>>  mpl=new HashMap<String, List<String>>();
		List<String> sqldir=null;
		List<String> configdir=null;
		String path="version.txt";
		String userName = "luojuwang";
		String password = "luojuwang";
		
//		String file_text=getFileContent(Constant.svn_base, userName, password, path, -1);
//		String [] ss=file_text.split("\n");
//		for(String abc: ss){
//			System.out.println(abc);
//			System.out.println("------------");
//		}
		
//		List<String> ll=repositoryDir(userName, password);
//		for(String cc: ll){
//			System.out.println(cc);
//		}
//		List<String> lldir=repositoryDir(Constant.svn_base+"20180403/", userName, password);
//		for(String cc: lldir){
//			if(cc.equals("sql")){
//				 sqldir=repositoryDir(Constant.svn_base+"20180403/"+cc, userName, password);
//				System.out.println("文件："+Constant.svn_base+"20180403/"+cc);
//			}if(cc.equals("config")){
//				 configdir=repositoryDir(Constant.svn_base+"20180403/"+cc, userName, password);
//			}
//			if(cc.contains("20180510")){
//				String file_text=getVersioninfo(Constant.svn_base, userName, password, cc);
//				String [] ss=file_text.split("\n");
//				for(String abc: ss){
//					System.out.println(abc);					
//				}
//			}
//		}
		mpl=getSVNFile("2018-09-05");
		sqldir=mpl.get("sqlFileList");
		configdir=mpl.get("configFileList");
		System.out.println("数据："+sqldir.size());
		for(String ss:sqldir){			
			System.out.println("文件："+ss);
		}
	}
}