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

import com.realnet.ProjectManagement.Documents.Pm_Goal_Upload;
import com.realnet.formio.entity.Card;

import lombok.Data;

@Data
@Entity
public class Pm_Goal {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Column(length = 5000)
	private String description;
	private String tags;
	private String priority;
	private String based_on_version;
	private String affects_version;
	private String status;
	private String project;
	private String repository;	
	private String milestone;
	private String iteration;
	private String team;
	private String assignee;
	private String report_to;
	private String requestor;
	private String owner;
	private String type;
	private String start_time;
	private String end_time;
	
	private String time_estimates_in_hrs;
	
	@Transient
//	@OneToMany(cascade = CascadeType.ALL)
	private List<Pm_Goal_Upload> pm_Goal_Upload;
	
	@Transient
	private List<Card> cards;

}
