package com.realnet.Wf_library.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;

@Entity
@Data
public class Wf_library_t extends Rn_Who_AccId_Column {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TECH_STACK")
	private String techStack;

	@Column(name = "OBJECT_TYPE")
	private String objectType;

	@Column(name = "SUB_OBJECT_TYPE")
	private String subObjectType;

	@Column(name = "UI_NAME")
	private String uiName;

	@Column(name = "FORM_TYPE")
	private String formType;

	@Column(name = "TABLE_NAME")
	private String tableName;

	@Column(name = "LINE_TABLE_NAME")
	private String lineTableName;

	@Column(name = "MULTILINE_TABLE_NAME")
	private String multilineTableName;

	@Column(name = "FORM_CODE")
	private String formCode;
	// =========================
	@Column(name = "JSP_NAME")
	private String jspName;

	@Column(name = "CONTROLLER_NAME")
	private String controllerName;

	@Column(name = "SERVICE_NAME")
	private String serviceName;

	@Column(name = "SERVICE_IMPL_NAME")
	private String serviceImplName;

	@Column(name = "DAO_NAME")
	private String daoName;

	@Column(name = "DAO_IMPL_NAME")
	private String daoImplName;
//==============================
	@Column(name = "IS_BUILD")
	private boolean build;

	@Column(name = "IS_UPDATED")
	private boolean updated;

	@Column(name = "MENU_NAME")
	private String menuName;

	@Column(name = "HEADER_NAME")
	private String headerName;

	@Column(name = "CONVERTED_TABLE_NAME")
	private String convertedTableName;

	private boolean testing;
	
	private boolean ischild;
	
	private Long libraryId;

	@Lob
	private String model;
}