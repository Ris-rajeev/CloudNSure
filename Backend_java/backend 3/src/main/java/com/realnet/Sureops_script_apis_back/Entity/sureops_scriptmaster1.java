package com.realnet.Sureops_script_apis_back.Entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class sureops_scriptmaster1 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String script_name;
	private String description;
	private String techstack;
	private String version;
	private String script;
	private boolean isactive;
}
