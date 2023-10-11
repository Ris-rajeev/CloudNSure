package com.realnet.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Entity.Pm_MilestoneTable;
import com.realnet.ProjectManagement.Entity.Projectmix;

@Repository
public interface MilestoneRepository extends JpaRepository<Pm_MilestoneTable, Integer>{

	Pm_MilestoneTable  findById(int id);
	
	@Query(value= "select id,name from pm_milestone_table",nativeQuery =true)
	public List<Projectmix> milestone();

}
