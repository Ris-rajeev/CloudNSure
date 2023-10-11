package com.realnet.wfb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.actionbuilder.entity.Rn_cff_ActionBuilder_Header;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;
import lombok.ToString;

@ToString(exclude = { "rn_fb_lines", "module", "rn_cff_actionBuilder" })
@Entity
@Table(name = "RN_FB_HEADER")
@Data
public class Rn_Fb_Header extends Rn_Who_AccId_Column {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

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
// ==============================
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

	private Long backend_id;

	private boolean testing;

	private boolean child_form;
	
	private boolean add_tomobile;
	
	private Integer ServiceTechid;
	
	private boolean editable;


	// ------ RELATIONSHIP ---------
//	@OneToMany(mappedBy = "rn_fb_header", targetEntity = Rn_Fb_Line.class, cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<Rn_Fb_Line> rn_fb_lines;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODULE_ID")
	@JsonBackReference
	private Rn_Module_Setup module;

	// --- action builder relation --
	@OneToMany(mappedBy = "rn_fb_header", targetEntity = Rn_cff_ActionBuilder_Header.class, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Rn_cff_ActionBuilder_Header> rn_cff_actionBuilder;

}