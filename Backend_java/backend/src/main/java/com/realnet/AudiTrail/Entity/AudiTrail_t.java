package com.realnet.AudiTrail.Entity;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.realnet.users.entity1.AppUser_who_column;

import lombok.Data;

@Data
@Entity
public class AudiTrail_t extends AppUser_who_column{

	private static final long serialVersionUID = 1L;



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long audi_id;
	

	@Lob
	private String json;
	
	private String entity_name;
	
	private String action;
	
	private String mac_id;
	
	private String ip;
	
	private String user;
	
	
	

}
