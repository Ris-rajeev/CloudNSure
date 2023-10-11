package com.realnet.Workspaceuser.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Workspaceuser.Entity.Sec_team_members;

@Repository
public interface Sec_team_MemberRepository extends CrudRepository<Sec_team_members, Integer> {
	Sec_team_members	findById(int id);
	
	@Query(value = "SELECT * FROM sec_team_members a where  a.team_id =?1", nativeQuery = true)
	List<Sec_team_members> getallteam(int team_id);
	@Query(value = "SELECT * FROM sec_team_members a where  a.team_id =?1 and a.member_id=?2", nativeQuery = true)
	Sec_team_members findteammember(int id, Long userId);
	
}
