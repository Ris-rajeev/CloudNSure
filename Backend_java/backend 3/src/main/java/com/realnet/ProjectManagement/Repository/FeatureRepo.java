package com.realnet.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.realnet.ProjectManagement.Entity.Pm_Feature;
import com.realnet.ProjectManagement.Entity.Projectmix;

public interface FeatureRepo extends JpaRepository<Pm_Feature, Integer> {
	
	Pm_Feature  findById(int id);
	
	@Query(value= "select id,name from pm_feature",nativeQuery =true)
		public List<Projectmix> getfeature();
	

}
