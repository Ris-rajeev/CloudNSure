package com.realnet.Rpt_builder2.Entity;

import lombok.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realnet.Rpt_builder2_lines.Entity.Rpt_builder2_lines_t;
import com.realnet.fnd.entity.Rn_Who_AccId_Column;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Rpt_builder2_t  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reportName;
	private String description;
	private Boolean active;
//	private String folderName;
//
//	private String conn_name;
//	private String date_param_req;
//	private String std_param_html;
//	private String adhoc_param_html;
//	private String column_str;
//	private String sql_str;
	
	private Boolean isSql;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "rpt_builder2_t")
	private List<Rpt_builder2_lines_t> Rpt_builder2_lines = new ArrayList<>();
	

}