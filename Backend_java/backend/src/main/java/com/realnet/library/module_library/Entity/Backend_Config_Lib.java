package com.realnet.library.module_library.Entity;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class Backend_Config_Lib {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String backend_service_name;
	private String techstack;
	private String description;
	private Integer proj_id;

	private boolean isprimary;
	
	//new field added
	private Integer dbconfig_lib_id;
	
	//new field added

	@ManyToMany(mappedBy = "backends")
	@JsonBackReference
	private List<ModuleLibrary> modulelibraries = new ArrayList();
	
}
