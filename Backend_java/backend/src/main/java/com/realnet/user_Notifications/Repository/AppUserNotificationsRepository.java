package com.realnet.user_Notifications.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realnet.user_Notifications.Entity.AppUserNotifications;

@Repository
public interface AppUserNotificationsRepository extends JpaRepository<AppUserNotifications, Integer> {
	
	//All Notifications By user id
	List<AppUserNotifications> findByUserId(Long userId);
	
	//Unseen Notifications By user id
	List<AppUserNotifications> findByUserIdAndSeenFalse(Long userId);
}
