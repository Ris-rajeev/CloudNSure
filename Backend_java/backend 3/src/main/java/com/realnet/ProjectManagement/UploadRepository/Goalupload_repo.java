package com.realnet.ProjectManagement.UploadRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Documents.Pm_Goal_Upload;

@Repository
public interface Goalupload_repo extends JpaRepository<Pm_Goal_Upload, Integer> {
	
	@Query(value= "SELECT * FROM pm_goal_upload WHERE pm_goal_id=?1", nativeQuery=true)

	List<Pm_Goal_Upload> findAllById(int pm_goal_id);


}
