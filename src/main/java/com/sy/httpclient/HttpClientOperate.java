package com.sy.httpclient;


import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.sy.tool.Constant;
import com.sy.xiaoniu.assets.ApiRequest;
import com.sy.xiaoniu.assets.ApiResponse;
import com.sy.xiaoniu.assets.RequestEncrypt;
import com.sy.xiaoniu.assets.ResponseDecrypt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientOperate implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 将required设置为false:
     * 为了避免RequestConfig没被注进来的时候其他方法都不能用,报createbeanfailedexception
     *
     */
    private RequestConfig requestConfig;

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    private CloseableHttpClient getHttpClient(){
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    /**
     * 无参get请求
     * @autho 董杨炀
     * @time 2017年5月8日 下午3:30:08
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException{
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);//设置请求参数
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return content;
            }
        } finally {
            if (response != null) {
                response.close();
            }
            //httpclient.close();
        }
        return null;
    }

    /**
     * 有参get请求
     * @param url
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String doGet(String url , Map<String, String> params) throws URISyntaxException, ClientProtocolException, IOException{
        URIBuilder uriBuilder = new URIBuilder(url);
        if(params != null){
            for(String key : params.keySet()){
                uriBuilder.setParameter(key, params.get(key));
            }
        }
        return this.doGet(uriBuilder.build().toString());
    }

    /**
     * 有参post请求
     * @autho 董杨炀
     * @time 2017年5月8日 下午3:32:48
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPost(String url , Map<String, String> params) throws ClientProtocolException, IOException{
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if(params != null){
            // 设置2个post参数，一个是scope、一个是q
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for(String key : params.keySet()){
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpPost);
            // 判断返回状态是否为200
            /*if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }*/
            return new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
            //httpclient.close();
        }
    }

    /**
     * 有参post请求,json交互
     * @autho 董杨炀
     * @time 2017年5月8日 下午3:33:01
     * @param url
     * @param json
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPostJson(String url , String json) throws ClientProtocolException, IOException{
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
            //标识出传递的参数是 application/json
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpPost);
            // 判断返回状态是否为200
            /*if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }*/
            return new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
            //httpclient.close();
        }
    }

    /**
     * 加密post请求
     * @param	url
     * @param	SoapReuqest
     * @return	String
     * @throws IOException 
     * @throws ClientProtocolException 
     * */
    public String doPostString(String url , String SoapReuqest) throws ClientProtocolException, IOException {
    	HttpPost	httppost = new HttpPost(url);
    	CloseableHttpResponse response=null;
    	ApiRequest request_text = RequestEncrypt.request(Constant.merChantCode,SoapReuqest,Constant.zxPublicKey, Constant.zxPrivateKey, Constant.otherPublicKey, Constant.otherPrivateKey);		        
        String jsonString = JSON.toJSONString(request_text);
    	try {
    	// 创建 httppost 的实例
    				//设置头信息
    	httppost.setHeader("Content-Type","application/json;charset=UTF-8");
		httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
		httppost.setHeader("Accept", "text/html,application/xml,application/json");
		httppost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");		
    				// 设置连接超时时间
    				httppost.setConfig(requestConfig);
    				
    				// 设置报文头以及参数的格式
    				byte[] b = jsonString.getBytes(Consts.UTF_8);
    				InputStream is = new ByteArrayInputStream(b, 0, b.length);
    				InputStreamEntity reqEntity = new InputStreamEntity(is, b.length);
    				httppost.setEntity(reqEntity);
    				// 执行post请求 ，返回Response，包含响应头和响度应体
    				response = this.getHttpClient().execute(httppost);
    				// 返回实体
    				HttpEntity entity = response.getEntity();
    				
						String responsetxt = EntityUtils.toString(entity, Consts.UTF_8);
						ApiResponse dencryptResponse=ResponseDecrypt.response(responsetxt, Constant.zxPublicKey, Constant.otherPrivateKey);
				        System.out.println("解密后响应报文:"+dencryptResponse);
				        String errorMsg=dencryptResponse.getErrorMsg();
				        String Response_Body=null;
				        if(errorMsg!=null){
				        	Response_Body=errorMsg;
				        }else{				        	
				        	Response_Body=dencryptResponse.getData();
				        }
						return Response_Body;
					
					}finally {						
						if(httppost !=null){
							httppost.releaseConnection();
						}
			            if (response != null) {
								response.close();
			            }						
			        }
    }
    /**
     * 无参post请求
     * @autho 董杨炀
     * @time 2017年5月8日 下午3:33:27
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPost(String url) throws ClientProtocolException, IOException{
        return this.doPost(url, null);
    }
}
