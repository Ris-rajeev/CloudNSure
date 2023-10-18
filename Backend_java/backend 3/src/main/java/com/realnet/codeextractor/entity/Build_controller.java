package com.realnet.codeextractor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class Build_controller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String api_endpoint;
	private String filepath;
	private String tech_stack;

	private String service;

	private String object_type;

	private String sub_object_type;

	@ManyToOne
	@JsonBackReference
	private Rn_Bcf_Extractor rn_bcf_extractor;
}
