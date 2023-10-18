package com.realnet.fnd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.fnd.entity.DropDownDTO;
import com.realnet.fnd.entity.Rn_Project_Setup;

public interface Rn_ProjectSetup_Service {
	List<Rn_Project_Setup> getAll();

	Page<Rn_Project_Setup> getAll(Pageable p);

	Rn_Project_Setup getById(int id);

	Rn_Project_Setup save(Rn_Project_Setup project) throws JsonProcessingException;
	Rn_Project_Setup gitcopy(Integer copy_from,String newprojectj_name,
			Long Deployment_profile,String commit_msg,String repo_cond) throws JsonProcessingException;

	boolean gitcopy1(Integer proj_id,String copy_from,String newprojectj_name,
			Long Deployment_profile,String commit_msg,String repo_cond,int count,int workflow_json_id) throws JsonProcessingException;

	Rn_Project_Setup updateById(int id, Rn_Project_Setup project);

	boolean deleteById(int id);

	// copy uploaded technology into base project folder
	boolean moveUploadedTechnologyToBaseProjectDir(Rn_Project_Setup project);
	
	//List<DropDownDTO> copyProject(String from_tech_stack, String from_object_type, String from_sub_object_type);

	List<DropDownDTO> getprojectsForDropDown();
	List<Rn_Project_Setup> getAllByUserId(Long created_by); 
	List<Rn_Project_Setup> getAllRecentByCreatedBy(Long created_by);

}
