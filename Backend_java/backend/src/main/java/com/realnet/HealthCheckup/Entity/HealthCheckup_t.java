package com.realnet.HealthCheckup.Entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class HealthCheckup_t {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ip;
	private Long port;
	private String serviceName;
	private String filepath;

	private boolean createProject;
	private boolean buildProject;
	private boolean createDeployment;
	private boolean deployApp;

}