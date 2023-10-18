package com.realnet.ProjectManagement.UploadRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;
import com.realnet.ProjectManagement.Documents.Pm_milestone_Upload;

@Repository
public interface MilestoneUpload_repository extends JpaRepository<Pm_milestone_Upload, Integer> {

	
	@Query(value= "SELECT * FROM pm_milestone_upload WHERE pm_milestone_id=?1", nativeQuery=true)

	List<Pm_milestone_Upload> findAllById(int pm_milestone_id);

}
