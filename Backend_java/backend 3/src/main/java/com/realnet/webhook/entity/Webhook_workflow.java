package com.realnet.webhook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Webhook_workflow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String event;

	private String on_entity;

	private String response_body;

	private String processing_flag;

	private int status_code;
	
	private Long webhook_workflow_id;

	@Column(length = 5000)
	  private String json;

	@Column(name = "created_at", nullable = true)
	private String created_at;

}
