package com.realnet.formio.entity;

import javax.persistence.Entity;

import lombok.Data;


public class NewBoard {
	private Long bId;
	private String bName;
	private String type;
	private int project_id;
	private Long cId;
	private String cName;
	public NewBoard(Long bId, String bName, String type, int project_id, Long cId, String cName) {
		super();
		this.bId = bId;
		this.bName = bName;
		this.type = type;
		this.project_id = project_id;
		this.cId = cId;
		this.cName = cName;
	}
	public Long getbId() {
		return bId;
	}
	public void setbId(Long bId) {
		this.bId = bId;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public Long getcId() {
		return cId;
	}
	public void setcId(Long cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public NewBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
