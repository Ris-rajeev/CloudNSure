package com.realnet.workflow.Entites;

import lombok.Data;

@Data
public class Connection {
	
	private String id;
	private String project_name;
//	@JsonIgnore
	private String connector_json;
}
