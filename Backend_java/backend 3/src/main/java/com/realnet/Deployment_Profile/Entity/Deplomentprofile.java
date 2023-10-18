package com.realnet.Deployment_Profile.Entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Deplomentprofile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String profile_name;
	private String end_date;
	private String active_flag;
	private String desc_header_table;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Deplomentprofilelines deplomentprofilelines;

}