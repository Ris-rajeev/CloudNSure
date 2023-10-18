package com.realnet.Wf_library.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.Dashboard1.Entity.dashbord_Who_collumn;

import lombok.Data;
import lombok.ToString;

@ToString(exclude = { "WfLib_Lb_Header" })
@Data
@Entity
@Table
public class Wf_lib_Lb_Line extends dashbord_Who_collumn {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String header_id;

	@Column(length = 15000)
	private String Model;

	@JsonBackReference
	@ManyToOne
	private WfLib_Lb_Header lb_Header;

}
