package com.realnet.Deployment_Profile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.Deployment_Profile.Entity.Deplomentprofilelines;

@Repository
public interface Deploymentprofile_line_repo extends JpaRepository<Deplomentprofilelines, Long> {
	
	@Query(value = "select * from realnet_CNSBE.deplomentprofilelines where profile_name=?1", nativeQuery = true)
	Deplomentprofilelines findByProfile_name(String profile_name);

}
