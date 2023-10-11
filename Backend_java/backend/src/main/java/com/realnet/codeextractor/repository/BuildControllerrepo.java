package com.realnet.codeextractor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Build_controller;

@Repository
public interface BuildControllerrepo extends JpaRepository<Build_controller, Integer> {

	@Query(value = "SELECT * FROM realnet_CNSBE.build_controller where rn_bcf_extractor_id =?1", nativeQuery = true)
	List<Build_controller> findallbybcfid(Integer id);

	@Query(value = "SELECT * FROM realnet_CNSBE.build_controller where tech_stack =?1", nativeQuery = true)
	List<Build_controller> findbytechsstack(String tech_stack);

}
