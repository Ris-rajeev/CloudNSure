package com.realnet.BackendConfig.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.fnd.entity.Rn_Module_Setup;

import lombok.Data;

@Entity
@Data
public class BackendConfig_t {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String backend_service_name;
	private String techstack;
	private String description;
	private Integer proj_id;
	private boolean isprimary;

	private Long db_id;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "backendConfig_ts")
	@JsonBackReference
	private List<Rn_Module_Setup> module_Setups = new ArrayList<>();

}