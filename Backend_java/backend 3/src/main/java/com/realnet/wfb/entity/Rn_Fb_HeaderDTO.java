package com.realnet.wfb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.actionbuilder.entity.Rn_cff_ActionBuilder_Header;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;

@Data
public class Rn_Fb_HeaderDTO {

	private String techStack;
	private String objectType;
	private String subObjectType;
	private String uiName;
	private String formType;
	private String tableName;
	private String lineTableName;
	private String multilineTableName;
	private String formCode;
	private String jspName;
	private String controllerName;
	private String serviceName;
	private String serviceImplName;
	private String daoName;
	private String daoImplName;
// ==============================
	private boolean build;
	private boolean updated;
	private String menuName;
	private String headerName;
	private String convertedTableName;

//	private List<Rn_Fb_Line> rn_fb_lines;
	private Rn_Module_Setup module;
	
	// --- action builder relation --
	private List<Rn_cff_ActionBuilder_Header> rn_cff_actionBuilder;

	// ----------- get set

	
//	@Override
//	public String toString() {
//		return "Rn_Fb_Header [techStack=" + techStack + ", objectType=" + objectType + ", subObjectType="
//				+ subObjectType + ", uiName=" + uiName + ", formType=" + formType + ", tableName=" + tableName
//				+ ", lineTableName=" + lineTableName + ", multilineTableName=" + multilineTableName + ", formCode="
//				+ formCode + ", jspName=" + jspName + ", controllerName=" + controllerName + ", serviceName="
//				+ serviceName + ", serviceImplName=" + serviceImplName + ", daoName=" + daoName + ", daoImplName="
//				+ daoImplName + ", build=" + build + ", updated=" + updated + ", menuName=" + menuName + ", headerName="
//				+ headerName + ", convertedTableName=" + convertedTableName + ", rn_fb_lines=" + rn_fb_lines
//				+ ", module=" + module + ", rn_cff_actionBuilder=" + rn_cff_actionBuilder + "]";
//	}

	

}