package com.realnet.flf.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;

@Data
@Entity
@Table(name = "RN_FLF_HEADER_T")
public class Rn_FLF_Header extends Rn_Who_AccId_Column {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "TECH_STACK")
	private String techStack;

	@Column(name = "OBJECT_TYPE")
	private String objectType;

	private String service;

	@Column(name = "SUB_OBJECT_TYPE")
	private String subObjectType;

	@Column(name = "FIELDTYPE")
	private String fieldtype;

	@Column(name = "IS_ACTIVE")
	private boolean active;

	@Column(name = "IS_BUILD")
	private boolean build;

	@OneToMany(mappedBy = "rn_flf_header", targetEntity = Rn_FLF_Line.class, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Rn_FLF_Line> rn_flf_lines;

	public Rn_FLF_Header() {
		super();
	}

	public Rn_FLF_Header(int id, String techStack, String objectType, String subObjectType, String fieldtype,
			List<Rn_FLF_Line> rn_flf_lines, boolean active, boolean build) {
		super();
		this.id = id;
		this.techStack = techStack;
		this.objectType = objectType;
		this.subObjectType = subObjectType;
		this.fieldtype = fieldtype;
		this.rn_flf_lines = rn_flf_lines;
		this.active = active;
		this.build = build;
	}

}
