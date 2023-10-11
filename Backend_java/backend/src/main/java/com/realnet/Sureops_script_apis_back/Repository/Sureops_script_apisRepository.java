package com.realnet.Sureops_script_apis_back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.realnet.Sureops_script_apis_back.Entity.Sureops_script_apis;
import com.realnet.workflow.Entites.Workflow_Line;

@Repository
public interface Sureops_script_apisRepository extends JpaRepository<Sureops_script_apis, Long> {
	
}