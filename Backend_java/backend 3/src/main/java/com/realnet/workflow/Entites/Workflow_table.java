package com.realnet.workflow.Entites;

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
import com.realnet.Dashboard1.Entity.dashbord_Who_collumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class Workflow_table extends dashbord_Who_collumn{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String workflow_name;
	
	@Column(length = 5000)
	private String description;
	private String tags;
	private String app_category;
	private String select_technology;
	private String callable;

	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "workflow_table")
	private List<Workflow_Line> workflow_Lines = new ArrayList<>();
	
	

}
