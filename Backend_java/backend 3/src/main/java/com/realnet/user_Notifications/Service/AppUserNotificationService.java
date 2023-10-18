package com.realnet.user_Notifications.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.realnet.user_Notifications.Entity.AppUserNotifications;
import com.realnet.user_Notifications.Entity.Notification_Dto;

@Service
public interface AppUserNotificationService {

	
	 List<AppUserNotifications> getAllnotificationByUserId(Long userId);
	
	 List<AppUserNotifications> getUnseennotificationByUserId(Long userId);
	
	 String postNotificationForallUsers(Notification_Dto notificationdata);
	
	 String postNotificationForSingleUsers(AppUserNotifications notificationdata);
}
