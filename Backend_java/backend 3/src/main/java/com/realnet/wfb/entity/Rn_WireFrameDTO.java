package com.realnet.wfb.entity;

import lombok.Data;

@Data
public class Rn_WireFrameDTO {
	String techStack;
	String objectType;
	String subObjectType;
	String uiName;
	String formType;
	boolean testing;
	int moduleId;
	Long backend_id;
	boolean child_form;

	// String menuName;
//	String tableName;
//	String formType;
//	String formCode;
//	String headerName;
	// String techStack;
	// String jsp_name;

}
