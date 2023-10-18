package com.realnet.user_Notifications.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.user_Notifications.Entity.AppUserNotifications;
import com.realnet.user_Notifications.Entity.Notification_Dto;
import com.realnet.user_Notifications.Repository.AppUserNotificationsRepository;
import com.realnet.user_Notifications.Service.AppUserNotificationService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@RequestMapping("/user_notifications")
public class AppUserNotificationsController {

	@Autowired
	AppUserNotificationService appUserNotificationService;
	
	@Autowired
	AppUserNotificationsRepository appUserNotificationRepo;

	@Autowired
	private AppUserServiceImpl userService;

	// Post Notifications For all User
	@PostMapping("/PostForAll")
	public ResponseEntity<?> addNewNotificationsForAllUser(@RequestBody Notification_Dto notificationdata) {

		String message = appUserNotificationService.postNotificationForallUsers(notificationdata);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	// Post Notifications Single User
	@PostMapping("/PostForSingle")
	public ResponseEntity<?> addNewNotifications(@RequestBody AppUserNotifications appUserNotifications) {
		String message = appUserNotificationService.postNotificationForSingleUsers(appUserNotifications);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	// get all nofication by userid
	@GetMapping("/getAllOfUser")
	public ResponseEntity<?> getUserNotifications() {
		AppUser loggedInUser = userService.getLoggedInUser();
		Long userId = loggedInUser.getUserId();
		List<AppUserNotifications> notifications = appUserNotificationService.getAllnotificationByUserId(userId);

		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}

	// get all Unseen nofication by userid
	@GetMapping("/get_unseen")
	public ResponseEntity<?> getUnseenUserNotifications() {
		AppUser loggedInUser = userService.getLoggedInUser();
		Long userId = loggedInUser.getUserId();
		List<AppUserNotifications> notifications = appUserNotificationService.getUnseennotificationByUserId(userId);

		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}
	
	
	//Seen Notification Save To table
	@GetMapping("/seen_success/{id}")
	public ResponseEntity<?> successseenbyuser(@PathVariable Integer id){
		
		Optional<AppUserNotifications> notiOpt = appUserNotificationRepo.findById(id);
		
		if(!notiOpt.isPresent()) {
			return new ResponseEntity<>("Invalid id",HttpStatus.BAD_REQUEST);
		}
		
		AppUserNotifications noti = notiOpt.get();
		
		noti.setSeen(true);
		
		appUserNotificationRepo.save(noti);
		
		return new ResponseEntity<>("success",HttpStatus.OK);
		
	}

}
