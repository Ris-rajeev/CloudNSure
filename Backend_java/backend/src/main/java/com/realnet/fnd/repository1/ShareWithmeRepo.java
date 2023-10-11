package com.realnet.fnd.repository1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity1.Sharewithme;

@Repository
public interface ShareWithmeRepo extends JpaRepository<Sharewithme, Long> {

	@Query(value = "SELECT * FROM realnet_CNSBE.sharewithme a where a.touser_id= ?1", nativeQuery = true)
	List<Sharewithme> getallprjbytouserid(Long touser_id);

	@Query(value = "SELECT * FROM realnet_CNSBE.sharewithme a where a.project_id= ?1 && a.touser_id= ?2", nativeQuery = true)
	Sharewithme getauserbyuseridnadprjid(Integer project_id, Long touser_id);
	
	@Query(value = "SELECT * FROM realnet_CNSBE.sharewithme a where a.fromuser_id= ?1 && a.touser_id= ?2", nativeQuery = true)
	Sharewithme getauserbyuseridnadfromuserid(Long fromuserId, Long touser_id);

}
