package com.realnet.ProjectManagement.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.realnet.ProjectManagement.Documents.Pm_milestone_Upload;

import lombok.Data;

@Entity
@Data
public class Pm_MilestoneTable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Column(length = 5000)
	private String description;
	private String tags;
	private String priority;
	private String billiable;
	private String status;
	private String project;
	
	private String repository;
	private String iteration;
	
	private String team;
	private String report_to;
	private String start_time;
	private String end_time;
	private String time_estimates_in_hrs;
	
	@Transient
//	@OneToMany(cascade = CascadeType.ALL)
	private List<Pm_milestone_Upload> pm_milestone_Uploads;
}
