package com.realnet.webhook.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.webhook.entity.Webhook_workflow;
import com.realnet.webhook.repository.WebhookWorkRepo;

@Service
public class Workflow_service {

	@Autowired
	private WebhookWorkRepo webhookWorkRepo;

	public Webhook_workflow save_webhook(Object data, String event, String onentity) throws JsonProcessingException {
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
		String stringDate = DateFor.format(date);

		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(data);
//		String str = data.toString();
//		int strt = str.indexOf("(");
//		
//		String substring = str.substring(strt);
//		String replace = substring.replace("(", "{");
//		String body = replace.replace(")", "}");

		Webhook_workflow web = new Webhook_workflow();
		web.setEvent(event);
		web.setOn_entity(onentity);
		web.setResponse_body(body);
		web.setCreated_at(stringDate);
		web.setWebhook_workflow_id(3l);
		web.setProcessing_flag("N");
		Webhook_workflow t = webhookWorkRepo.save(web);
		return t;

	}

}
