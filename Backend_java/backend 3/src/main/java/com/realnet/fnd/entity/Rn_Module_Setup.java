package com.realnet.fnd.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.rb.entity.Rn_report_builder;
import com.realnet.wfb.entity.Rn_Fb_Header;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = { "project", "rn_fb_headers", "rn_report_builder" })
@Entity
@Table(name = "RN_MODULE_SETUP")
public class Rn_Module_Setup extends Rn_Who_AccId_Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "MODULE_NAME")
	private String moduleName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "MODULE_PREFIX")
	private String modulePrefix;

	@Column(name = "COPY_TO")
	private String copyTo;

	@Column(name = "TECHNOLOGY_STACK")
	private String technologyStack;

	@Lob
	private String tags;
	private boolean readme;
	private String dbName;
	private String dbUserName;
	private String dbPassword;
	private String portNumber;
	private boolean microservice;
	private boolean portaldeployment;
	private String parentrepo;

	private boolean copy_baseproj;
	private boolean loginservice;

	private boolean testing;

	private String type;

//	@Column(name = "IS_BUILD")
//	private boolean build;

	// this should be many to one
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@JsonBackReference
	private Rn_Project_Setup project;

	@OneToMany(mappedBy = "module", targetEntity = Rn_Fb_Header.class, orphanRemoval = true, cascade = CascadeType.PERSIST)
	@JsonManagedReference
	private List<Rn_Fb_Header> rn_fb_headers;

	@OneToMany(mappedBy = "module", targetEntity = Rn_report_builder.class, orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Rn_report_builder> rn_report_builder;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JsonIgnoreProperties("module_Setups")
	private Set<BackendConfig_t> backendConfig_ts = new HashSet<>();

	@Transient
	private int projectId;

	@Transient
	private String projectName;

}