//package com.realnet.webhook.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.realnet.workflow.Entites.Workflow_table;
//
//import lombok.Data;
//
//@Entity
//@Data
//public class OutgoingMapEntity {
//private Integer id;
//	
//	private String header_id;
//	
//	@Column(length = 5000)
//     private String Model;
//	
//	@JsonBackReference
//	@ManyToOne
//	private Workflow_table workflow_table;  
//}
