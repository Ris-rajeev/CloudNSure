package com.realnet.webhook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.webhook.repository.WebhookDataRepository;

@RestController
@RequestMapping("/api/webhookdata")
public class WebhookIncomingDataController {


	@Autowired
	private WebhookDataRepository webhook_data_repo;

//	get all Webhook details 
	@GetMapping("/getall")
	public ResponseEntity<?> getWebhookData() {

		return new ResponseEntity<>(webhook_data_repo.findAll(), HttpStatus.ACCEPTED);
	}

//	Delete All
	@DeleteMapping("/deleteall")
	public ResponseEntity<?> deleteWebhookData() {
		webhook_data_repo.deleteAll();
		return new ResponseEntity<>("deleted", HttpStatus.ACCEPTED);
	}
	
	
}
