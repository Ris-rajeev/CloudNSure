package com.realnet.formio.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class Boardmicro {

	private Long bId;
	private String bName;
	private String type;
	private int project_id;

	private Long cId;
	private String cName;

	private Long id;
	private Long assigned_to;
	private String title;
	private Date date_created;
	private String date;
	private String image;
	private String presentColumn;

	// extra variables of card for making changes

	// variables for description
	private String description;
	private String tags;

	// variables for Time and Assignment
	private String storyPoints;
	private String valuePoints;
	private String issueType;

	private String scheduledDate;
	private String attachmentTaA;

	// variables for Definition of Ready
	private String story_details;
	private String scenario_given_when;
	private String data_expected_result;
	private String functional_area_passfail_details;
	private String scalibility;
	private String test_case_demo_case;
	private String estimates;
	private String dor_approval_status;
	

	// variables for Definition of Done
	private boolean gitCommited = false;
	private boolean code_quality_review_done = false;
	private boolean peer_revired = false;
	private boolean builds_without_error = false;
	private String unit_test_details;
	private boolean promoted_test_instance = false;
	private boolean configuration_changes = false;
	private String unit_test_details2;
	private boolean closed_for_clocking = false;
	private String attachmentDoD;

	// static value variables
	private String priority_index;
	private String related_issue;
	// Time Stats variables
	private String estimated_time;
	private String elapsed_time;
	private String start_date_time;
	private String completion_date_time;
	// Other Details
	private String project;
	private String repository;
	private String sprint_test;
	private String current_status;
	private String clock_status;
	private String project_priority;
	private String pinned_status;
	private Long requested_by;
	private String milestone;
	private String iteration;
	private String goal;
	
	
	
	
	

}
