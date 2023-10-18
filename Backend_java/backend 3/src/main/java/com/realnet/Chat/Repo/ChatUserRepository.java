package com.realnet.Chat.Repo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realnet.fnd.entity1.GrpMenuAccess;
import com.realnet.users.entity1.AppUser;

@Repository
public interface ChatUserRepository extends JpaRepository<AppUser,Long>{
	Optional<AppUser> findByUsername(String username);


	

	
}
