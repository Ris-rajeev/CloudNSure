package com.realnet.ProjectManagement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Entity.Pm_Iteration;
import com.realnet.ProjectManagement.Entity.Projectmix;

@Repository
public interface IterationRepository extends JpaRepository<Pm_Iteration, Integer> {
	
	Pm_Iteration  findById(int id);
	
	@Query(value= "select id,name from pm_iteration",nativeQuery =true)
	public List<Projectmix> iteartion();

	

}
