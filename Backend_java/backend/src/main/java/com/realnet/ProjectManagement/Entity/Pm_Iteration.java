package com.realnet.ProjectManagement.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pm_Iteration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Column(length = 5000)
	private String description;
	private String tags;
	private String category;
	private String status;
	private String project;
	
	private String repository;
	
	private String team;
	private String report_to;
	private String start_time;
	private String end_time;
	private String time_estimates_in_hrs;
	
	@Transient
//	@OneToMany(cascade = CascadeType.ALL)
	private List<Pm_Uploadphoto> doc;
	

//	private int photo;

}
