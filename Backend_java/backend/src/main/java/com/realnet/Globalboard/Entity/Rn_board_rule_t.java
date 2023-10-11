package com.realnet.Globalboard.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class Rn_board_rule_t extends Rn_Who_AccId_Column {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String rule_name;
	
	@Column(length=5000)
	private String description;
	
	private String on_event;
	private String updated_field;
	private int board_id;
	private String updated_value;
	private String to_updated_field;
	private String to_updated_value;
	private String alert_to;
	private String subject;
	
	@Column(length= 5000)
	private String message;
	private String in_to;
	private String assign_to;
	
	

}
