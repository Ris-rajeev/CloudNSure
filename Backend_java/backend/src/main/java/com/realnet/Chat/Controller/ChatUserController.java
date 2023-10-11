package com.realnet.Chat.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Chat.Entity.ChatUserDto;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@RequestMapping(value = "/chat")
public class ChatUserController {
	@Autowired
	private AppUserServiceImpl userService;
	
	@Autowired
	private AppUserRepository appUserRepository;

	
	@PostMapping("/getuser")
	public ResponseEntity<?> addOneUser(@RequestBody ChatUserDto reg) {
		Optional<AppUser> a = appUserRepository.findById(reg.getUserId());
		return new ResponseEntity<>(a.get(), HttpStatus.OK);
	}

	

}
