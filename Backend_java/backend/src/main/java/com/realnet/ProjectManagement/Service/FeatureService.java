package com.realnet.ProjectManagement.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.ProjectManagement.Entity.Pm_Feature;
import com.realnet.ProjectManagement.Repository.FeatureRepo;
import com.realnet.ProjectManagement.UploadRepository.FeatureUploadRepo;

@Service
public class FeatureService {
	
	@Autowired
	private FeatureRepo featureRepo;
	
	@Autowired
	private FeatureUploadRepo featureUploadRepo;
	
	
	public Pm_Feature create(Pm_Feature rnrule) {
		return featureRepo.save(rnrule);
	}

	
	public List<Pm_Feature> getall() {
		return (List<Pm_Feature>) featureRepo.findAll();
	}

	
	public Pm_Feature getbyid(int id) {
		return featureRepo.findById(id);
	}


	public Pm_Feature update(Pm_Feature project, int id) {
		Pm_Feature pm = featureRepo.findById(id);
		
		
		pm.setIteration(project.getIteration());
		pm.setDescription(project.getDescription());
		pm.setMilestone(project.getMilestone());
		pm.setName(project.getName());
		pm.setPriority(project.getPriority());
		pm.setProject(project.getProject());
		pm.setGoal(project.getGoal());
		pm.setIteration(project.getIteration());
		pm.setRepository(project.getRepository());
		pm.setTags(project.getTags());
		pm.setUser_story(project.getUser_story());
		
				
		return featureRepo.save(pm);
	}


	public void deletebyid(int id) {
		featureRepo.deleteById(id);
	}


//	public List<Pm_Feature_ListOfTasks> addallattachments(List<Pm_Feature_ListOfTasks> attachments) {
//		for(Pm_Feature_ListOfTasks at : attachments) {
//			featureUploadRepo.save(at);
//		}
//          return attachments;		
//	}
//
//
//	public void deleteAttachment(int pm_feature_id) {
//		List<Pm_Feature_ListOfTasks> attachments = featureUploadRepo.findAllById(pm_feature_id);
//		for(Pm_Feature_ListOfTasks attachment: attachments) {
//			featureUploadRepo.deleteById(attachment.getId());
//		}
//	}


}
