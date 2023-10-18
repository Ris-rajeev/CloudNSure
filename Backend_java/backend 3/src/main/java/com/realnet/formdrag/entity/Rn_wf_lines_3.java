package com.realnet.formdrag.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "rn_wf_lines_3")
public class Rn_wf_lines_3 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer header_id;
	
	@Lob
	private String model;
	
	
	
	
	
}
