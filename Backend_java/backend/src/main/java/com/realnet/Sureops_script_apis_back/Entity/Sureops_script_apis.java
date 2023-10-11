package com.realnet.Sureops_script_apis_back.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Sureops_script_apis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String script_name;
	private String description;
	private String techstack;
	private String version;
	//private String script;
	private boolean isactive;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sureops_script_apis")
	private List<Script_Lines> script_Lines= new ArrayList<>();;

}