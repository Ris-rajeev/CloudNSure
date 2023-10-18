package com.realnet.library.module_library.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.BackendConfig.Entity.BackendConfig_t;

import lombok.Data;

@Data
@Entity
public class Dbconfig_Lib {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String db_name;
	private String db_type;
	private String db_username;
	private String db_password;
	private Integer port_no;

	private Integer proj_id;

	private String techstack;

}
