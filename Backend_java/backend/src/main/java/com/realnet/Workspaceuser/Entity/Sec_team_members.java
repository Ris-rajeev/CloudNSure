package com.realnet.Workspaceuser.Entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class Sec_team_members {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int team_id;
	private Long member_id;
	private boolean member_type;
	private String access_days;
	private Date access_start_date;
	private String member_name;
}
