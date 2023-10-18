package com.realnet.HealthCheckup.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.HealthCheckup.Entity.HealthCheckFiles;

@Repository
public interface HealthCheckup_file_Repository extends JpaRepository<HealthCheckFiles, Long> {

	@Query(value = "SELECT * FROM realnet_CNSBE.health_check_files where healthcheckup_id=?1", nativeQuery = true)
	List<HealthCheckFiles> findbyhealthid(Long health_id);

}