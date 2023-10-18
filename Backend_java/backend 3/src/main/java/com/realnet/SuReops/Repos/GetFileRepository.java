package com.realnet.SuReops.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.SuReops.entity.GetFile;

@Repository
public interface GetFileRepository extends JpaRepository<GetFile, Long>{
	
	@Query(value = "select * from get_file  where profile_id=?1 order by id  desc", nativeQuery = true)
	List<GetFile> findTopByOrderByd(Long profile_id);

}
