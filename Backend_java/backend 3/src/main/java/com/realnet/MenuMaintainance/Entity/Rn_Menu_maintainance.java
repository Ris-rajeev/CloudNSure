package com.realnet.MenuMaintainance.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Rn_Menu_maintainance {
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id;
	
	private String menu_item_name;
	
	private String sequence;
	
	private String module_name;
	private String status;
	private String original_menu;

}
