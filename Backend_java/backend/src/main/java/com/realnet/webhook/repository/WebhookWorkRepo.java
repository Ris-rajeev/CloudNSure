package com.realnet.webhook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.webhook.entity.Webhook_workflow;
@Repository
public interface WebhookWorkRepo extends JpaRepository<Webhook_workflow, Long>{

	@Query(value = "select * from realnet_CNSBE.webhook_workflow where processing_flag = 'N' ",nativeQuery = true )
	List<Webhook_workflow> findbyprocessingflag();
	

}
