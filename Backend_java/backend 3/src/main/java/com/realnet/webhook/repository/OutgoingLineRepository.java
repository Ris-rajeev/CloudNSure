package com.realnet.webhook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.realnet.webhook.entity.OutgoingEntity;
import com.realnet.webhook.entity.Outgoing_lines;

public interface OutgoingLineRepository extends JpaRepository<Outgoing_lines, Long>{
	
//	@Query(value = "SELECT * FROM sec_users a where a.outgoing_entity =?1", nativeQuery = true)
//	List<Outgoing_lines> getOutgoinglines(OutgoingEntity out);
	
	
//	List<Outgoing_lines> findByOutgoing_entity(OutgoingEntity outgoing_entity);
	
	
	
	
//	@Query(value = "SELECT * FROM outgoing_lines a where a.outgoing_entity =?1", nativeQuery = true)
//	List<Outgoing_lines> findByOutgoing_entity(long id);
}
