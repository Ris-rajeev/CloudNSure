package com.realnet.Workspaceuser.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.Workspaceuser.Entity.Sec_workspace_users;

@Repository
public interface SecWorkspaceUserRepo extends JpaRepository<Sec_workspace_users, Long> {

	@Query(value = "SELECT * FROM sec_workspace_users WHERE user_id=?1 &&  access_till_date >= NOW();", nativeQuery = true)
	List<Sec_workspace_users> getallbyuserid(Long userId);

	@Query(value = "SELECT * FROM sec_workspace_users WHERE user_id=?1 and project_id=?2", nativeQuery = true)
	Sec_workspace_users getallsecworkspcceuser(Long userid, Integer project_id);

	@Query(value = "SELECT * FROM sec_workspace_users WHERE  project_id=?1", nativeQuery = true)
	List<Sec_workspace_users> getallproject(Integer project_id);

	@Query(value = "SELECT count(*) FROM sec_workspace_users WHERE user_id=?1", nativeQuery = true)
	Object countSharewithme(Long userId);
}
