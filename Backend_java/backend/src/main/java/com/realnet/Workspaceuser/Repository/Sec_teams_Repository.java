package com.realnet.Workspaceuser.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Workspaceuser.Entity.Sec_teams;

@Repository
public interface Sec_teams_Repository extends CrudRepository<Sec_teams, Integer> {

	
	Sec_teams	findById(int id);
}
