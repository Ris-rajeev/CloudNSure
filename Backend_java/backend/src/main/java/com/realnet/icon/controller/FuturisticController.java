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
import com.realnet.icon.entity.ArchivedEntity;
import com.realnet.icon.entity.FuturisticEntity;
import com.realnet.icon.repository.FuturisticRepository;
import com.realnet.icon.service.FuturisticService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(tags = { "Futuristic" })
public class FuturisticController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private FuturisticService futuristicService;

	@Autowired
	private Rn_ProjectSetup_Service projectSetupService;

	@Autowired
	private FuturisticRepository futuristicRepository;

	// SAVE
	@ApiOperation(value = "Add New Star")
	@PostMapping(value = "/addFuturisticById", consumes = { "application/json" })
	public ResponseEntity<?> saveFuturistic(@RequestBody FuturisticEntity futuristic, HttpSession session1)
			throws IOException {
		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<FuturisticEntity> age = futuristicRepository.findobject(futuristic.getObjectId(), loggedInUser.getUserId());

		if (age.isPresent()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.MODULE_SETUP_API_TITLE);
			error.setMessage(Constant.MODULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} 
		else
		{
		futuristic.setCreatedBy(loggedInUser.getUserId());
		futuristic.setUpdatedBy(loggedInUser.getUserId());
		futuristic.setUserId(loggedInUser.getUserId());
		Rn_Project_Setup project = projectSetupService.getById(futuristic.getObjectId());
		// favourite.setObjectId(project.getId());
		futuristic.setProject(project);

		// starred star =
		FuturisticEntity savedFuturistic = futuristicService.save(futuristic);
		System.out.println("save futuristic id" + savedFuturistic);

		if (savedFuturistic == null) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.MODULE_SETUP_API_TITLE);
			error.setMessage(Constant.MODULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}
		session1.setAttribute("futuristicId", savedFuturistic.getId());
		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.MODULE_SETUP_API_TITLE);
		success.setMessage(Constant.MODULE_CREATED_SUCCESSFULLY);
		successPojo.setSuccess(success);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
		}
	}

	// DELETE
	@ApiOperation(value = "Delete A Futuristic", response = FuturisticEntity.class)
	@DeleteMapping("/removeFuturisticById/{object_id}")
	public void deleteStarred(@PathVariable(value = "object_id") int object_id) {
		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<FuturisticEntity> age = futuristicRepository.findobject(object_id, loggedInUser.getUserId());
		if (age.isPresent()) {

			futuristicRepository.delete(age.get());
		}
		}


	// GET BY ID
	@ApiOperation(value = "Get A futuristic", response = FuturisticEntity.class)
	@GetMapping("/getFuturisticById/{id}")
	public ResponseEntity<?> getFuturisticDetails(@PathVariable(value = "id") int id) {
		FuturisticEntity future = futuristicService.getById(id);
		return new ResponseEntity<FuturisticEntity>(future, HttpStatus.OK);
	}

}
