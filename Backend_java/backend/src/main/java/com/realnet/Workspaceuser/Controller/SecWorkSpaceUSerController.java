package com.realnet.Workspaceuser.Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.Workspaceuser.Entity.Sec_team_members;
import com.realnet.Workspaceuser.Entity.Sec_workspace_users;
import com.realnet.Workspaceuser.Repository.SecWorkspaceUserRepo;
import com.realnet.Workspaceuser.Repository.Sec_team_MemberRepository;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.icon.service.AgedService;
import com.realnet.icon.service.ArchivedService;
import com.realnet.icon.service.FavouriteService;
import com.realnet.icon.service.FuturisticService;
import com.realnet.icon.service.PinnedService;
import com.realnet.icon.service.StarService;
import com.realnet.icon.service.WatchListService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.response.MessageResponse;
import com.realnet.users.service1.AppUserServiceImpl;

@RestController
@RequestMapping("/workspace/secworkspaceuser")
@EnableCaching
public class SecWorkSpaceUSerController {

	@Autowired
	private SecWorkspaceUserRepo secWorkspaceUserRepo;
	@Autowired
	private AppUserServiceImpl userService;
	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private FavouriteService favService;
	@Autowired
	private StarService starService;
	@Autowired
	private WatchListService watchService;
	@Autowired
	private FuturisticService futurService;
	@Autowired
	private PinnedService pinService;
	@Autowired
	private ArchivedService archService;
	@Autowired
	private AgedService ageService;

	@Autowired
	private Sec_team_MemberRepository memberRepository;
	@Autowired
	private AppUserRepository userRepository;

	// SHARED WITH ME
	@GetMapping("/sharedwithme")
	@Cacheable(value = "sharedwithme")
	public ResponseEntity<?> getbyid() {
		ArrayList<Rn_Project_Setup> list = new ArrayList<Rn_Project_Setup>();
		AppUser loggedInUser = userService.getLoggedInUser();
		Long userId = loggedInUser.getUserId();
		List<Sec_workspace_users> users = secWorkspaceUserRepo.getallbyuserid(userId);
		for (Sec_workspace_users user : users) {

			Rn_Project_Setup o = projectSetupRepository.findById(user.getProject_id()).orElseThrow(null);
			// project_Setup.forEach(o->{
			o.setFavCnt(favService.getFavCount(o.getId()));
			o.setIs_fav(favService.IsFav(o.getId(), o.getCreatedBy()));
			o.setStaredCnt(starService.getStarCount(o.getId()));
			o.setIs_stared(starService.IsStar(o.getId(), o.getCreatedBy()));
			o.setWatchlistedCnt(watchService.getWatchlistCount(o.getId()));
			o.setIs_watchlisted(watchService.IsWatchlist(o.getId(), o.getCreatedBy()));
			o.setFuturisticCnt(futurService.getFuturisticCount(o.getId()));
			o.setIs_futuristic(futurService.IsFuturistic(o.getId(), o.getCreatedBy()));
			o.setPinnedCnt(pinService.getPinnedCount(o.getId()));
			o.setIs_pinned(pinService.IsPinned(o.getId(), o.getCreatedBy()));
			o.setIs_archived(archService.IsArchived(o.getId(), o.getCreatedBy()));
			o.setIs_aged(ageService.IsAged(o.getId(), o.getCreatedBy()));
			// });
			list.add(o);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// ADD WHOLE TEAM TO SECWORKSPACE USER BY TEAM_ID
	@PostMapping("/addteam/{project_id}/{team_id}")
	public ResponseEntity<?> addwholeteam(@PathVariable Integer project_id, @PathVariable int team_id,
			@RequestBody Sec_workspace_users users) {
		List<Object> list = new ArrayList<>();
		List<Sec_team_members> members = memberRepository.getallteam(team_id);
		for (Sec_team_members mem : members) {
			Sec_workspace_users secuser = secWorkspaceUserRepo.getallsecworkspcceuser(mem.getMember_id(), project_id);
			if (secuser == null) {
				Sec_workspace_users user = new Sec_workspace_users();
				Optional<AppUser> us = userRepository.findById(mem.getMember_id());
				user.setAccountId(us.get().getAccount().getAccount_id());
				user.setUser_id(mem.getMember_id());
				user.setWorksapce_id(project_id);
				user.setProject_id(project_id);
				user.setUser_name(mem.getMember_name());
//				        Set<Role> roles = new HashSet<>();
//				        String role1 = "ROLE_Developer";
//				        Role userRole = roleRepo.findByName(role1);
//				        roles.add(userRole);
//				        users.setUser_role(roles);
				Sec_workspace_users save = secWorkspaceUserRepo.save(user);
				list.add(save);
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// GET ALL SEC_WORKSPACE_USER
	@GetMapping("/sec_workspace_users")
	public ResponseEntity<?> getallusers() {
		List<Sec_workspace_users> list = secWorkspaceUserRepo.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// GET ALL SEC_WORKSPACE_USER by PROJECT ID
	@GetMapping("/get_by_projectid/{project_id}")
	public ResponseEntity<?> getallusers(@PathVariable Integer project_id) {
		List<Sec_workspace_users> list = secWorkspaceUserRepo.getallproject(project_id);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// ADD SEC WORKSPACE USER
	@PostMapping("/add_workspace/users/{userid}/{project_id}/{access_duration}")
	public ResponseEntity<?> addsecusers(@RequestBody Sec_workspace_users users, @PathVariable Long userid,
			@PathVariable Integer project_id, @PathVariable Integer access_duration) {

		AppUser loggedInUser = userService.getLoggedInUser();
		Long fromuserid = loggedInUser.getUserId();
		// Long account_id = loggedInUser.getAccount().getAccount_id();

		Sec_workspace_users workspace_users = secWorkspaceUserRepo.getallsecworkspcceuser(userid, project_id);
		if (workspace_users == null) {
			Optional<AppUser> us = userRepository.findById(userid);
			users.setAccountId(us.get().getAccount().getAccount_id());
			users.setFromuserId(fromuserid);
			users.setUser_id(userid);
			users.setUser_name(us.get().getFullName());
			users.setWorksapce_id(project_id);
			users.setProject_id(project_id);

			users.setAccess_duration(access_duration);

			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, access_duration);

//			SimpleDateFormat dateFormat = new SimpleDateFormat();
//			String format = dateFormat.format(c.getTime());

			users.setAccess_till_date(c.getTime());

//				Set<Role> roles = new HashSet<>();
//				String role1 = "ROLE_Developer";
//				Role userRole = roleRepo.findByName(role1);
//				roles.add(userRole);
//				users.setUser_role(roles);
			Sec_workspace_users save = secWorkspaceUserRepo.save(users);
			return new ResponseEntity<>(save, HttpStatus.OK);
		} else
			return ResponseEntity.badRequest().body(new MessageResponse("user already added"));

	}

	// TOTAL COUNT OF SHARED WITH ME
	@GetMapping("/count_sharedwithme")
	public ResponseEntity<?> toatalcount() {
		AppUser loggedInUser = userService.getLoggedInUser();
		Long userId = loggedInUser.getUserId();
		Object object = secWorkspaceUserRepo.countSharewithme(userId);
		if (object != null) {
			return new ResponseEntity<>(object, HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("no project"));
		}
	}

	// COUNT OF MY PROJECT
	@GetMapping("/count_myproject")
	public ResponseEntity<?> count_project() {
		AppUser loggedInUser = userService.getLoggedInUser();
		Object object = projectSetupRepository.countmyproject(loggedInUser.getUserId());
		if (object != null) {
			return new ResponseEntity<>(object, HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("no project"));
		}
	}
	@GetMapping("/count_allproject")
	public ResponseEntity<?> count_allproject() {
		 Long accountid = userService.getLoggedInUserAccountId();
		Object object = projectSetupRepository.countallproject(accountid);
		if (object != null) {
			return new ResponseEntity<>(object, HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("no project"));
		}
	}

}
