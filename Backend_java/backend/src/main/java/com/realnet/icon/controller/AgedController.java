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
import com.realnet.fnd.entity.projectCopyDTO;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.fnd.service.Rn_ProjectSetup_Service;
import com.realnet.icon.entity.AgedEntity;
import com.realnet.icon.repository.AgedRepository;
import com.realnet.icon.service.AgedService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(tags = { "Aged" })
public class AgedController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private AgedService agedService;

	@Autowired
	private Rn_ProjectSetup_Service projectSetupService;

	@Autowired
	private AgedRepository agedRepository;

	@Autowired
	private Rn_ProjectSetup_Repository projectrepository;

	// SAVE
	@ApiOperation(value = "Add New Aged")
	@PostMapping(value = "/addAgedById", consumes = { "application/json" })
	public ResponseEntity<?> saveStar(@RequestBody AgedEntity aged, HttpSession session1) throws IOException {

		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<AgedEntity> age = agedRepository.findobject(aged.getObjectId(), loggedInUser.getUserId());

		if (age.isPresent()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.MODULE_SETUP_API_TITLE);
			error.setMessage(Constant.MODULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} else {

			aged.setCreatedBy(loggedInUser.getUserId());
			aged.setUpdatedBy(loggedInUser.getUserId());
			aged.setAccountId(loggedInUser.getAccount().getAccount_id());
			Rn_Project_Setup project = projectSetupService.getById(aged.getObjectId());
			// Rn_Module_Setup module = moduleSetupService.getById(favourite.getObjectId());
			// favourite.setObjectId(project.getId());
			aged.setProject(project);
			// favourite.setModule(module);

			// starred star =
			AgedEntity savedAged = agedService.save(aged);
			System.out.println("save archived id" + savedAged);

			if (savedAged == null) {
				ErrorPojo errorPojo = new ErrorPojo();
				Error error = new Error();
				error.setTitle(Constant.MODULE_SETUP_API_TITLE);
				error.setMessage(Constant.MODULE_NOT_CREATED);
				errorPojo.setError(error);
				return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
			}
			session1.setAttribute("agedId", savedAged.getId());
			SuccessPojo successPojo = new SuccessPojo();
			Success success = new Success();
			success.setTitle(Constant.MODULE_SETUP_API_TITLE);
			success.setMessage(Constant.MODULE_CREATED_SUCCESSFULLY);
			successPojo.setSuccess(success);
			return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);

		}
	}

	// DELETE
	@ApiOperation(value = "Delete A Aged", response = AgedEntity.class)
	@DeleteMapping("/removeAgedById/{object_id}")
	public void deleteAged(@PathVariable(value = "object_id") int object_id) {
		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<AgedEntity> age = agedRepository.findobject(object_id, loggedInUser.getUserId());
		if (age.isPresent()) {

			agedRepository.delete(age.get());
		}
	}

	// GET BY ID
	@ApiOperation(value = "Get A Aged", response = AgedEntity.class)
	@GetMapping("/getAgedById/{id}")
	public ResponseEntity<?> getAgedDetails(@PathVariable(value = "id") int id) {
		AgedEntity aged = agedService.getById(id);
		return new ResponseEntity<AgedEntity>(aged, HttpStatus.OK);
	}

}
