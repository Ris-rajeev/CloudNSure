package com.realnet.flf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;

@Data
@Entity
@Table(name = "RN_FLF_LINE_T")
public class Rn_FLF_Line extends Rn_Who_AccId_Column {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "HEADER_ID")
	@JsonBackReference
	private Rn_FLF_Header rn_flf_header;

	@Column(name = "TECH_STACK")
	private String techStack;

	@Column(name = "OBJECT_TYPE")
	private String objectType;

	@Column(name = "SUB_OBJECT_TYPE")
	private String subObjectType;

	@Column(name = "JAVA")
	private String java;

	@Column(name = "JSP")
	private String jsp;

	@Column(name = "JAVASCRIPT")
	private String javascript;

	@Column(name = "XML")
	private String xml;

	@Column(name = "FIELDTYPE")
	private String fieldtype;

	public Rn_FLF_Line() {
		super();
	}

}