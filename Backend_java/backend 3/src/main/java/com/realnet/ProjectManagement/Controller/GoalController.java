package com.realnet.ProjectManagement.Controller;

import java.io.IOException;
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
import com.realnet.ProjectManagement.Documents.Pm_Goal_Upload;
import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;
import com.realnet.ProjectManagement.Entity.Pm_Goal;
import com.realnet.ProjectManagement.Entity.Pm_Iteration;
import com.realnet.ProjectManagement.Entity.ProjectDto;
import com.realnet.ProjectManagement.Entity.Projectmix;
import com.realnet.ProjectManagement.Repository.GoalRepository;
import com.realnet.ProjectManagement.Service.GoalService;

@RestController
public class GoalController {

	@Autowired
	private GoalService goalService;

	@Autowired
	private GoalRepository goalRepository;

	@PreAuthorize("hasAnyRole('SYSADMIN','SCUM_MASTER')")
	@PostMapping("/goal")
	public ResponseEntity<?> add(@RequestParam String o1, @RequestParam Map<String, MultipartFile> attachmentsfile)
			throws IOException {
		Pm_Goal pm_Goal;
		pm_Goal = new ObjectMapper().readValue(o1, Pm_Goal.class);
		Pm_Goal order = goalService.create(pm_Goal);

		if (!attachmentsfile.isEmpty()) {
//			long cLong = 1;
			ArrayList<Pm_Goal_Upload> attachments = new ArrayList<Pm_Goal_Upload>();
			for (Map.Entry<String, MultipartFile> e : attachmentsfile.entrySet()) {

				System.out.println(e.getKey());
				Pm_Goal_Upload a = new Pm_Goal_Upload();
				a.setPm_Goal(order);
				a.setAttachmentId(order.getId());
				a.setAttachment(e.getValue().getBytes());
				a.setAttachmentType(e.getValue().getContentType());
				a.setAttachmentFilename(e.getValue().getOriginalFilename());
				attachments.add(a);
//			cLong++;

			}
			goalService.addallattachments(attachments);

		}

		return new ResponseEntity<>(order, HttpStatus.OK);

	}

//	update data with file
	@PreAuthorize("hasAnyRole('SYSADMIN','SCUM_MASTER')")
	@PutMapping("/goal/{id}")
	public ResponseEntity<?> update(@RequestParam String o1, @RequestParam Map<String, MultipartFile> attachmentFile,
			@PathVariable int id) throws Exception {
		// this method is now onward for SAVE so status =P
		Pm_Goal pm_Goal;

		pm_Goal = new ObjectMapper().readValue(o1, Pm_Goal.class);
		Pm_Goal order = goalService.update(pm_Goal, id);

		if (!attachmentFile.isEmpty()) {
//			long c = 1;
			ArrayList<Pm_Goal_Upload> attachments = new ArrayList<Pm_Goal_Upload>();
			for (Map.Entry<String, MultipartFile> e : attachmentFile.entrySet()) {
				System.out.println(e.getKey());
				Pm_Goal_Upload a = new Pm_Goal_Upload();
				a.setPm_Goal(order);
//				a.setAttachmentId(c);
//				a.setCreatedBy(a.getCreatedBy());
				a.setAttachment(e.getValue().getBytes());
				a.setAttachmentType(e.getValue().getContentType());
				a.setAttachmentFilename(e.getValue().getOriginalFilename());
				attachments.add(a);
//				c++;
			}
			goalService.addallattachments(attachments);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

//	Get all
	@GetMapping("/goal")
	public ResponseEntity<?> getall() {
		List<Pm_Goal> pm = goalService.getall();
		return new ResponseEntity<>(pm, HttpStatus.OK);
	}

//	get by id
	@GetMapping("/goal/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id) {
		Pm_Goal pm = goalService.getbyid(id);

		List<Pm_Goal_Upload> orderAttachments = goalService.getallattachmentsbyid(id);

		pm.setPm_Goal_Upload(orderAttachments);

		return new ResponseEntity<>(pm, HttpStatus.OK);
	}

//	delete by id
	@DeleteMapping("/goal/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id) {
//		Pm_Goal order = goalService.getbyid(id);
//		if(order != null) {
		goalService.deleteAttachment(id);
		goalService.deletebyid(id);
//		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

// GET ID AND NAME
	@GetMapping("/goalname")
	public ResponseEntity<?> getgoal() {

		List<ProjectDto> s = goalService.getall1();

		return new ResponseEntity<>(s, HttpStatus.OK);
	}

}
