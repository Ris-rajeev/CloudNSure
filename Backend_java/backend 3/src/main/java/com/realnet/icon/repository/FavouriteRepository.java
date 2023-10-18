package com.realnet.icon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.realnet.icon.entity.ArchivedEntity;
import com.realnet.icon.entity.FavouriteEntity;

@Repository
public interface FavouriteRepository  extends JpaRepository<FavouriteEntity,Integer>{
	
     @Query(value="SELECT count(*) from favourite where object_id=?1",nativeQuery = true)
     int countFav(int projectId);
     
     @Query(value="SELECT count(*) from favourite where object_id=?1 AND created_by=?2",nativeQuery = true)
     int IsFav(int projectId, Long userId);
     @Query(value="SELECT * FROM favourite where created_by=?1",nativeQuery=true)
     List<FavouriteEntity> findByCreatedBy(Long created_by); 
     
     @Query(value="SELECT * from favourite where object_id=?1",nativeQuery = true)
     FavouriteEntity findByObjectid(int projectId);
     
     @Query(value="SELECT * from favourite where object_id=?1 AND created_by=?2",nativeQuery = true)
 	Optional<FavouriteEntity> findobject( int objectId, Long userId);
     
     

}
