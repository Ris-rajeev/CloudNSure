package com.realnet.ProjectManagement.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.ProjectManagement.Documents.Pm_User_Upload;
import com.realnet.ProjectManagement.Entity.Pm_User;
import com.realnet.ProjectManagement.Entity.Projectmix;
import com.realnet.ProjectManagement.Repository.Pm_UserRepo;
import com.realnet.ProjectManagement.Service.UserService;

@RestController
public class Pm_UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Pm_UserRepo pm_UserRepo;

	
	@PostMapping("/pmUser")
	public ResponseEntity<?> add(@RequestParam String o1, 
			                      @RequestParam Map<String, MultipartFile> attachmentsfile ) throws IOException {
		Pm_User pm_User;
		pm_User = new ObjectMapper().readValue(o1, Pm_User.class);
		Pm_User order= userService.create(pm_User);
		
		if(!attachmentsfile.isEmpty()) {
			ArrayList<Pm_User_Upload> attachments = new ArrayList<Pm_User_Upload>();
			for (Map.Entry<String, MultipartFile> e : attachmentsfile.entrySet()) {
				
			System.out.println(e.getKey());
			Pm_User_Upload a = new Pm_User_Upload();
			a.setPm_User(order);
			a.setAttachmentId(order.getId());
			a.setAttachment(e.getValue().getBytes());
			a.setAttachmentType(e.getValue().getContentType());
			a.setAttachmentFilename(e.getValue().getOriginalFilename());
			attachments.add(a);
			
			}
			userService.addallattachments(attachments);
	
		}
		
		return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	
//	update data with file
	@PutMapping("/pmUser/{id}")
	public ResponseEntity<?> update(@RequestParam String o1,
			@RequestParam Map<String, MultipartFile> attachmentFile,
			@PathVariable int id) throws Exception {
		//this method is now onward for SAVE so status =P
		Pm_User pm_Feature;
	
		pm_Feature = new ObjectMapper().readValue(o1, Pm_User.class);
		Pm_User order = userService.update(pm_Feature, id);

		if (!attachmentFile.isEmpty()) {
//			long c = 1;
			ArrayList<Pm_User_Upload> attachments = new ArrayList<Pm_User_Upload>();
			for (Map.Entry<String, MultipartFile> e : attachmentFile.entrySet()) {
				System.out.println(e.getKey());
				Pm_User_Upload a = new Pm_User_Upload();
				a.setPm_User(order);
//				a.setAttachmentId(c);
//				a.setCreatedBy(a.getCreatedBy());
				a.setAttachment(e.getValue().getBytes());
				a.setAttachmentType(e.getValue().getContentType());
				a.setAttachmentFilename(e.getValue().getOriginalFilename());
				attachments.add(a);
//				c++;
			}
			userService.addallattachments(attachments);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	

	
	
//	Get all
	@GetMapping("/pmUser")
	public ResponseEntity<?> getall(){
		List<Pm_User> pm = userService.getall();
	return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	

//	get by id
	@GetMapping("/pmUser/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
		Pm_User pm = userService.getbyid(id);
		
		List<Pm_User_Upload> orderAttachments = userService.getallattachmentsbyid(id);
		
		pm.setPm_User_Uploads(orderAttachments);


		return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	
	
	
//	delete by id
	@DeleteMapping("/pmUser/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
//		Pm_Goal order = goalService.getbyid(id);
//		if(order != null) {
		userService.deleteAttachment(id);
		userService.deletebyid(id);
//		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		}
	
	//GET ID AND NAME
		 @GetMapping("/pmusername")
		    public ResponseEntity<?> getuser() {
		    	
		    	List<Projectmix> s = pm_UserRepo.userstory();
		    	return new ResponseEntity<>(s,HttpStatus.OK);
		    }
}
