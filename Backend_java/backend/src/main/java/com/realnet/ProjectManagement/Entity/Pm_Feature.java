package com.realnet.ProjectManagement.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.ProjectManagement.Documents.Pm_Feature_ListOfTasks;

import lombok.Data;

@Data
@Entity
public class Pm_Feature {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Column(length = 5000)
	private String description;
	private String tags;
	private String priority;
	private String project;
	private String repository;	
	private String milestone;
	private String iteration;
	private String goal;
	private String user_story;

	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pm_Feature")
	private List< Pm_Feature_ListOfTasks> pm_Feature_ListOfTasks = new ArrayList<>();


}
