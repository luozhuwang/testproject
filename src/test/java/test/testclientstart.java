package test;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.TimeToLive;
import org.mockserver.matchers.Times;
import org.mockserver.socket.PortFactory;
import org.mockserver.model.Header;
import org.mockserver.model.HttpForward;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.RegexBody;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpForward.Scheme;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class testclientstart {

	private ClientAndProxy proxy;
	private ClientAndServer mockServer;
	public void startProxy() {
	    mockServer = startClientAndServer(1080);
	    //proxy = startClientAndProxy(1090);
	}
	public void shouldLoadListOfBooks() throws Exception {
		String expected = "{ message: 'incorrect username and password combination' }";
		//get请求示例
//		mockServer.when(
//				request()
//				.withPath("/getUser")
//				.withMethod("GET")
//				).respond(
//				response()
//				.withStatusCode(200)
//				.withBody(expected)
//				);  
		//post请求示例
		mockServer
		.when(
		request()
		.withMethod("POST")
		.withPath("/login")
		.withBody("{\"username\":\"foo\", \"password\":\"bar\"}")
		)
		.respond(
		response()
		.withStatusCode(200)
		.withHeaders(
		new Header("Content-Type", "application/json; charset=utf-8"),
		new Header("Cache-Control", "public, max-age=86400")
		)
		.withBody("{ \"apply_id\": \"000001\", \"overdued\": \"Y\" }")
		);
		
		
	}

	public void removeRequest(){
	   mockServer.stop();
	}
	public static void main(String[] args) {
	    testclientstart t = new testclientstart();
	    
	    try {
	        t.startProxy();
	        t.shouldLoadListOfBooks();
	        
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    System.out.println("-----------------------------------------------------------------");
//	   t.removeRequest();       
	    
	}
}
