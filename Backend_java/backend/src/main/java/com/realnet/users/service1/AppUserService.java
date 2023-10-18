package com.realnet.users.service1;

import java.util.List;
import java.util.Optional;


import com.realnet.users.entity1.AppUser;

public interface AppUserService {
	public boolean insertOrSaveUser(AppUser appUser);

	public List<AppUser> getAllUsers();

	// company registration
	List<AppUser> getAll();

	void delete(long id);

	// Optional<User> getByUserNameAndPassword(String username, String password);
	AppUser getByUserNameAndPassword(String username, String userPassw);

	Optional<AppUser> getByUserName(String username);

	AppUser getByEmail(String email);

	Optional<AppUser> getById(Long id);

	boolean existsByEmail(String email);

	String getLoggedInUserEmail();

	Long getLoggedInUserId();
	
	Long getLoggedInUserAccountId();

	AppUser getLoggedInUser();

	AppUser getUserInfoByUserId(Long userId);

	// creating new user (sign up user as ADMIN)
	AppUser userResister(AppUser appUser);

	// --- USERS ADDED BY ADMIN ---
	AppUser createUserByAdmin(AppUser appUser);

	boolean deleteById(Long id);
	

	
	public void sendEmail(String email,Long id,Long checkNo);


	public AppUser userResister(AppUser user, Long id);

	

	
}
