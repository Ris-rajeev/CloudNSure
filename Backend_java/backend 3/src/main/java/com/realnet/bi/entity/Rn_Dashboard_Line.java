package com.realnet.bi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.fnd.entity.Rn_Forms_Setup;

@Entity
@Table(name = "RN_DASHBOARD_LINE_T")
public class Rn_Dashboard_Line {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "PROJECT_ID")
	private int project_id;

	@Column(name = "SECTION_TYPE")
	private String section_type;

	@Column(name = "WIDGETS1")
	private String widgets1;

	@Column(name = "WIDGETS2")
	private String widgets2;

	@Column(name = "WIDGETS3")
	private String widgets3;

	@Column(name = "WIDGETS4")
	private String widgets4;

	@Column(name = "WIDGETS5")
	private String widgets5;

	@Column(name = "WIDGETS6")
	private String widgets6;

//	@Column(name = "HEADER_ID")
//	private int header_id;

	@Column(name = "ACCOUNT_ID")
	private int account_id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "header_id", nullable = false)
	@JsonBackReference
	private Rn_Dashboard_Header rn_dash_header;
	

	public Rn_Dashboard_Header getRn_dash_header() {
		return rn_dash_header;
	}

	public void setRn_dash_header(Rn_Dashboard_Header rn_dash_header) {
		this.rn_dash_header = rn_dash_header;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public int getHeader_id() {
//		return header_id;
//	}
//
//	public void setHeader_id(int header_id) {
//		this.header_id = header_id;
//	}

	public String getSection_type() {
		return section_type;
	}

	public void setSection_type(String section_type) {
		this.section_type = section_type;
	}

	public String getWidgets1() {
		return widgets1;
	}

	public void setWidgets1(String widgets1) {
		this.widgets1 = widgets1;
	}

	public String getWidgets2() {
		return widgets2;
	}

	public void setWidgets2(String widgets2) {
		this.widgets2 = widgets2;
	}

	public String getWidgets3() {
		return widgets3;
	}

	public void setWidgets3(String widgets3) {
		this.widgets3 = widgets3;
	}

	public String getWidgets4() {
		return widgets4;
	}

	public void setWidgets4(String widgets4) {
		this.widgets4 = widgets4;
	}

	public String getWidgets5() {
		return widgets5;
	}

	public void setWidgets5(String widgets5) {
		this.widgets5 = widgets5;
	}

	public String getWidgets6() {
		return widgets6;
	}

	public void setWidgets6(String widgets6) {
		this.widgets6 = widgets6;
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

}