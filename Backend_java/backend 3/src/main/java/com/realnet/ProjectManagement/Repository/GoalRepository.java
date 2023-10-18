package com.realnet.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Entity.Pm_Goal;
import com.realnet.ProjectManagement.Entity.Projectmix;

@Repository
public interface GoalRepository extends JpaRepository<Pm_Goal, Integer> {
	Pm_Goal findById(int id); 
	
	@Query(value= "select id,name from pm_goal",nativeQuery =true)
	public List<Projectmix> getgoal();
	
	@Query(value= "	SELECT * FROM realnet_CNSBE.pm_goal where column_id = ?1",nativeQuery =true)
	List<Pm_Goal> findByColumn_id(Long column_id);

}
