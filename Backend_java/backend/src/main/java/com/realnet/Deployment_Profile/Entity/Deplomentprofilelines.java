package com.realnet.Deployment_Profile.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class Deplomentprofilelines {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String  lines_tables;
	private String  git_url;
	private String  git_password;
	private String 	git_user;

	private String 	git_key;
	private String 	git_pass;
	private String 	git_email;


	
	private String deplkey;
	private String depl_url;
	private String depl_username;
	private String depl_pass;
	
	private String dokrkey;
	private String dokr_username;
	private String dokr_url;
	private String dokr_pass;

	
	
	
	
	

	
	
	
	
	
	
	
	
}
