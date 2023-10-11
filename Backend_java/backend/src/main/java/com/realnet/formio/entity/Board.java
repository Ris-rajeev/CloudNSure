package com.realnet.formio.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
@Entity
@Table(name = "board")
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bId;
	private String bName;
	private String type;
	 private int project_id;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	@JsonIgnore
//	@Transient
	List<Column> columns = new ArrayList<>();
	
	@Transient
	private List<Column> cols;
//	@Transient
//	private Column cols;
	
	public Board() {}
	
	

	
	public Board(Long bId, String bName) {
		super();
		this.bId = bId;
		this.bName = bName;

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




	public List<Column> getColumns() {
		return columns;
	}




	public void setColumns(List<Column> columns) {
		this.columns = columns;
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
	
	
}
