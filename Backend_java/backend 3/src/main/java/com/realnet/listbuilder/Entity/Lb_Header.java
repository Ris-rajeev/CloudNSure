package com.realnet.listbuilder.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.Dashboard1.Entity.Dashbord1_Line;
import com.realnet.Dashboard1.Entity.dashbord_Who_collumn;

import lombok.Data;
import lombok.ToString;

@ToString(exclude = { "lb_Line" })
@Entity
@Data
public class Lb_Header extends dashbord_Who_collumn {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

// ==============================

	@Column(name = "MENU_NAME")
	private String menuName;

	@Column(name = "IS_UPDATED")
	private boolean updated;

	private String tech_Stack;

	private String object_type;
	private String sub_object_type;

	@Column(name = "IS_BUILD")
	private boolean build;
	private boolean testing;

	private String lb_name;

	private int module_id;

	private String description;

	private String secuirity_profile;

	private Long backend_id;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "lb_Header")
	private List<Lb_Line> lb_Line = new ArrayList<>();

}
