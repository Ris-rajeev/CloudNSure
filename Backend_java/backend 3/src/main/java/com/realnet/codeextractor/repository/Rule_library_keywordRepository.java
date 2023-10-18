package com.realnet.codeextractor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.codeextractor.entity.Rule_library_keyword;

@Repository
public interface Rule_library_keywordRepository extends JpaRepository<Rule_library_keyword, Integer> {

	Rule_library_keyword findByKeyword(String keyword);

	@Query(value = "SELECT * FROM realnet_CNSBE.rule_library_keyword where tech_stack = ?1 && service =?2 && "
			+ "object_type =?3 && sub_object_type =?4 ORDER BY priority DESC", nativeQuery = true)
	List<Rule_library_keyword> findByTech_stack(String tech_stack, String service, String object_type,
			String sub_object_type);
}
