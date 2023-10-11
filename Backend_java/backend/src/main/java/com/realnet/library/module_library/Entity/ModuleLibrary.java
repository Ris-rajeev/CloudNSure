package com.realnet.library.module_library.Entity;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.Transient;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnore;
import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;
import com.realnet.library.project_library.Entity.ProjectLibrary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleLibrary extends Rn_Who_AccId_Column{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID")
	@JsonBackReference
	private ProjectLibrary project;

	@Transient
	private String projectName;
	
	@Transient
	private int projectId;
	
	//new Field added
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Backend_Config_Lib> backends = new ArrayList();
	
	
}
