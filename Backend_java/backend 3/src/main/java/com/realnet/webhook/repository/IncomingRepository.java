package com.realnet.webhook.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.webhook.entity.IncomingEntity;


@Repository
public interface IncomingRepository extends JpaRepository<IncomingEntity, Long>{
	
	
	
	@Query(value = "SELECT * FROM incoming_entity a where a.user_id =?1", nativeQuery = true)
	IncomingEntity findByUserId(Long id);

	
	@Query(value = "SELECT * FROM incoming_entity a where a.user_key = ?1", nativeQuery = true)
	IncomingEntity findUserKey(String user_key);


	@Query(value = "SELECT * FROM incoming_entity a where a.api_key =?1", nativeQuery = true)
	IncomingEntity findApiKey(String api_key);

	@Query(value = "SELECT * FROM incoming_entity a where a.token =?1", nativeQuery = true)
	IncomingEntity findByToken(String token);

	@Query(value = "SELECT * FROM incoming_entity a where a.webhook_name =?1", nativeQuery = true)
	IncomingEntity findByWebhook_Name(String webhook_name);
	

	}

