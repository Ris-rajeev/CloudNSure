package com.realnet.ProjectManagement.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.ProjectManagement.Documents.Pm_milestone_Upload;
import com.realnet.ProjectManagement.Entity.Pm_Iteration;
import com.realnet.ProjectManagement.Entity.Pm_MilestoneTable;
import com.realnet.ProjectManagement.Entity.Projectmix;
import com.realnet.ProjectManagement.Repository.MilestoneRepository;
import com.realnet.ProjectManagement.Service.MilestoneService;

@RestController
public class MilestoneController {
	
	@Autowired
	private MilestoneService milestoneService;
	
	@Autowired
	private MilestoneRepository milestoneRepository;
	
	@PreAuthorize("hasAnyRole('SYSADMIN','SCUM_MASTER')")
	@PostMapping("/milestone")
	public ResponseEntity<?> updateOneOrder(@RequestParam String o1,
			@RequestParam Map<String, MultipartFile> attachmentFile) throws Exception {
		Pm_MilestoneTable pm_Milestone;
	
		pm_Milestone = new ObjectMapper().readValue(o1, Pm_MilestoneTable.class);
		Pm_MilestoneTable order = milestoneService.create(pm_Milestone);

		if (!attachmentFile.isEmpty()) {
			long c = 1;
			ArrayList<Pm_milestone_Upload> attachments = new ArrayList<Pm_milestone_Upload>();
			for (Map.Entry<String, MultipartFile> e : attachmentFile.entrySet()) {
				System.out.println(e.getKey());
				Pm_milestone_Upload a = new Pm_milestone_Upload();
				a.setPm_Milestone(order);
				a.setAttachmentId(order.getId());
				a.setAttachment(e.getValue().getBytes());
				a.setAttachmentType(e.getValue().getContentType());
				a.setAttachmentFilename(e.getValue().getName());
				attachments.add(a);
				c++;
			}
			milestoneService.addAllAtthacments(attachments);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	

//	update data with file
	@PreAuthorize("hasAnyRole('SYSADMIN','SCUM_MASTER')")
	@PutMapping("/milestone/{id}")
	public ResponseEntity<?> update(@RequestParam String o1,
			@RequestParam Map<String, MultipartFile> attachmentFile,
			@PathVariable int id) throws Exception {
		//this method is now onward for SAVE so status =P
		Pm_MilestoneTable pm_MilestoneTable;
	
		pm_MilestoneTable = new ObjectMapper().readValue(o1, Pm_MilestoneTable.class);
		Pm_MilestoneTable order = milestoneService.update(pm_MilestoneTable, id);

		if (!attachmentFile.isEmpty()) {
//			long c = 1;
			ArrayList<Pm_milestone_Upload> attachments = new ArrayList<Pm_milestone_Upload>();
			for (Map.Entry<String, MultipartFile> e : attachmentFile.entrySet()) {
				System.out.println(e.getKey());
				Pm_milestone_Upload a = new Pm_milestone_Upload();
				a.setPm_Milestone(order);
//				a.setAttachmentId(c);
//				a.setCreatedBy(a.getCreatedBy());
				a.setAttachment(e.getValue().getBytes());
				a.setAttachmentType(e.getValue().getContentType());
				a.setAttachmentFilename(e.getValue().getOriginalFilename());
				attachments.add(a);
//				c++;
			}
			milestoneService.addAllAtthacments(attachments);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	

	
	
//	Get all
	@GetMapping("/milestone")
	public ResponseEntity<?> getall(){
		List<Pm_MilestoneTable> pm = milestoneService.getall();
	return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	
//	 get by milestone id with uploaded files
	
	@GetMapping("/milestone/{id}")
	
	public ResponseEntity<?> getbymileId(@PathVariable int id){
		Pm_MilestoneTable pm = milestoneService.getbyid(id);
		
		List<Pm_milestone_Upload> attachments = milestoneService.getallattachmentsbyid(id);
		pm.setPm_milestone_Uploads(attachments);
		
		return 	new ResponseEntity<>(pm, HttpStatus.OK);	
	}
	
//	Delete by milestone Id
	@DeleteMapping("/milestone/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
//		Pm_MilestoneTable order = milestoneService.getbyid(id);
//		if(order != null) {
			milestoneService.deleteAttachment(id);
			milestoneService.deletebyid(id);
//		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		}

	
	//GET ID AND NAME
		 @GetMapping("/milestonename")
		    public ResponseEntity<?> getmile() {
		    	
		    	List<Projectmix> s = milestoneRepository.milestone();
		    	return new ResponseEntity<>(s,HttpStatus.OK);
		    }
	

}
