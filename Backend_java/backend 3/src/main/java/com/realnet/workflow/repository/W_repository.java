package com.realnet.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.realnet.workflow.Entites.Workflow_table;

public interface W_repository extends JpaRepository<Workflow_table, Integer> {
	
	Workflow_table findById(int id);
	@Query(value = "SELECT * FROM workflow_table WHERE  workflow_name=?1", nativeQuery = true)
	Workflow_table findByWorkflow_name(String workflow_name);
	
	@Query(value = "SELECT * FROM realnet_CNSBE.workflow_table where callable ='Y'", nativeQuery = true)
	List<Workflow_table> callTable();
	
}
