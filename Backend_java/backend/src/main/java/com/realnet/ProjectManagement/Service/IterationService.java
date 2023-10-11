package com.realnet.ProjectManagement.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;
import com.realnet.ProjectManagement.Entity.Pm_Iteration;
import com.realnet.ProjectManagement.Entity.ProjectDto;
import com.realnet.ProjectManagement.Repository.IterationNewRepository;
import com.realnet.ProjectManagement.Repository.IterationRepository;
import com.realnet.ProjectManagement.UploadRepository.UploadpicRepository;
import com.realnet.ncso.entity1.OrderAttachment;

@Service
public class IterationService {
	

	@Autowired
	private UploadpicRepository uploadpicRepository;
	
	@Autowired
private	IterationRepository iterationRepository;
	
	@Autowired
	private IterationNewRepository iterationNewRepository;
	
	public Pm_Iteration create(Pm_Iteration rnrule) {
		return iterationRepository.save(rnrule);
	}

	
	public List<Pm_Iteration> getall() {
		return (List<Pm_Iteration>) iterationRepository.findAll();
	}

	
	public Pm_Iteration getbyid(int id) {
		return iterationRepository.findById(id);
	}
	
	public Optional<Pm_Iteration> getbyOptionalid(int id){
		return iterationNewRepository.findById(id);
	}


	public Pm_Iteration update(Pm_Iteration project, int id) {
		Pm_Iteration pm = iterationRepository.findById(id);
		
		pm.setCategory(project.getCategory());
		pm.setDescription(project.getDescription());
		pm.setEnd_time(project.getEnd_time());
		pm.setName(project.getName());
		pm.setProject(project.getProject());
		pm.setReport_to(project.getReport_to());
		pm.setRepository(project.getRepository());
		pm.setStart_time(project.getStart_time());
		pm.setStatus(project.getStatus());
		pm.setTags(project.getTags());
		pm.setTeam(project.getTeam());
		pm.setTime_estimates_in_hrs(project.getTime_estimates_in_hrs());
				
		return iterationRepository.save(pm);
	}


	public void deletebyid(int id) {
		iterationRepository.deleteById(id);
	}


//	public Pm_Iteration getJson(String user, List<MultipartFile> file) {
//		Pm_Iteration userJson = new Pm_Iteration();
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			userJson = objectMapper.readValue(user, Pm_Iteration.class);
//		} catch (IOException err) {
//			System.out.printf("Error", err.toString());
//		}
//		
//		int fileCount = file.size();
//		userJson.setPhoto(fileCount);
//		
//		return iterationRepository.save(userJson);
//	}


	public List<Pm_Uploadphoto> addAllAtthacments(List<Pm_Uploadphoto> attachments){
		for(Pm_Uploadphoto at: attachments) {
			uploadpicRepository.save(at);
		}
		return attachments;
}

	public void deleteAttachment(int pm_iteration_id) {
		// TODO Auto-generated method stub
		List<Pm_Uploadphoto> attachments = uploadpicRepository.findAllById(pm_iteration_id);
		for(Pm_Uploadphoto attachment: attachments) {
			uploadpicRepository.deleteById(attachment.getAttachmentId());
		}
	}


	public List<Pm_Uploadphoto> getallattachmentsbyid(int pm_iteration_id) {
		// TODO Auto-generated method stub
		return uploadpicRepository.findAllById(pm_iteration_id);
	}


	public List<Pm_Uploadphoto> get() {
		// TODO Auto-generated method stub
		return uploadpicRepository.findAll();
	}


	public List<ProjectDto> getallpro() {
		List<Pm_Iteration> pm= this.getall();
		ArrayList<ProjectDto> Dtos = new ArrayList<>();	
		
		for(Pm_Iteration p: pm) {
			ProjectDto pro	= new ProjectDto();
			pro.setId(p.getId());
			pro.setName(p.getName());
			Dtos.add(pro);
		}
		return Dtos;
	}
	
	


	
}
