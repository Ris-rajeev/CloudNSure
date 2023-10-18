package com.realnet.webhook.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.SureConnect.Entities.Sure_Connect;
import com.realnet.SureConnect.Repository.SureRepository;
import com.realnet.utils.Port_Constant;
import com.realnet.webhook.entity.Outgoing_lines;
import com.realnet.webhook.entity.Webhook_workflow;
import com.realnet.webhook.repository.OutgoingLineRepository;
import com.realnet.webhook.repository.WebhookWorkRepo;

@Service
public class WebhookJobService {
	
	private static final Logger log= LoggerFactory.getLogger(WebhookJobService.class);
	
	@Autowired
	private WebhookWorkRepo workflowrepo;

	@Autowired
	private OutgoingLineRepository lineRepository;
	
	@Autowired
	private SureRepository sureRepository;

//	get list of outgoing webhook created by logged in user 

	public String callworkflow() throws JsonProcessingException {

		String url = "";
		String type = "";

		String token = "";
		String body = "";

		String connection_name = "";

		int status_code = 0;

		HashMap<String, String> map = new HashMap<>();
		
		List<Webhook_workflow> webs = workflowrepo.findbyprocessingflag();
		for (Webhook_workflow webhook_workflow : webs) {

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
			
			Long webhook_workflow_id = webhook_workflow.getWebhook_workflow_id();
			Long webhook_id = webhook_workflow.getId();

			Outgoing_lines lines = lineRepository.findById(webhook_workflow_id).get();
			String model = lines.getModel();

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(model);


			JsonArray arr = element.getAsJsonArray();
			for (JsonElement object : arr) {
				JsonObject jsonobject = object.getAsJsonObject();
				
				connection_name = jsonobject.get("connection_name").getAsString();
				type = jsonobject.get("type").getAsString();
				url = jsonobject.get("url").getAsString();
				
				Sure_Connect sure_Connect = sureRepository.findByConnection_name(connection_name);
				token = sure_Connect.getAccess_token();
				log.info("token : "+ token);
				
				insertin_jobpro(url, type, connection_name, webhook_id, map.toString());
				log.info("insert in job pro");

			}

		}

		return "success";
	}
	
	
// INSERT IN JOB PRO 
	public void insertin_jobpro(String url, String type,String connecton_name,Long webhook_id,String body )
			throws JsonProcessingException {

		Map<String, String> jobdata = new HashMap<String, String>();
		jobdata.put("parameters", body.toString());
		jobdata.put("url", url);
		jobdata.put("method", type);
		jobdata.put("connection_name", connecton_name);
		jobdata.put("createdBy", "2022");
		jobdata.put("updatedBy", "2022");
		jobdata.put("job_type", "webhook");
		jobdata.put("ref", webhook_id.toString());
		System.out.println(jobdata);

		RestTemplate restTemplate = new RestTemplate();
		String jobprourl = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.JOBPRO_PORT_8087 + "/jobpro/pipiline";
		HttpHeaders headers = getHeaders();
		HttpEntity<Object> request = new HttpEntity<Object>(jobdata, headers);
		ResponseEntity<Object> res1 = restTemplate.postForEntity(jobprourl, request, Object.class);
		if (res1.getStatusCodeValue() == 200) {
			log.info("webhook inserted in job pro");

//			workflowService.updateJasonObject(w_id, count, current_state, 200);
		}
		System.out.println(res1.getBody());
	}
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
