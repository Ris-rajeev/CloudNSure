package com.realnet.fnd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity.ProjectMini;
import com.realnet.fnd.entity.Rn_Project_Setup;

@Repository
public interface Rn_ProjectSetup_Repository extends JpaRepository<Rn_Project_Setup, Integer> {
	// for pagination
	Page<Rn_Project_Setup> findAll(Pageable p);

//	@Query(value = "SELECT ID, PROJECT_NAME FROM RN_PROJECT_SETUP", nativeQuery = true)
//	List<Rn_Project_Setup> findProjectsForDropDown();
	@Query(value = "SELECT * FROM rn_project_setup where created_by=?1", nativeQuery = true)
	List<Rn_Project_Setup> findByCreatedBy(Long created_by);

	@Query(value = "SELECT * FROM rn_project_setup where created_by=?1 AND updated_at > now() - interval 7 day ", nativeQuery = true)
	List<Rn_Project_Setup> findRecentByCreatedBy(Long created_by);

	@Query(value = "SELECT r.id, r.project_name from rn_project_setup r", nativeQuery = true)
	List<ProjectMini> findbyproject();

	@Query(value = "SELECT * FROM rn_project_setup where owned_by=?1 order by id  desc", nativeQuery = true)
	List<Rn_Project_Setup> getmyproject(Long userId);

	@Query(value = "SELECT count(*) FROM rn_project_setup where owned_by=?1 ", nativeQuery = true)
	Object countmyproject(Long userId);
	
	@Query(value = "SELECT count(*) FROM rn_project_setup where account_id=?1 ", nativeQuery = true)
	Object countallproject(Long accountid);

	// FOR SEARCH FUNCTIONALITY
	@Query("SELECT r FROM Rn_Project_Setup r WHERE LOWER(r.projectName) LIKE %:searchWord% AND r.accessibility = false")
	List<Rn_Project_Setup> findAccessibleProjectsByNameContaining(@Param("searchWord") String searchWord);
	
	@Query("SELECT r FROM Rn_Project_Setup r WHERE r.accessibility = false")
	List<Rn_Project_Setup> findProjectsByAccessibilityFalse();
	
	List<Rn_Project_Setup> findByAccountId(Long accountId);
}
