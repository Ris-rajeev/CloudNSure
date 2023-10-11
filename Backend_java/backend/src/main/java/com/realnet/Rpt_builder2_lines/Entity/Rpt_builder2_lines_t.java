package com.realnet.Rpt_builder2_lines.Entity;

import lombok.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.Rpt_builder2.Entity.Rpt_builder2_t;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Rpt_builder2_lines_t {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String header_id;
	@Lob
	private String model;

	@JsonBackReference
	@ManyToOne
	private Rpt_builder2_t  rpt_builder2_t;

}