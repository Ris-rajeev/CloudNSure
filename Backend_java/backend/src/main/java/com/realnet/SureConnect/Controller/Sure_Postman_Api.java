package com.realnet.SureConnect.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sure_connect/sure_postman")
public class Sure_Postman_Api {

	Logger log = org.slf4j.LoggerFactory.getLogger(Sure_Postman_Api.class);

	@PostMapping("/call_api")
	public ResponseEntity<?> calldifferentmethod(@RequestBody String json_body,
			@RequestParam String api_url,
			//@RequestParam String json_body,
			@RequestParam String method, @RequestParam String token) {


		log.info("executing no json_bodyeters");

		if (method.equalsIgnoreCase("DELETE")) {
			Object body = DELETE(api_url,token);

			return new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			Object object = callmethod(api_url, json_body, method, token);

			System.out.println(object);
			return new ResponseEntity<>(object, HttpStatus.OK);

		}
	}

	// CALL METHOD
	public Object callmethod(String urll, String json_body, String method, String token) {

	

		if (method.equalsIgnoreCase("GET")) {
			ResponseEntity<Object> get = GET(urll,token);
			Object body = get.getBody();
			System.out.println(body);
			return get.getBody();
		}

		else if (method.equalsIgnoreCase("POST")) {
			ResponseEntity<Object> post = POST(urll, json_body, token);
			Object body = post.getBody();
			System.out.println(body);

			return post.getBody();

		} else if (method.equalsIgnoreCase("PUT")) {
			ResponseEntity<Object> put = PUT(urll, json_body, token);
			Object body = put.getBody();
			System.out.println(body);

			return put.getBody();

		} else {
			return null;
		}

	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

	public ResponseEntity<Object> GET(String url,String token) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = url;
		String token1 = "Bearer " + token;
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", token1);
		HttpEntity<Object> request = new HttpEntity<Object>( headers);
//		ResponseEntity<Object> u = restTemplate.getForEntity(url, Object.class);
		ResponseEntity<Object> u = restTemplate.exchange(resourceUrl, HttpMethod.GET, request, Object.class);

		return u;

	}

	public ResponseEntity<Object> POST(String jobinfo, Object user, String token) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = jobinfo;
		String token1 = "Bearer " + token;
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", token1);
		HttpEntity<Object> request = new HttpEntity<Object>(user, headers);
		ResponseEntity<Object> res = restTemplate.postForEntity(resourceUrl, request, Object.class);

		return res;

	}

	public ResponseEntity<Object> PUT(String jobinfo, Object user, String token) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = jobinfo;
		String token1 = "Bearer " + token;
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", token1);
		HttpEntity<Object> request = new HttpEntity<Object>(user, headers);
//		ResponseEntity<Object> res = restTemplate.put(resourceUrl, request, Object.class);
		ResponseEntity<Object> res = restTemplate.exchange(resourceUrl, HttpMethod.PUT, request, Object.class);

		return res;

	}

	public Object DELETE(String url, String token) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = url;
		String token1 = "Bearer " + token;
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", token1);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
//		ResponseEntity<Object> u = restTemplate.getForEntity(url, Object.class);
		ResponseEntity<Object> u = restTemplate.exchange(resourceUrl, HttpMethod.DELETE, request, Object.class);
		return u;
//		restTemplate.delete(url, Object.class);
	}
}
