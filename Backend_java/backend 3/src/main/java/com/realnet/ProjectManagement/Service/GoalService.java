package com.realnet.ProjectManagement.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.ProjectManagement.Documents.Pm_Goal_Upload;
import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;
import com.realnet.ProjectManagement.Entity.Pm_Goal;
import com.realnet.ProjectManagement.Entity.ProjectDto;
import com.realnet.ProjectManagement.Repository.GoalRepository;
import com.realnet.ProjectManagement.UploadRepository.Goalupload_repo;
@Service
public class GoalService {
	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private Goalupload_repo goalupload_repo;
	

	public Pm_Goal create(Pm_Goal rnrule) {
		return goalRepository.save(rnrule);
	}

	
	public List<Pm_Goal> getall() {
		return (List<Pm_Goal>) goalRepository.findAll();
	}

	
	public Pm_Goal getbyid(int id) {
		return goalRepository.findById(id);
	}


	public Pm_Goal update(Pm_Goal project, int id) {
		Pm_Goal pm = goalRepository.findById(id);
		
		pm.setAffects_version(project.getAffects_version());
		pm.setAssignee(project.getAssignee());
		pm.setBased_on_version(project.getBased_on_version());
		pm.setIteration(project.getIteration());
		pm.setDescription(project.getDescription());
		pm.setEnd_time(project.getEnd_time());
		pm.setMilestone(project.getMilestone());
		pm.setName(project.getName());
		pm.setOwner(project.getOwner());
		pm.setPriority(project.getPriority());
		pm.setProject(project.getProject());
		pm.setReport_to(project.getReport_to());
		pm.setRepository(project.getRepository());
		pm.setRequestor(project.getRequestor());
		pm.setStart_time(project.getStart_time());
		pm.setStatus(project.getStatus());
		pm.setTags(project.getTags());
		pm.setTeam(project.getTeam());
		pm.setTime_estimates_in_hrs(project.getTime_estimates_in_hrs());
				
		return goalRepository.save(pm);
	}


	public void deletebyid(int id) {
		goalRepository.deleteById(id);
	}


	public List<Pm_Goal_Upload> addallattachments(List<Pm_Goal_Upload> attachments) {
		for(Pm_Goal_Upload at : attachments) {
			goalupload_repo.save(at);
		}
          return attachments;		
	}


	public void deleteAttachment(int pm_goal_id) {
		List<Pm_Goal_Upload> attachments = goalupload_repo.findAllById(pm_goal_id);
		for(Pm_Goal_Upload attachment: attachments) {
			goalupload_repo.deleteById(attachment.getAttachmentId());
		}
	}


	public List<Pm_Goal_Upload> getallattachmentsbyid(int pm_goal_id) {
		return goalupload_repo.findAllById(pm_goal_id);
	}


	public List<ProjectDto> getall1() {
		
		List<Pm_Goal> pm =this.getall();
		ArrayList<ProjectDto> a =new ArrayList<ProjectDto>();
		for(Pm_Goal p : pm ) {
			ProjectDto pro= new ProjectDto();
			pro.setId(p.getId());
			pro.setName(p.getName());
			a.add(pro);	
		}
		return a;
	}



	


}
