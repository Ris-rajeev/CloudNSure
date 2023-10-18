package com.realnet.AudiTrail.Repos;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realnet.AudiTrail.Entity.AudiTrail_t;

@Repository
public interface AudiTrail_Repo extends JpaRepository<AudiTrail_t, Long>{
	
	@Query(value = "select COLUMN_NAME\n"
			+ "from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME=?1",nativeQuery = true)
	List<String> findbytable_name(String tablename);

	@Query(value = "SELECT * FROM realnet_CNSBE.audi_trail_t where user=?1",nativeQuery = true)
	List<AudiTrail_t> findbyuser(String username);

	@Query(value = "SELECT * FROM realnet_CNSBE.audi_trail_t where entity_name=?1",nativeQuery = true)
	List<AudiTrail_t> findbyentity(String entity_name);
	
	@Query(value = "SELECT * FROM realnet_CNSBE.audi_trail_t c where c.created_at between :startDate and :endDate", nativeQuery = true)
	public List<AudiTrail_t> findByDate_createdBetween(Date startDate, Date endDate);

	@Query(value = "SELECT * FROM realnet_CNSBE.audi_trail_t c where c.user =:user and c.entity_name=:entity_name and c.created_at between :startDate and :endDate", nativeQuery = true)
	public List<AudiTrail_t> findByDate_createdBetweenbyuser(@Param("user") String username,@Param("entity_name")String entity_name,Date startDate, Date endDate);
}
