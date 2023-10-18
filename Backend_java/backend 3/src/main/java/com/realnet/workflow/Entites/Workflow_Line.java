package com.realnet.workflow.Entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.Dashboard1.Entity.dashbord_Who_collumn;

import lombok.Data;

@Entity
@Data
public class Workflow_Line extends dashbord_Who_collumn {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String header_id;
	
	@Column(length = 5000)
     private String Model;
	
	@JsonBackReference
	@ManyToOne
	private Workflow_table workflow_table;
}
