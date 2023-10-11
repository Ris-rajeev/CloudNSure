package com.realnet.webhook.controller;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.oauth2.sdk.Request;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.webhook.entity.Constant;
import com.realnet.webhook.entity.IncomingEntity;
import com.realnet.webhook.entity.WebhookData;
import com.realnet.webhook.repository.IncomingRepository;
import com.realnet.webhook.repository.WebhookDataRepository;

@RestController
@RequestMapping("/api/incoming")
public class IncomigController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private IncomingRepository incoming_repo;

	@Autowired
	private WebhookDataRepository webhook_data_repo;

	@RequestMapping("/test")
	public String webhooktest(@RequestBody String body) {
		System.out.println(body);
		return body;
	}

	@GetMapping("/genrateuserkey")
	public ResponseEntity<?> genrateUserKey() {

		String key = "";
		try {
			key ="user_"+ genratekey();
//			return  ResponseEntity<String>("",HttpStatus.ACCEPTED);

		} catch (NoSuchAlgorithmException e) {
			key = "Retry";
			e.printStackTrace();
		}

		return new ResponseEntity<String>(key, HttpStatus.ACCEPTED);

	}

	@GetMapping("/genrateapikey")
	public ResponseEntity<?> genrateApiKey() {
		String key = "";
		try {
			key ="api_"+ genratekey();
//			return  ResponseEntity<String>("",HttpStatus.ACCEPTED);

		} catch (NoSuchAlgorithmException e) {
			key = "Retry";
			e.printStackTrace();
		}

		return new ResponseEntity<String>(key, HttpStatus.ACCEPTED);
	}

	@GetMapping("/genratetokenkey")
	public ResponseEntity<?> genrateToenKey() throws JsonProcessingException {
		AppUser user = userService.getLoggedInUser();

		String token = "";
		// return ResponseEntity<String>("",HttpStatus.ACCEPTED);
		HashMap<String, String> map = new HashMap<>();
		map.put("email", user.getUsername());
		map.put("password", user.getChangePassw());

		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191 + "/token/session";
		HttpHeaders headers = getHeaders();
		HttpEntity<Object> request = new HttpEntity<Object>(map, headers);
		ResponseEntity<Object> res = restTemplate.postForEntity(resourceUrl, request, Object.class);

		Object body = res.getBody();
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(body);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();
		JsonElement item = obj.get("item");
		JsonObject object = item.getAsJsonObject();
		token = object.get("token").getAsString();
		System.out.println(token);

		return new ResponseEntity<String>(token, HttpStatus.ACCEPTED);

	}

	@GetMapping("/generateurl")
	public ResponseEntity<?> generateUrl() {
		String key = "";
		String genrated_url = "";
		try {

			key = genratekey();
//			genrated_url = "http://" + "localhost:9191" + "/webhook/" + key;

			genrated_url = "http://" + Constant.LOCAL_HOST + ":" + Constant.BACKEND_PORT_9191 + "/api/webhook/" + key;

		} catch (NoSuchAlgorithmException e) {
			key = "Retry";
			e.printStackTrace();
		}

		return new ResponseEntity<String>(genrated_url, HttpStatus.ACCEPTED);

	}

//	incoming Webhook

//	save outgoing details
	@PostMapping("/save")
	public ResponseEntity<?> outgoingSave(@RequestBody IncomingEntity incoming_data) {
		AppUser loggedInUser = userService.getLoggedInUser();

//			Saving Incoming data  With loggedint user id

//		IncomingEntity incoming = new IncomingEntity();
//		incoming.setApi_key(incoming_data.getApi_key());
//		incoming.setDescription(incoming_data.getDescription());
//		incoming.setIs_active(incoming_data.getIs_active());
//		incoming.setToken(incoming_data.getToken());
//		incoming.setUrl(incoming_data.getUrl());
//		incoming.setUser_key(incoming_data.getUser_key());
//		incoming.setWebhook_name(incoming_data.getWebhook_name());
//		incoming.setUser_id(loggedInUser.getUserId());
//		incoming_repo.save(incoming);
		IncomingEntity save = incoming_repo.save(incoming_data);
		return new ResponseEntity<>(save, HttpStatus.ACCEPTED);

	}

////	update incoming details
	@PutMapping("/update/{id}")
	public ResponseEntity<String> outgoingUpdate(@PathVariable Long id, @RequestBody IncomingEntity incoming_data) {
		IncomingEntity incoming = incoming_repo.findById(id).get();
		incoming.setApi_key(incoming_data.getApi_key());
		incoming.setDescription(incoming_data.getDescription());
		incoming.setIs_active(incoming_data.getIs_active());
		incoming.setToken(incoming_data.getToken());
		incoming.setUrl(incoming_data.getUrl());
		incoming.setUser_key(incoming_data.getUser_key());
		incoming.setWebhook_name(incoming_data.getWebhook_name());
		incoming_repo.save(incoming);
		return new ResponseEntity<>("", HttpStatus.ACCEPTED);
	}

//	Get By id incoming details
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		System.out.println(id);
		IncomingEntity findById = incoming_repo.findById(id).get();

		System.out.println(findById);

		return new ResponseEntity<>(findById, HttpStatus.ACCEPTED);
	}

//	Get All outgoing details
	@GetMapping("/getall")
	public ResponseEntity<?> getAllOutgoing() {

		return new ResponseEntity<>(incoming_repo.findAll(), HttpStatus.ACCEPTED);
	}

//	delete outgoing details
	@DeleteMapping("/delete/{id}")
	public String outgoingDelete(@PathVariable Long id) {
		incoming_repo.deleteById(id);
		return "Deleted";
	}

	private String genratekey() throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey secretKey = keyGen.generateKey();
		byte[] encoded = secretKey.getEncoded();

		String key = DatatypeConverter.printHexBinary(encoded).toLowerCase();
		System.out.println(key);
		return key;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
