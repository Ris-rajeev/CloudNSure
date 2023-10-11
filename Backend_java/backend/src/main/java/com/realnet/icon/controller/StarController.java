package com.realnet.icon.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.fnd.service.Rn_ProjectSetup_Service;
import com.realnet.icon.entity.PinnedEntity;
import com.realnet.icon.entity.StarEntity;
import com.realnet.icon.repository.StarRepository;
import com.realnet.icon.service.StarService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(tags = { "Star" })
public class StarController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private StarService starService;

	@Autowired
	private Rn_ProjectSetup_Service projectSetupService;

	@Autowired
	private StarRepository starRepository;

	// SAVE
	@ApiOperation(value = "Add New Star")
	@PostMapping(value = "/addStarById", consumes = { "application/json" })
	public ResponseEntity<?> saveStar(@RequestBody StarEntity starred, HttpSession session1) throws IOException {

		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<StarEntity> age = starRepository.findobject(starred.getObjectId(),
				loggedInUser.getUserId());

		if (age.isPresent()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.MODULE_SETUP_API_TITLE);
			error.setMessage(Constant.MODULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} else
		{
		
		starred.setCreatedBy(loggedInUser.getUserId());
		starred.setUpdatedBy(loggedInUser.getUserId());
		starred.setUserId(loggedInUser.getUserId());
		Rn_Project_Setup project = projectSetupService.getById(starred.getObjectId());
		// Rn_Module_Setup module = moduleSetupService.getById(favourite.getObjectId());
		// favourite.setObjectId(project.getId());
		starred.setProject(project);
		// favourite.setModule(module);

		// starred star =
		StarEntity savedStar = starService.save(starred);
		System.out.println("save star id" + savedStar);

		if (savedStar == null) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.MODULE_SETUP_API_TITLE);
			error.setMessage(Constant.MODULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
		session1.setAttribute("starId", savedStar.getId());
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.MODULE_SETUP_API_TITLE);
		success.setMessage(Constant.MODULE_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
		}
	}

	// DELETE
	@ApiOperation(value = "Delete A Starred", response = StarEntity.class)
	@DeleteMapping("/removeStarById/{object_id}")
	public void deleteStarred(@PathVariable(value = "object_id") int object_id) {
		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<StarEntity> age = starRepository.findobject(object_id, loggedInUser.getUserId());
		if (age.isPresent()) {

			starRepository.delete(age.get());
		}
	}

	// GET BY ID
	@ApiOperation(value = "Get A star", response = StarEntity.class)
	@GetMapping("/getStarById/{id}")
	public ResponseEntity<?> getStaredDetails(@PathVariable(value = "id") int id) {
		StarEntity starred = starService.getById(id);
		return new ResponseEntity<StarEntity>(starred, HttpStatus.OK);
	}

}
