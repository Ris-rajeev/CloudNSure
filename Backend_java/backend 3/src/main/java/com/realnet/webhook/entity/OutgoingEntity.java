package com.realnet.webhook.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.users.entity1.AppUser;
import com.realnet.workflow.Entites.Workflow_Line;

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
public class OutgoingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = true)
	private Long user_id;
	
	private String webhook_name; 
	private String on_entity; 
	private String on_event; 
	private String on_incoming_webhook;
	@Column(name = "description",nullable = true)
	private String Description;
	
	private boolean is_active=true;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "outgoing_entity")
	private List<Outgoing_lines> outgoing_Lines = new ArrayList<>();
	
	
}
