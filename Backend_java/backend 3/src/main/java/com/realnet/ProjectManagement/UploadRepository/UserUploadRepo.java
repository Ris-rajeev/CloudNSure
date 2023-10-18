package com.realnet.ProjectManagement.UploadRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Documents.Pm_User_Upload;

@Repository
public interface UserUploadRepo extends JpaRepository<Pm_User_Upload, Integer> {
	
	@Query(value= "SELECT * FROM pm_user_upload WHERE pm_user_id=?1", nativeQuery=true)
	List<Pm_User_Upload> findAllById(int pm_user_id);

}
