package test;


import java.util.ArrayList;
import java.util.List;

import org.mockserver.integration.ClientAndServer;
import org.junit.Rule;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


public class MockServer {
	private static ClientAndServer mockServer=null;
	
	
	public static void main(String args []){
		String expected = "{ message: 'incorrect username and password combination' }";
		if(mockServer ==null){
			mockServer=startClientAndServer(1080);
		}
		
		mockServer.when(
				request().withKeepAlive(true).withPath("/get").withMethod("GET"))
				.respond(response().withStatusCode(200).withBody(expected));
	}
}
