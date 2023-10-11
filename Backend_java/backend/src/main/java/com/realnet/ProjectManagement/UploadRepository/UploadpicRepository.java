package com.realnet.ProjectManagement.UploadRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;
import com.realnet.ProjectManagement.Entity.Pm_Iteration;

@Repository
public interface UploadpicRepository extends JpaRepository<Pm_Uploadphoto, Integer> {

	
	@Query(value= "SELECT * FROM pm_uploadphoto WHERE pm_iteration_id=?1", nativeQuery=true)
	List<Pm_Uploadphoto> findAllById(int pm_iteration_id);

//	void deleteById(Pm_Iteration pm_Iteration);

}
