package com.sy.service.Impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import com.sy.service.SSHUtilService;
import com.sy.tool.Constant;

@Service
public class SSHUtilServiceImp implements SSHUtilService{
	private Logger log = LoggerFactory.getLogger(SSHUtilServiceImp.class); 
	
	private ChannelSftp sftp;  
	 private Session session;  
	 private String privateKey; 
	 
	 
	@Override
	public String login(String username, String password, String host, int port) {
		String ConnectInfo="";
        try {  
            JSch jsch = new JSch();  
            if (privateKey != null) {  
                jsch.addIdentity(privateKey);// 设置私钥  
                  
            }  
            session = jsch.getSession(username, host, port);  
            log.info("Session is build");  
            if (password != null) {  
                session.setPassword(password);    
            }  
            Properties config = new Properties();  
            config.put("StrictHostKeyChecking", "no");  
                
            session.setConfig(config);  
            session.connect();  
            log.info("Session is connected");  
              
            Channel channel = session.openChannel("sftp");  
            channel.connect();  
            log.info("channel is connected");  
    
            sftp = (ChannelSftp) channel;  
            ConnectInfo=String.format("sftp server host:[%s] port:[%s] is connect successfull<br>", host, port);
        } catch (JSchException e) {  
        	ConnectInfo="登陆异常";
        } 
        log.info("ConnectInfo:"+ConnectInfo);
        return ConnectInfo;
	}

	@Override
	public void logout() {		  
        if (sftp != null) {  
            if (sftp.isConnected()) {  
                sftp.disconnect();  
                log.info("sftp is closed already");  
            }  
        }  
        if (session != null) {  
            if (session.isConnected()) {  
                session.disconnect();  
                log.info("sshSession is closed already");  
            }  
        }  
    
	}

	@Override
	public String execCmd(String command) throws Exception {
		 String execCmdInfo="";
        BufferedReader reader = null;  
        Channel channel = null;            
        channel = session.openChannel("exec");  
        ((ChannelExec) channel).setCommand(command);  
        channel.setInputStream(null);  
        ((ChannelExec) channel).setErrStream(System.err);  
        channel.connect();  
        InputStream in = channel.getInputStream();  
        reader = new BufferedReader(new InputStreamReader(in,Constant.UTF_8));  
        String buf = null;  
        while ((buf = reader.readLine()) != null) {  
        	execCmdInfo=execCmdInfo+buf+"<br>";
//            log.info(buf);  
        }  
        channel.disconnect();  
        log.info("execCmdInfo:"+execCmdInfo);
        return execCmdInfo;
	}

	@Override
	public void upload(String directory, String sftpFileName, InputStream input)throws SftpException {		 
        try {    
            sftp.cd(directory);  
        } catch (SftpException e) {  
              
            sftp.mkdir(directory);  
            sftp.cd(directory);  
        }  
        sftp.put(input, sftpFileName);   
        //文件赋权：传入的int会被转成8进制,我们需提前转成8进制
        //参考文献：http://blog.csdn.net/u013637569/article/details/51852589
        sftp.chmod(Integer.parseInt("777",8), sftpFileName);        
    
	}
		
}
