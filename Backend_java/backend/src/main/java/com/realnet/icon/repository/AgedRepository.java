package com.realnet.icon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.realnet.icon.entity.AgedEntity;

@Repository
public interface AgedRepository extends JpaRepository<AgedEntity, Integer> {

	@Query(value="SELECT count(*) from aged where object_id=?1 AND created_by=?2",nativeQuery = true)
    int IsAged(int projectId, Long userId);

	@Query(value="SELECT * from aged where object_id=?1 AND created_by=?2",nativeQuery = true)
	Optional<AgedEntity> findobject( int objectId, Long userId);
	
//	@Query(value="SELECT * from aged where object_id=?1 AND created_by=?2",nativeQuery = true)
//	Optional<AgedEntity> findObject( int objectId, Long userId);
}
