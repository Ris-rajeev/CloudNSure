package com.realnet.ProjectManagement.Documents;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.ProjectManagement.Entity.Pm_Feature;
import lombok.Data;

@Entity
@Data
public class Pm_Feature_ListOfTasks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JsonBackReference
	@ManyToOne
	private Pm_Feature pm_Feature;
	
	private String name;

	
	

}
