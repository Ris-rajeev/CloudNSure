package com.realnet.ProjectManagement.Documents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realnet.ProjectManagement.Entity.Pm_Iteration;
import com.realnet.ncso.entity1.OrderDetails;

import lombok.Data;

@Entity
@Data
public class Pm_Uploadphoto {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int attachmentId;
	
	@JsonBackReference
	@ManyToOne
	private Pm_Iteration pm_Iteration;
	
	@Lob
	@Column(nullable = true)
	private byte[] attachment;
	
	@Column(nullable = true)
	private String attachmentType;
	
	@Column(nullable = true)
	private String attachmentFilename;
	
	@Column(nullable = false)
	private String cancelStatus="N";
	
//	private String createdBy;
	
	@Column(nullable = true)
	private String updatedBy;
	
	private String externalFlag;

}
