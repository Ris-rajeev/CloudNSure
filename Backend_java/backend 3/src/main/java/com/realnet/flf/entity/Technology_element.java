package com.realnet.flf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Technology_element extends Rn_Who_AccId_Column{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String header_id;
	
	@Lob
    private String Model;
	
	@JsonBackReference
	@ManyToOne
	private Rn_Bcf_Technology_Stack technology_Stack;
	
	
}
