package com.realnet.Pipe.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.realnet.Dashboard1.Entity.dashbord_Who_collumn;

import lombok.Data;

@Entity
@Data
public class Rn_surepipe_play extends dashbord_Who_collumn{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int projectid;
	private int repoid;
	private int templateid;
	
//	@JsonIgnore
//	@Transient
	  @OneToOne(cascade=CascadeType.ALL)
	private Rn_surepipe_Workflow workflow_instanceid;



}
