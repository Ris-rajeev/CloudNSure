package com.realnet.codeextractor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "RN_BCF_EXCEPTION_RULES")
public class Rn_Bcf_Exception_Rules extends Rn_Who_AccId_Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "TECH_STACK")
	private String tech_stack;

	@Column(name = "OBJECT_TYPE")
	private String object_type;

	@Column(name = "SUB_OBJECT_TYPE")
	private String sub_object_type;

	@Column(name = "OBJECT_NAME_VARIABLE")
	private String object_name_variable;

	@Column(name = "OBJECT_NAME_DYNAMIC_STRING")
	private String object_name_dynamic_string;

	private String type;
	private String service;

	private String path;
	private String file_name;
	private String startstring;
	private String endstring;
	private String replaceWith;
	private String linestring;
	private String keyword;

	public Rn_Bcf_Exception_Rules() {
		super();
		// TODO Auto-generated constructor stub
	}

}
