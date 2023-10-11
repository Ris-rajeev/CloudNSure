package com.realnet.Deployment_Profile.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.Deployment_Profile.Entity.Deplomentprofile;

@Repository
public interface  deplomentprofileRepository  extends  JpaRepository<Deplomentprofile, Long>  {

	
}