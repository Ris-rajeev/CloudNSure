package com.realnet.HealthCheckup.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class HealthCheckFiles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String file_name;
	private String file_path;
	private String serviceName;

	private Long healthcheckup_id;

}
