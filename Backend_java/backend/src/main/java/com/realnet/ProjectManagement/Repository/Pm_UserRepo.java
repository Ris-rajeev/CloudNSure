package com.realnet.ProjectManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.ProjectManagement.Entity.Pm_User;
import com.realnet.ProjectManagement.Entity.Projectmix;

@Repository
public interface Pm_UserRepo extends JpaRepository<Pm_User, Integer> {

	
	Pm_User  findById(int id);
	
	@Query(value= "select id,name from pm_user",nativeQuery =true)
	public List<Projectmix> userstory();

}
