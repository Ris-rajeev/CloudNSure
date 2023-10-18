package com.realnet.webhook.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookData {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String webhook_name; 

	@Column(name = "type",nullable = true)
	private String type;
	@Column(name = "model",nullable = true)
	private String model;
	@Column(name = "token",nullable = true)
	private String token;
	@Column(name = "created_at",nullable = true)
	private String created_at;
	
}
