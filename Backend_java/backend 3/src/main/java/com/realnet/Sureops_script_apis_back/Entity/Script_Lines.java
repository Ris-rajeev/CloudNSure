package com.realnet.Sureops_script_apis_back.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
@Entity
@Data
public class Script_Lines {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String model;
	
	@JsonBackReference
	@ManyToOne
	private Sureops_script_apis sureops_script_apis;
}
