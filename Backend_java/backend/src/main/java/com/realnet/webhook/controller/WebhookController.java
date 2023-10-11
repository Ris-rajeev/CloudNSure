package com.realnet.webhook.controller;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.utils.Port_Constant;
import com.realnet.webhook.Response.EntityResponse;
import com.realnet.webhook.Service.OutgoingService;
import com.realnet.webhook.Service.Workflow_service;
import com.realnet.webhook.entity.IncomingEntity;
import com.realnet.webhook.entity.OutgoingEntity;
import com.realnet.webhook.entity.WebhookData;
import com.realnet.webhook.entity.Webhook_workflow;
import com.realnet.webhook.repository.IncomingRepository;
import com.realnet.webhook.repository.OutgoingRepository;
import com.realnet.webhook.repository.WebhookDataRepository;
import com.realnet.webhook.repository.WebhookWorkRepo;

@RestController
@RequestMapping("/api")
public class WebhookController {
	@Autowired
	private WebhookDataRepository webhook_repo;

	@Autowired
	private WebhookWorkRepo workRepo;

	@Autowired
	private Workflow_service workflow_service;

	@Autowired
	private OutgoingService outgoingService;

	@Autowired
	private OutgoingRepository outgoingRepository;

//	@Autowired
//	private AppUserServiceImpl userService;

	@Autowired
	private IncomingRepository incoming_repo;

	@PostMapping("/webhook/{user_key}/{api_key}")
	public ResponseEntity<?> outgoingSave(@RequestBody String body, @PathVariable String user_key,
			@PathVariable String api_key) throws JsonProcessingException {

//		AppUser loggedInUser = userService.getLoggedInUser();
//		Long id = loggedInUser.getUserId();

// Using Token as a unique value to fetch details from incoming table
		IncomingEntity findApiKey = incoming_repo.findApiKey(api_key);

//		validating api key
		if (findApiKey == null) {
			return new ResponseEntity<>("no Such api key found ", HttpStatus.BAD_REQUEST);
		}

//	validating user key

		if (!findApiKey.getUser_key().equals(user_key)) {
			return new ResponseEntity<>("no Such User key found ", HttpStatus.BAD_REQUEST);
		}

//		validating token
		if (!findApiKey.getIs_active() == true) {
			return new ResponseEntity<>("Webhook is not activated ", HttpStatus.BAD_REQUEST);
		}

		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
		String stringDate = DateFor.format(date);
		WebhookData webhook = new WebhookData();
		webhook.setToken(findApiKey.getToken());
		webhook.setWebhook_name(findApiKey.getWebhook_name());
		webhook.setCreated_at(stringDate);
		webhook.setModel(body);
		webhook.setType("Bearer Token");
		WebhookData save = webhook_repo.save(webhook);

		Long incoming_id = findApiKey.getId();
		boolean in = outgoingService.isOnincomingwebhookallowed(incoming_id);

		if (in) {

			workflow_service.save_webhook(save, "post", "WebhookData");

		}

		return new ResponseEntity<>(new EntityResponse("Connection Succesfully "), HttpStatus.ACCEPTED);

	}

	@GetMapping("/webhook/{user_key}/{api_key}")
	public ResponseEntity<?> outgoingGet(@PathVariable String user_key, @PathVariable String api_key) {

//		AppUser loggedInUser = userService.getLoggedInUser();
//		Long id = loggedInUser.getUserId();

// Using Token as a unique value to fetch details from incoming table
		IncomingEntity findApiKey = incoming_repo.findApiKey(api_key);

//		validating api key
		if (findApiKey == null) {
			return new ResponseEntity<>("no Such api key found ", HttpStatus.BAD_REQUEST);
		}

//	validating user key

		if (!findApiKey.getUser_key().equals(user_key)) {
			return new ResponseEntity<>("no Such User key found ", HttpStatus.BAD_REQUEST);
		}

//		validating token
//		if (!findApiKey.getIs_active() == true) {
//			return new ResponseEntity<>("Webhook is not activated ", HttpStatus.BAD_REQUEST);
//		}

		return new ResponseEntity<>(new EntityResponse("get Connection Succesfully "), HttpStatus.ACCEPTED);

	}
	
	
	
	
	@GetMapping("/webhook/{Webhook_name}")
	public ResponseEntity<IncomingEntity> findbyWebhookName(@PathVariable String Webhook_name) {
	    IncomingEntity findName = incoming_repo.findByWebhook_Name(Webhook_name);
	    
	    if (findName == null) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    return ResponseEntity.ok(findName);
	}


	

}
