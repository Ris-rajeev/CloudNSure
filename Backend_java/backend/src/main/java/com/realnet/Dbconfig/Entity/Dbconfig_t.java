package com.realnet.Dbconfig.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.BackendConfig.Entity.BackendConfig_t;

import lombok.Data;

@Entity
@Data
public class Dbconfig_t {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String db_name;
	private String db_type;
	private String db_username;
	private String db_password;
	private Integer port_no;

	private Integer proj_id;
//	private Integer module_id;
//	private Long Backend_id;
	private String techstack;

	private String host_name;

	private boolean existing_db;

}