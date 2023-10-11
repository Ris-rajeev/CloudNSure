package com.realnet.icon.controller;

import java.io.IOException;
import java.util.List;

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
import com.realnet.icon.entity.AgedEntity;
import com.realnet.icon.entity.ArchivedEntity;
import com.realnet.icon.repository.ArchivedRepository;
import com.realnet.icon.service.ArchivedService;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(tags = { "Archived" })
public class ArchivedController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private ArchivedService archService;

	@Autowired
	private ArchivedRepository archivedRepository;

	@Autowired
	private Rn_ProjectSetup_Service projectSetupService;

	
	// SAVE
	@ApiOperation(value = "Add New Archived")
	@PostMapping(value = "/addArchiveById", consumes = { "application/json" })
	public ResponseEntity<?> saveStar(@RequestBody ArchivedEntity arch, HttpSession session1) throws IOException {
		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<ArchivedEntity> age = archivedRepository.findobject(arch.getObjectId(), loggedInUser.getUserId());

		if (age.isPresent()) {
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.MODULE_SETUP_API_TITLE);
			error.setMessage(Constant.MODULE_NOT_CREATED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		} else {

			arch.setCreatedBy(loggedInUser.getUserId());
			arch.setUpdatedBy(loggedInUser.getUserId());
			arch.setAccountId(loggedInUser.getAccount().getAccount_id());
			Rn_Project_Setup project = projectSetupService.getById(arch.getObjectId());
			// Rn_Module_Setup module = moduleSetupService.getById(favourite.getObjectId());
			// favourite.setObjectId(project.getId());
			arch.setProject(project);
			// favourite.setModule(module);

			// starred star =
			ArchivedEntity savedArch = archService.save(arch);
			System.out.println("save archived id" + savedArch);

			if (savedArch == null) {
				ErrorPojo errorPojo = new ErrorPojo();
				Error error = new Error();
				error.setTitle(Constant.MODULE_SETUP_API_TITLE);
				error.setMessage(Constant.MODULE_NOT_CREATED);
				errorPojo.setError(error);
				return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
			}
			session1.setAttribute("agedId", savedArch.getId());
			SuccessPojo successPojo = new SuccessPojo();
			Success success = new Success();
			success.setTitle(Constant.MODULE_SETUP_API_TITLE);
			success.setMessage(Constant.MODULE_CREATED_SUCCESSFULLY);
			successPojo.setSuccess(success);
			return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.CREATED);
		}
	}

	// DELETE
	@ApiOperation(value = "Delete A Archived", response = ArchivedEntity.class)
	@DeleteMapping("/removeArchiveById/{object_id}")
	public void deleteArchive(@PathVariable(value = "object_id") int object_id) {
		AppUser loggedInUser = userService.getLoggedInUser();
		Optional<ArchivedEntity> age = archivedRepository.findobject(object_id, loggedInUser.getUserId());
		if (age.isPresent()) {

			archivedRepository.delete(age.get());
		}

	}
	
	// GET BY ID
		@ApiOperation(value = "Get A Archived", response = ArchivedEntity.class)
		@GetMapping("/getArchivedById/{id}")
		public ResponseEntity<?> getArchivedDetails(@PathVariable(value = "id") int id) {
			ArchivedEntity arch = archService.getById(id);
			return new ResponseEntity<ArchivedEntity>(arch, HttpStatus.OK);
		}


	// GET BY userId
	@ApiOperation(value = "Get all archived by userId", response = ArchivedEntity.class)
	@GetMapping("/getArchivedPrjs/{id}")
	public ResponseEntity<?> getMyFavouritesAll(@PathVariable(value = "id") Long created_by) {

		List<ArchivedEntity> arch = archService.getAllByUserId(created_by);
		return new ResponseEntity<List<ArchivedEntity>>(arch, HttpStatus.OK);
	}
}
