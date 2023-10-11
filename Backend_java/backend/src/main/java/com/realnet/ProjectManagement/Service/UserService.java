package com.realnet.ProjectManagement.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.ProjectManagement.Documents.Pm_Goal_Upload;
import com.realnet.ProjectManagement.Documents.Pm_User_Upload;
import com.realnet.ProjectManagement.Entity.Pm_Goal;
import com.realnet.ProjectManagement.Entity.Pm_User;
import com.realnet.ProjectManagement.Repository.Pm_UserRepo;
import com.realnet.ProjectManagement.UploadRepository.UserUploadRepo;

@Service
public class UserService {
	
	@Autowired
	private Pm_UserRepo pm_UserRepo;
	
	@Autowired
	private UserUploadRepo userUploadRepo;
	
	
	public Pm_User create(Pm_User rnrule) {
		return pm_UserRepo.save(rnrule);
	}

	
	public List<Pm_User> getall() {
		return (List<Pm_User>) pm_UserRepo.findAll();
	}

	
	public Pm_User getbyid(int id) {
		return pm_UserRepo.findById(id);
	}


	public Pm_User update(Pm_User project, int id) {
		Pm_User pm = pm_UserRepo.findById(id);
		
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
		pm.setGoal(project.getGoal());
				
		return pm_UserRepo.save(pm);
	}


	public void deletebyid(int id) {
		pm_UserRepo.deleteById(id);
	}


	public List<Pm_User_Upload> addallattachments(List<Pm_User_Upload> attachments) {
		for(Pm_User_Upload at : attachments) {
			userUploadRepo.save(at);
		}
          return attachments;		
	}


	public void deleteAttachment(int pm_user_id) {
		List<Pm_User_Upload> attachments = userUploadRepo.findAllById(pm_user_id);
		for(Pm_User_Upload attachment: attachments) {
			userUploadRepo.deleteById(attachment.getAttachmentId());
		}
	}
	
	public List<Pm_User_Upload> getallattachmentsbyid(int pm_user_id) {
		return userUploadRepo.findAllById(pm_user_id);
	}



}
