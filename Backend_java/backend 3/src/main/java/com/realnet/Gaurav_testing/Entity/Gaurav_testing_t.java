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
	private String mobno;
	private String address;
	private String pincode;
	private String description;
	private String text;
	private String password;
	private Long number;
	private LocalDateTime datetime;
	private Date date;
	private String paragraph;
	private String textarea;
	private String dropdowm;
	private String autocomplete;
	private Boolean radio;
	private Boolean checkbox;
	private String email;
	private Long phonenumber;
	private Long calculated_field;
	private String section;
	private String division;

}