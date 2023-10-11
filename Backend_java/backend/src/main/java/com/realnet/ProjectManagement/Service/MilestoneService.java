package com.realnet.ProjectManagement.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;
import com.realnet.ProjectManagement.Documents.Pm_milestone_Upload;
import com.realnet.ProjectManagement.Entity.Pm_MilestoneTable;
import com.realnet.ProjectManagement.Repository.MilestoneRepository;
import com.realnet.ProjectManagement.UploadRepository.MilestoneUpload_repository;

@Service
public class MilestoneService {
	
	@Autowired
	private MilestoneRepository milestoneRepository;
	
	@Autowired
	private MilestoneUpload_repository milestoneUpload_repository;
	
	public Pm_MilestoneTable create(Pm_MilestoneTable rnrule) {
		return milestoneRepository.save(rnrule);
	}

	
	public List<Pm_MilestoneTable> getall() {
		return (List<Pm_MilestoneTable>) milestoneRepository.findAll();
	}

	
	public Pm_MilestoneTable getbyid(int id) {
		return milestoneRepository.findById(id);
	}


	public Pm_MilestoneTable update(Pm_MilestoneTable project, int id) {
		Pm_MilestoneTable pm = milestoneRepository.findById(id);
		
		pm.setBilliable(project.getBilliable());
		pm.setIteration(project.getIteration());
		pm.setDescription(project.getDescription());
		pm.setEnd_time(project.getEnd_time());
		pm.setName(project.getName());
		pm.setPriority(project.getPriority());
		pm.setProject(project.getProject());
		pm.setReport_to(project.getReport_to());
		pm.setRepository(project.getRepository());
		pm.setStart_time(project.getStart_time());
		pm.setStatus(project.getStatus());
		pm.setTags(project.getTags());
		pm.setTeam(project.getTeam());
		pm.setTime_estimates_in_hrs(project.getTime_estimates_in_hrs());
				
		return milestoneRepository.save(pm);
	}


	public void deletebyid(int id) {
		milestoneRepository.deleteById(id);
	}


	public List<Pm_milestone_Upload> addAllAtthacments(List<Pm_milestone_Upload> attachments){
		for(Pm_milestone_Upload at: attachments) {
			milestoneUpload_repository.save(at);
		}
		return attachments;
}
	
	public List<Pm_milestone_Upload> getallattachmentsbyid(int pm_milestone_id) {
		// TODO Auto-generated method stub
		return milestoneUpload_repository.findAllById(pm_milestone_id);
	}


	public void deleteAttachment(int pm_milestone_id) {
		// TODO Auto-generated method stub
		List<Pm_milestone_Upload> attachments = milestoneUpload_repository.findAllById(pm_milestone_id);
		for(Pm_milestone_Upload attachment: attachments) {
			milestoneUpload_repository.deleteById(attachment.getAttachmentId());
		}
	}

}
