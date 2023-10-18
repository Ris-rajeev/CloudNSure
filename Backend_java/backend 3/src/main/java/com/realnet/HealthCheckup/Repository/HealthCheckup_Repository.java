package com.realnet.HealthCheckup.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.HealthCheckup.Entity.HealthCheckup_t;

@Repository
public interface HealthCheckup_Repository extends JpaRepository<HealthCheckup_t, Long> {
	
	
	@Query(value =  "SELECT * FROM realnet_CNSBE.health_checkup_t where create_project=true",nativeQuery = true)
	List<HealthCheckup_t> findbycreateproj();
	
	@Query(value =  "SELECT * FROM realnet_CNSBE.health_checkup_t where create_deployment=true",nativeQuery = true)
	List<HealthCheckup_t> findbycreatedeployment();
	
	@Query(value =  "SELECT * FROM realnet_CNSBE.health_checkup_t where build_project=true",nativeQuery = true)
	List<HealthCheckup_t> findbybuildapp();
	
	@Query(value =  "SELECT * FROM realnet_CNSBE.health_checkup_t where deploy_app=true",nativeQuery = true)
	List<HealthCheckup_t> findbydeployapp();
}