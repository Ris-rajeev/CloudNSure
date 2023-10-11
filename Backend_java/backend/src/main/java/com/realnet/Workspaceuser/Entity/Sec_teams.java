package com.realnet.Workspaceuser.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import lombok.Data;

@Entity
@Data
public class Sec_teams  extends Rn_Who_AccId_Column{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	private String name;
	private String description;
	private String Is_active;

}
