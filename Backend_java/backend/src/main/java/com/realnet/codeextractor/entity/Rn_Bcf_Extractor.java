package com.realnet.codeextractor.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.fnd.entity.Rn_Who_Columns;

import lombok.Data;

@Data
@Entity
@Table(name = "RN_BCF_EXTRACTOR_T")
public class Rn_Bcf_Extractor extends Rn_Who_Columns {
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

	private String service;

	@Column(name = "TECH_STACK_KEY")
	private String tech_stack_key;

	@Column(name = "OBJECT_TYPE")
	private String object_type;

	@Column(name = "SUB_OBJECT_TYPE")
	private String sub_object_type;

	@Column(name = "FORM_TYPE_NAME")
	private String form_type_name;

	@Column(name = "STD_WF_NAME")
	private String std_wf_name;

	@Column(name = "ICON_FILE_NAME")
	private String icon_file_name;

	@Column(name = "SAMPLE_FILE_NAME")
	private String sample_file_name;

	@Column(name = "EXTRACTOR_STAGE")
	private String extractor_stage;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "rn_bcf_extractor")
	@JsonManagedReference
	private List<Build_controller> build_controller;

	@OneToMany(mappedBy = "rn_bcf_extractor", targetEntity = Rn_Bcf_Extractor_Params.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Rn_Bcf_Extractor_Params> rn_bcf_extractor_Params;

	// SYSTEM ACCOUNT ID
	@Column(name = "ACCOUNT_ID")
	private long accountId;

}
