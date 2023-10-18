package com.realnet.Workspaceuser.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realnet.Workspaceuser.Entity.Sec_workspace;

@Repository
public interface WorkspaceRepository extends CrudRepository<Sec_workspace, Integer> {

	Sec_workspace findById(int id);

	List<Sec_workspace> findByAccountId(Long accountId);
}
