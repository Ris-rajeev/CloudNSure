package com.realnet.webhook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.realnet.users.entity1.AppUser;
import com.realnet.webhook.entity.OutgoingEntity;

public interface OutgoingRepository extends JpaRepository<OutgoingEntity, Long> {

//	
//	@Query(value = "SELECT * FROM sec_users a where a.user_id =?1", nativeQuery = true)
//	List<OutgoingEntity> getallOutgoingList(AppUser id);
	@Query(value = "SELECT * FROM outgoing_entity a where a.user_id =?1", nativeQuery = true)
	List<OutgoingEntity> findByUser_id(Long user_id);

//	@Query(value = "SELECT * FROM realnet_CNSBE.outgoing_entity WHERE on_entity = ?1", nativeQuery = true)
//    List<OutgoingEntity> findByEntityName( String on_entity);

	@Query(value = "SELECT * FROM outgoing_entity WHERE on_entity = :entityName", nativeQuery = true)
	List<OutgoingEntity> findByOnEntity(@Param("entityName") String entityName);

	@Query(value = "SELECT * FROM outgoing_entity WHERE on_entity = :entityName AND on_event = :eventType", nativeQuery = true)
	OutgoingEntity findByOnEntityAndOnEvent(@Param("entityName") String entityName,
			@Param("eventType") String eventType);
	
	@Query(value = "SELECT * FROM outgoing_entity a where a.on_incoming_webhook =?1", nativeQuery = true)
	List<OutgoingEntity> findbyincomingwebhook(Long on_incoming_webhook);

}
