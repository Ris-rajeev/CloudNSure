package com.realnet.Gaurav_testing.Entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Gaurav_testing_t {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String email;
	private String mobno;
	private String address;
	private String pincode;
	private String description;


}