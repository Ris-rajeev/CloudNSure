package com.realnet.users.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.realnet.config.EmailService;

import com.realnet.users.entity.PasswordResetRequest;
import com.realnet.users.entity.PasswordResetToken;
import com.realnet.users.entity.Sys_Accounts;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.repository1.passwordTokenRepository;
import com.realnet.users.response.MessageResponse;
import com.realnet.users.service1.AppUserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api") // , produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "User Mnagement" })
public class UserController {

	@Autowired
	private AppUserServiceImpl userService;
	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	
	@Autowired
	private passwordTokenRepository passwordTokenRepository;


//	@Autowired
//	private FileStorageService fileStorageService;
	
	private AppUserServiceImpl appUserServiceImpl;
	private EmailService emailService;
	@Autowired
	public UserController(AppUserServiceImpl appUserServiceImpl,
			EmailService emailService) {
		super();
		this.appUserServiceImpl = appUserServiceImpl;
		this.emailService = emailService;
	}


	@Value("${projectPath}")
	private String projectPath;

	// GET profile details (ADMIN, USER BOTH, WHO IS LOGGED IN)
	@ApiOperation(value = "Gets current user information", response = AppUser.class)
	@GetMapping("/user-profile")
	public ResponseEntity<?> getUserProfile() {
		AppUser user = userService.getLoggedInUser();
		return ResponseEntity.ok().body(user);
	}

	// UPDATE (ADMIN, USER BOTH, WHO IS LOGGED IN)
//	@ApiOperation(value = "Update current user information", response = User.class)
//	@PutMapping("/user-profile")
//	public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UserProfileDTO userRequest) {
//		String loggedInUserEmail = userService.getLoggedInUserEmail();
//		AppUser user = userService.updateAppUserDto(userRequest);
//		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
//	}

	// UPLOAD IMAGE
//	@PostMapping("/upload")
//	public ResponseEntity<?> uploadProfilePic(@RequestParam("imageFile") MultipartFile file) throws IOException {
//		AppUser user = userService.getLoggedInUser();
//		// String userId = Long.toString(user.getUserId());
//		String userId = String.valueOf(user.getUserId());
//		System.out.println("USER ID = " + userId);
//		// String uploadPath =
//		// projectPath.concat("/src/main/resources/uploaded-picture/" + userId + "/");
//		String uploadPath = projectPath.concat("/src/main/resources/uploaded-picture/");
//		System.out.println("UPLOAD PATH = " + uploadPath);
//
//		fileStorageService.uploadProfilePicture(file, uploadPath);
//
//		String fileName = file.getOriginalFilename();
//		// String head = fileName.substring(0, fileName.indexOf("."));
//		String ext = fileName.substring(fileName.lastIndexOf("."));
//		String fileNewName = "profile-pic-" + userId + ext;
//
//		System.out.println(
//				"UPLOAD PATH = " + uploadPath + "\nFILE NAME = " + fileNewName + "\nFile Size = " + file.getSize());
//		user.setPhotoName(fileNewName);
//		boolean success = userService.insertOrSaveUser(user);
//		Map<String, Boolean> res = new HashMap<String, Boolean>();
//		if (success) {
//			res.put("success", success);
//			return new ResponseEntity<>(res, HttpStatus.OK);
//		} else {
//			res.put("success", success);
//			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
//		}
//	}

	// GET IMAGE
	@GetMapping("/retrieve-image")
	public ResponseEntity<?> getProfilePic() {
		AppUser user = userService.getLoggedInUser();
		String imageName = user.getPhotoName();
		String imagePath = System.getProperty("java.io.tmpdir")+ System.getProperty("file.separator")+imageName;
		File file = new File(imagePath);
		Map<String, String> res = new HashMap<String, String>();

		String encodeBase64 = null;
		try {
			String ext = FilenameUtils.getExtension(file.getName());
			FileInputStream fis = new FileInputStream(file);
			// byte[] media = IOUtils.toByteArray(fis);
			byte[] bytes = new byte[(int) file.length()];
			fis.read(bytes);
			encodeBase64 = Base64.getEncoder().encodeToString(bytes);
			String data = "data:/image/" + ext + ";base64," + encodeBase64;
			fis.close();
			res.put("image", data);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (IOException e) {
			log.debug("File Not Found Exception Handled: {}", e.getMessage());
			res.put("image", "Not Found");
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
	}

	// ====================USER ACCOUNT DETAILS================
	// ########## NEED MOD ##########
	// GET ADMIN profile details (admin accounts)
	@ApiOperation(value = "Get User Account Details", response = Sys_Accounts.class)
	@GetMapping("/user-account")
	public ResponseEntity<?> getUserAccountDetails() {
		AppUser user = userService.getLoggedInUser();
		Sys_Accounts sys_account = user.getAccount();
		System.out.println("Company Details : " + sys_account);
		return ResponseEntity.ok().body(sys_account);
	}

	// =========== reset password =============
	@ApiOperation(value = "Reset Password", response = PasswordResetRequest.class)
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetReq) {
		AppUser reset = userService.resetPassword(passwordResetReq.getUserId(),
				passwordResetReq.getHash(),passwordResetReq.getNewPassword());
		System.out.println("resetPassword() Controller : RESET ? " + reset);
		Map<String, AppUser> res = new HashMap<String, AppUser>();
		if (reset!=null) {
			res.put("reset", reset);
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		} else {
			res.put("reset", reset);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
//	 new Reset password
//	by Gaurav
	
	@ApiOperation(value = "Reset Password", response = PasswordResetRequest.class)
	@PostMapping("/reset_password")
	public ResponseEntity<?> resetPasswordnew(@Valid @RequestBody PasswordResetRequest passwordResetReq) {
		AppUser reset = userService.resetPasswordnew(passwordResetReq);
		System.out.println("resetPassword() Controller : RESET ? " + reset);
		Map<String, AppUser> res = new HashMap<String, AppUser>();
		if (reset!=null) {
			res.put("reset", reset);
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		} else {
			res.put("reset", reset);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Forgot password", response = PasswordResetRequest.class)
	@PostMapping("/forgot_password")
	public ResponseEntity<?> forgotpassword(@Valid @RequestBody PasswordResetRequest passwordResetReq) {
		AppUser reset = userService.forgotpassword(passwordResetReq);
		System.out.println("resetPassword() Controller : RESET ? " + reset);
		Map<String, AppUser> res = new HashMap<String, AppUser>();
		if (reset!=null) {
			res.put("reset", reset);
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		} else {
			res.put("reset", reset);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	
//	Reset password by email sending
	@PostMapping("/resources/forgotpassword")
	public ResponseEntity<?> resetPassword(HttpServletRequest request, 
	  @RequestParam("email") String email) {
		AppUser user = userService.findUserByEmail(email);
	    if (user == null) {
	        return ResponseEntity.badRequest()
	        		.body(new MessageResponse(email +" not found"));
	    }else {
	    String token = UUID.randomUUID().toString();
	    userService.createPasswordResetTokenForUser(user, token);
	       
	    String em = user.getEmail();	
	    String subject = "Pass reset";
	    String url = "http://localhost:4200/#/forgotresetpassword/" +token;
	   // String url = "http://surecns.ml:30165/#/forgotresetpassword/" + token;
//	    String url = "http://localhost:9191/api" + "/resources/savePassword/" + token;
		emailService.constructEmail(em, subject, url);
		return new ResponseEntity<>("Email sent success",HttpStatus.OK);
	    }
	    
	}
	
	
	

	// get email save link
	@PostMapping("/resources/savePassword/{token}")
	public ResponseEntity<?> savePassword( @PathVariable String token,
			@Valid @RequestBody PasswordResetRequest passwordResetReq) {
		
		PasswordResetToken resetToken = passwordTokenRepository.findByToken(token);
		if (resetToken == null) {
			 return ResponseEntity.badRequest()
		        		.body(new MessageResponse("Token expire"));
		}
		
//		AppUser a = appUserRepository.findById(resetToken.getUser().getUserId()).orElse(null);
		AppUser a = appUserRepository.findByEmail(resetToken.getUser().getEmail());

		if(a!=null) {
				
			if (passwordResetReq.getNewPassword().equals(passwordResetReq.getConfirmPassword())){
			
				a.setUserPassw(bcryptEncoder.encode(passwordResetReq.getNewPassword()));
				a.setPwdChangedCnt(a.getPwdChangedCnt()!=null?(long)1+a.getPwdChangedCnt():(long)1);
				a.setLastPwdChangedDate(new Date());
				a.setChangePassw(passwordResetReq.getNewPassword());
				
				appUserRepository.save(a);
				passwordTokenRepository.delete(resetToken);
				return new ResponseEntity<>(a, HttpStatus.OK);
			}
			return ResponseEntity.badRequest()
					.body(new MessageResponse("password and confirm password not match"));
			}
		return ResponseEntity.badRequest()
				.body(new MessageResponse("user not found"));
	}
	
	
	
	

	@GetMapping("/all-users")
	public List<AppUser> getAll(){
		System.out.println("Request came to API..");
		List<AppUser> usersAll = this.userService.getAllUsers();
		return usersAll;
	}
	
	@GetMapping("/get-one/{userId}")
	public ResponseEntity<?> getOne(@PathVariable("userId") Long userId){
		AppUser byId = this.userService.getById(userId).orElse(null);
		return ResponseEntity.ok(byId);
	}
	// ############=== NEED MODIFICATION ========######

	
}
