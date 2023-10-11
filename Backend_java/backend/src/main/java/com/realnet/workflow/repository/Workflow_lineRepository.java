package com.realnet.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.workflow.Entites.Workflow_Line;
import com.realnet.workflow.Entites.Workflow_table;

@Repository
public interface Workflow_lineRepository extends JpaRepository<Workflow_Line, Integer> {
	
//	@Query(value = "SELECT * FROM workflow_line WHERE  header_id=?1", nativeQuery = true)
//	static
	Workflow_Line findById(int id);
	

@Query(value = "SELECT * FROM workflow_line WHERE  workflow_table_id=?1", nativeQuery = true)
	Workflow_Line findByWorkflow_table_id(int id);

}
