package com.cloudblue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudblueApplicationTests {

	@Test
	@Order(1)
	//Expected an error if we do a 2 executions without 1 minut of wait time.
	public void test_checkip_http200() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().build();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/checkip")).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		assertNotNull(response);
		assertEquals(200, response.statusCode());
		//assertEquals(HttpClient.Version.HTTP_1_1, response.version());
	}

	@Test
	@Order(2)
	public void test_checkip_http502() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().build();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/checkip")).build();

		HttpResponse<String> response = null;

		for(int i=0;i<50;i++){
			 response = client.send(request, HttpResponse.BodyHandlers.ofString());
		}

		assertNotNull(response);
		assertEquals(502, response.statusCode());
		//assertEquals(HttpClient.Version.HTTP_1_1, response.version());
	}

	/*@Test
	@Order(3)
	public void test_checkip_http200_after_1_minut() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().build();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/checkip")).build();

		HttpResponse<String> response = null;

		for(int i=0;i<50;i++){
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		}
		if(502 == response.statusCode()){
			Thread.sleep(1000*60);
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		}

		assertNotNull(response);
		assertEquals(200, response.statusCode());

	}*/

}
