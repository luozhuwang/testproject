package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.commons.net.telnet.TelnetClient;




public class TelnetUtil {

	/** Telnet服务器返回的字符集 */
	private static final String SRC_CHARSET = "ISO-8859-1";
	/** 转换后的字符集 */
	private static final String DEST_CHARSET = "utf-8";

	/** 新建一个TelnetClient对象 */

    private TelnetClient telnetClient = new TelnetClient();

    String response=null;
    /** 系统标示符号 */

    private final String osTag = "\r\n";

    /** get Value 系统标示符号 */

    private final String getValOsTag = "END\r\n";

    private char promptChar = '>'; 
    /** 输入流，接收返回信息 */

    private InputStream in;

    /** 向 服务器写入 命令 */

    private PrintStream out;

 

    /**

     * @param ip : telnet的IP地址

     * @param port : 端口号，默认11211

     */

    public TelnetUtil(String ip, Integer port) {
       try {
           telnetClient.connect(ip, port);
           in = telnetClient.getInputStream();
           out = new PrintStream(telnetClient.getOutputStream());
       } catch (Exception e) {
           System.out.println("[telnet] connect error: connect to [" + ip + ":" + port + "] fail!");
       }

    }

 

    public TelnetUtil(String ip) {

       try {

           telnetClient.connect(ip, 11211);

           in = telnetClient.getInputStream();

           out = new PrintStream(telnetClient.getOutputStream());

       } catch (Exception e) {

           System.out.println("[telnet] connect error: connect to [" + ip + ":" + 11211 + "] fail!");

       }

    }

 

    /**

     * 执行telnet命令

     *

     * @param command

     * @return

     */

    public String execute(String command) {
       write(command);
       StringBuffer sb = new StringBuffer();
       String osTagX = osTag;
       if (command.startsWith("get")) {
           osTagX = getValOsTag;
       }       
       try {
           char ch = (char) in.read();      
           int isEnd = 0;
           while (true) {
              sb.append(ch);
//              if (ch == osTagX.charAt(isEnd)) {
              if (ch ==promptChar) {
                  isEnd++;
                  if (sb.toString().endsWith(osTagX) && isEnd == osTagX.length());
                  String ss=new String(sb.toString().getBytes(SRC_CHARSET), DEST_CHARSET); // 编码转换，解决中文乱码问题
                  if(ss.contains("elapsed:")){
                	  int lastIndex = ss.lastIndexOf("elapsed:");  
                	  response=ss.substring(0, lastIndex);
                  }else{
                	  response=ss.replace("dubbo>", ""); 
                  }
                  return response;
//                	  return new String(sb.toString().getBytes(SRC_CHARSET), DEST_CHARSET); // 编码转换，解决中文乱码问题
              } else {
                  isEnd = 0;
              }
              ch = (char) in.read();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

       return "error! when the program execute";

    }
    
    /**

     * 向telnet命令行输入命令

     *

     * @param command

     */

    public void write(String command) {

       try {

           out.println(command);

           out.flush();

           System.out.println("[telnet] 打印本次执行的telnet命令:" + command);

       } catch (Exception e) {

           e.printStackTrace();

       }

    }

 

    /**

     * 关闭Telnet连接

     */

    public void disconnect() {
       try {
    	   Thread.sleep(10);
    	   if(telnetClient!=null&&!telnetClient.isConnected())  
    		   telnetClient.disconnect();

       } catch (InterruptedException e1) {

           e1.printStackTrace();

       } catch (IOException e2) {

           e2.printStackTrace();

       }

    }

 

   
   

    /**

     * 存储服务器正在清空缓存服务器缓存

     * @param url

     * @param port

     */

    public static void clearCache(String url,Integer port){

       System.out.println("[telnet] 存储服务器正在清空缓存服务器缓存[" + url + ":" + port + "]----------------------------");

       TelnetUtil telnetTest = new TelnetUtil(url, port);

       String result = telnetTest.execute("flush_all");

       System.out.println(result);

       telnetTest.disconnect();

    }
//	
	
    public static void main(String args []){
    	TelnetUtil tu = new TelnetUtil("172.20.20.215", 6028);
//    	String response1=tu.execute("ls");
//    	System.out.println(response1);
//    	invoke com.neo.xnol.pcts.product.facade.ProductFacade.findProductList({"productType":2,"productTypeEnum":"DURATION","minDeadline":0,"maxDeadline":0},0,10) 
    	String response2=tu.execute("invoke com.neo.xnol.app.security.service.CryptoService.decryptData(\"T56dIPq/mDGUsjS3t/5uEJivm5I9BkSGLQoAsgIyzOPg=\")");
    	System.out.println(response2);
    	tu.disconnect();
    	
    }

    
}

