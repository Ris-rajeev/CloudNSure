package com.realnet.user_Notifications.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realnet.user_Notifications.Entity.AppUserNotifications;
import com.realnet.user_Notifications.Entity.Notification_Dto;
import com.realnet.user_Notifications.Repository.AppUserNotificationsRepository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;

@Service
public class AppUserNotificationServiceImpl implements AppUserNotificationService {
	
	@Autowired
	AppUserNotificationsRepository appUserNotificationsRepository;
	
	@Autowired
	AppUserRepository appUserRepository;
	

	@Override
	public List<AppUserNotifications> getAllnotificationByUserId(Long userId) {
		List<AppUserNotifications> list = appUserNotificationsRepository.findByUserId(userId);
		return list;
	}

	@Override
	public List<AppUserNotifications> getUnseennotificationByUserId(Long userId) {
		List<AppUserNotifications> list = appUserNotificationsRepository.findByUserIdAndSeenFalse(userId);
		return list;
	}

	@Override
	public String postNotificationForallUsers(Notification_Dto notificationdata) {
		List<AppUser> userList = appUserRepository.findAll();
		
		for(AppUser user:userList) {
			AppUserNotifications noti = new AppUserNotifications();
			noti.setNotification(notificationdata.getNotification());
			noti.setTitle(notificationdata.getTitle());
			noti.setSeen(false);
			noti.setUserId(user.getUserId());
			appUserNotificationsRepository.save(noti);
			System.out.println(user.getUserId());
		}
		
		return "success";
	}

	@Override
	public String postNotificationForSingleUsers(AppUserNotifications notificationdata) {
		notificationdata.setSeen(false);
		appUserNotificationsRepository.save(notificationdata);
		return "Added Success";
	}

}
