package com.realnet.ProjectManagement.UploadRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Documents.Pm_Feature_ListOfTasks;

@Repository
public interface FeatureUploadRepo extends JpaRepository<Pm_Feature_ListOfTasks, Integer> {

	@Query(value= "SELECT * FROM pm_feature_listoftasks WHERE pm_feature_id=?1", nativeQuery=true)
	List<Pm_Feature_ListOfTasks> findAllById(int pm_feature_id);
}
