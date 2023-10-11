package com.realnet.webhook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.webhook.Service.WebhookJobService;
import com.realnet.webhook.entity.IncomingEntity;
import com.realnet.webhook.entity.Outgoing_lines;
import com.realnet.webhook.entity.Webhook_workflow;
import com.realnet.webhook.repository.IncomingRepository;
import com.realnet.webhook.repository.OutgoingLineRepository;
import com.realnet.webhook.repository.WebhookWorkRepo;

@RestController
@RequestMapping("/token/webhook/Workflow")
public class WebhookWorkflowController {
	
	
	@Autowired
	private WebhookJobService jobService;
	
	@Autowired
	private WebhookWorkRepo workflowrepo;

	@Autowired
	private OutgoingLineRepository lineRepository;
	
	@Autowired
	private IncomingRepository incomingRepository;

//	get all Webhook details 
	@GetMapping("/getall")
	public ResponseEntity<?> getWebhookData() {

		return new ResponseEntity<>(workflowrepo.findAll(), HttpStatus.ACCEPTED);
		
	}
//	get by id Webhook details
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getWebhookDatabyid(@PathVariable Long id ) {
		return new ResponseEntity<>(workflowrepo.findById(id).get(), HttpStatus.ACCEPTED);
		
	}
//	call workflow
	@GetMapping("/web")
	public ResponseEntity<?> insertinjob() throws JsonProcessingException {
		String callworkflow = jobService.callworkflow();
		return new ResponseEntity<>(callworkflow, HttpStatus.ACCEPTED);
	}
	
//	call workflow
	@GetMapping("/web_flagN")
	public ResponseEntity<?> callwbhookN_flag() throws JsonProcessingException {
		List<Webhook_workflow> webs = workflowrepo.findbyprocessingflag();
		return new ResponseEntity<>(webs, HttpStatus.ACCEPTED);
	}
	
//	call workflow
	@GetMapping("/web_line/{webhook_workflow_id}")
	public ResponseEntity<?> getline(@PathVariable Long webhook_workflow_id) throws JsonProcessingException {
		Outgoing_lines lines = lineRepository.findById(webhook_workflow_id).get();
		return new ResponseEntity<>(lines, HttpStatus.ACCEPTED);
	}
	
//	runurl
	@GetMapping("/run_url/{id}/{web_id}")
	public ResponseEntity<?> runurl(@PathVariable Long id,@PathVariable Long web_id,
			@RequestParam String token) throws JsonProcessingException {
		IncomingEntity inc = incomingRepository.findById(id).get();
		Webhook_workflow webhook_workflow = workflowrepo.findById(web_id).get();
		
		HashMap<String, String> map = new HashMap<>();

		
		String res_body = webhook_workflow.getResponse_body();
		ObjectMapper mapper = new ObjectMapper();
		JsonParser parser1 = new JsonParser();

		// for old data
		String str = mapper.writeValueAsString(res_body);
		String str1 = str.replaceAll("\"", "");
		JsonElement element1 = parser1.parse(str1);

		 JsonObject object2 = element1.getAsJsonObject();
		Set<Entry<String, JsonElement>> entrySet = object2.entrySet();
		
		for (Entry<String, JsonElement> ent : entrySet) {
			
			String key = ent.getKey();
			String value = ent.getValue().toString();
			map.put(key, value);
			
		}

		
		String url = inc.getUrl();
		
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = url;
		String token1 = "Bearer " + token;
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", token1);
		HttpEntity<Object> request = new HttpEntity<Object>(map, headers);
		ResponseEntity<Object> u = restTemplate.postForEntity(resourceUrl, request, Object.class);

		int status = u.getStatusCodeValue();
		if (200 <= status && status<= 205) {
			webhook_workflow.setProcessing_flag("Y");
			webhook_workflow.setStatus_code(status);
			workflowrepo.save(webhook_workflow);
		}
		
		return new ResponseEntity<>(u.getBody(), HttpStatus.OK);
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
}
