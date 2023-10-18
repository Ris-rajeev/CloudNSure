package com.realnet.Pipe.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class rn_surepipe_t extends Rn_Who_AccId_Column {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private int project_id;
	private int repo_id;
	private int template_id;
	private int wf_id;
	
	private String status;
	
	
}
