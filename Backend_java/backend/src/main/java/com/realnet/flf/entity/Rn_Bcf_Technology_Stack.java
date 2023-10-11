package com.realnet.flf.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Data
@Getter
@Setter
@Table(name = "RN_BCF_TECHNOLOGY_STACK")
public class Rn_Bcf_Technology_Stack extends Rn_Who_AccId_Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "TECH_STACK")
	private String tech_stack;

	@Column(name = "TECH_STACK_KEY")
	private String tech_stack_key;

	private String servicetype;

	// Big text
	@Column(name = "TAGS")
	private String tags;
	private String description;
	private String version;

	@Column(name = "BASE_PRJ_FILE_NAME")
	private String base_prj_file_name;

	@Column(name = "IS_ACTIVE")
	private boolean active;
//	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "technology_Stack", targetEntity = Technology_element.class)
	private List<Technology_element> technology_elements = new ArrayList<>();

}
