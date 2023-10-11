package com.realnet.webhook.entity;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String webhook_name; 
	private String url;
	private String user_key;
	private String api_key;
	private Boolean is_active;
	private String token;
	@Column(name = "description",nullable = true)
	private String description;
	
	
//	private Long user_id;
	
	
	
	
	
	
}
