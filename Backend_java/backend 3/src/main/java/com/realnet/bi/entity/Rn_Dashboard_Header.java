package com.realnet.bi.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.fnd.entity.Rn_Forms_Component_Setup;

@Entity
@Table(name = "RN_DASHBOARD_HEADER_T")
public class Rn_Dashboard_Header {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")

	@Column(name = "HEADER_ID")
	private int header_id;

	@Column(name = "DASHBOARD_NAME")
	private String dashboard_name;

	@Column(name = "ACCOUNT_ID")
	private int account_id;

	@Column(name = "PROJECT_ID")
	private int project_id;

	@Column(name = "IS_BUILD")
	private String is_build;

	@Column(name = "IS_UPDATED")
	private String is_updated;

	@Column(name = "MODULE_ID")
	private int module_id;
	
	@Column(name = "ROUTING_ADD")
	private char routing_add;
	
	public char getRouting_add() {
		return routing_add;
	}

	public void setRouting_add(char routing_add) {
		this.routing_add = routing_add;
	}

	@OneToMany(mappedBy = "rn_dash_header", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Rn_Dashboard_Line> components;
	
	
	
	public List<Rn_Dashboard_Line> getComponents() {
		return components;
	}

	public void setComponents(List<Rn_Dashboard_Line> components) {
		this.components = components;
	}
	

	public int getModule_id() {
		return module_id;
	}

	public void setModule_id(int module_id) {
		this.module_id = module_id;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getIs_build() {
		return is_build;
	}

	public void setIs_build(String is_build) {
		this.is_build = is_build;
	}

	public String getIs_updated() {
		return is_updated;
	}

	public void setIs_updated(String is_updated) {
		this.is_updated = is_updated;
	}

	public int getHeader_id() {
		return header_id;
	}

	public void setHeader_id(int header_id) {
		this.header_id = header_id;
	}

	public String getDashboard_name() {
		return dashboard_name;
	}

	public void setDashboard_name(String dashboard_name) {
		this.dashboard_name = dashboard_name;
	}

}