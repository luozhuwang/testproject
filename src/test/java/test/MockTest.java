package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.AbstractDocument.Content;

import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Parameter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class MockTest {
	private ClientAndServer mockServer;

	@Rule
	public MockServerRule server = new MockServerRule(this, 5000);

//	@Test
	public void testMockServer() throws IOException {		
		
		MockServerClient mockClient = new MockServerClient("localhost", 5000);
		String expected = "{ message: 'incorrect username and password combination' }";
		
		List<Parameter> parameters=new ArrayList<Parameter>();
		Parameter cc=new Parameter("name", "foo");
		
		parameters.add(cc);
		
		mockClient.when(request().withPath("/hello/John").withMethod("GET").withQueryStringParameters(parameters)
		// .withHeader(new Header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN))
		// .withQueryStringParameter(new Parameter("my-token", "12345"))
				).respond(response().withStatusCode(200).withBody(expected));
		
		
		Map<String, String> MapParams=new HashMap<String, String>();
		MapParams.put("name", "foo");
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(geturl("http://localhost:5000/hello/John", MapParams));
		
		CloseableHttpResponse response = client.execute(httpGet);
		// 验证输出是否是正确
		InputStream content = response.getEntity().getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(content);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String responseText = bufferedReader.readLine();
		assertThat(responseText, equalTo(expected));
	}
	
	
	public void testa(){
		MockServerClient mockClient = new MockServerClient("localhost", 1080);
		mockClient.when(
	        request()
	            .withMethod("POST")
	            .withPath("/login")
	            .withBody("{username: 'foo', password: 'bar'}")
	    )
	    .respond(
	        response()
	            .withStatusCode(302)
	            .withCookie(
	                "sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW"
	            )
	            .withHeader(
	                "Location", "https://www.mock-server.com"
	            )
	    );
	}

//	 @Test
	 public void should_response_as_expected() throws IOException {

			String expected = "{ message: 'incorrect username and password combination' }";
			
			mockServer=startClientAndServer(1020);
			mockServer.when(
					request()
					.withPath("/getUser")
					.withMethod("GET")
					).respond(
					response()
					.withStatusCode(200)
					.withBody(expected)
					);
					CloseableHttpClient client = HttpClients.createDefault();
					HttpGet httpGet = new HttpGet("http://localhost:1020/getUser");
					CloseableHttpResponse response = client.execute(httpGet);
					//验证输出是否是正确
					InputStream content = response.getEntity().getContent();
					InputStreamReader inputStreamReader = new InputStreamReader(content);
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					String responseText = bufferedReader.readLine();
					assertThat(responseText, equalTo(expected));
			
			mockServer.stop();
		
	 }

	 /**
		 * 将Url和Map的参数进行拼接，最后拼接成一个请求地址
		 * @param url
		 * @param NameValuePair
		 * @author luojuwang
		 * */
		public String geturl(String url, Map<String, String> mapparams) {	
			String request="";
			try {
				if (mapparams == null || mapparams.isEmpty()) {
					request=url;
					System.out.println("不带参数get请求地址：" + url);
				}else{
					if (!url.contains("?")) {
		                url += "?";
		            }
					List<NameValuePair> formparams = new ArrayList<NameValuePair>();
					System.out.println("请求参数：");
					Set<String> ks = mapparams.keySet();
					for (String k : ks) {
						String v = mapparams.get(k);
						System.out.println("\t" + k + "=" + v);
						formparams.add(new BasicNameValuePair(k, v));
					}
					String str = EntityUtils.toString(new UrlEncodedFormEntity(formparams, com.sy.tool.Constant.UTF_8));
					request= url+str;
					System.out.println("带参数get请求地址：" + request);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return request;
		}
}
