package com.realnet.flf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.flf.entity.Technology_element;

@Repository
public interface Techstack_elementRepo extends JpaRepository<Technology_element, Integer>{

	@Query(value = "SELECT * FROM realnet_CNSBE.technology_element where technology_stack_id =?1", nativeQuery=true)
	List<Technology_element> findtechid(Integer technology_Stack);
}
