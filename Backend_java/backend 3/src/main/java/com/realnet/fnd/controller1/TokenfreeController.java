package com.realnet.fnd.controller1;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.BackendConfig.Repository.BackendConfig_Repository;
import com.realnet.Dashboard1.Entity.Dashbord1_Line;
import com.realnet.Dashboard1.Entity.Dashbord_Header;
import com.realnet.Dashboard1.Repository.Dashboard_lineRepository;
import com.realnet.Dashboard1.Repository.HeaderRepository;
import com.realnet.Dbconfig.Entity.Dbconfig_t;
import com.realnet.Dbconfig.Repository.Dbconfig_Repository;
import com.realnet.Deployment_Profile.Entity.Deplomentprofile;
import com.realnet.Deployment_Profile.Entity.Deplomentprofilelines;
import com.realnet.Deployment_Profile.Repository.Deploymentprofile_line_repo;
import com.realnet.Deployment_Profile.Services.deplomentprofileService;
import com.realnet.bi.entity.Rn_Dashboard_Header;
import com.realnet.bi.repository.Rn_Dashboard_Header_Repository;
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.fnd.service.Rn_ModuleSetup_Service;
import com.realnet.fnd.service.Rn_ProjectSetup_Service;
import com.realnet.fnd.service.Rn_ProjectSetup_ServiceImpl;
import com.realnet.formdrag.entity.Rn_wf_lines_3;
import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
import com.realnet.listbuilder.Entity.Lb_Header;
import com.realnet.listbuilder.Service.Lb_HeaderService;
import com.realnet.userDTO.User;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Constant;
import com.realnet.webhook.Response.EntityResponse;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.repository.Rn_Fb_Header_Repository;
import com.realnet.wfb.service.Rn_WireFrame_Service;
import com.realnet.workflow.Entites.Workflow_Line;
import com.realnet.workflow.Entites.Workflow_table;
import com.realnet.workflow.repository.W_repository;
import com.realnet.workflow.repository.Workflow_lineRepository;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/token/fnd1/callingsureops")
@RestController
public class TokenfreeController {

	@Autowired
	private deplomentprofileService Service;

	@Autowired
	private Rn_ProjectSetup_Service projectSetupService;

	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private W_repository w_repository;

	@Autowired
	private Workflow_lineRepository workflow_lineRepository;

	@Autowired
	private Deploymentprofile_line_repo lineRepository;

	@Autowired
	private Rn_wf_lines_3Repository wfline_repo;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetupRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private Rn_ProjectSetup_ServiceImpl projectSetup_ServiceImpl;

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private Rn_Fb_Header_Repository fbHeaderRepository;

	@Autowired
	private HeaderRepository header_Repository;

	@Autowired
	private Dashboard_lineRepository dashboard_lineRepository;

	@Autowired
	private Lb_HeaderService headerService;

	@Autowired
	private BackendConfig_Repository backendConfig_Repository;

	@Autowired
	private Dbconfig_Repository dbconfig_Repository;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetup_Repository;

	// GET project ID
	@GetMapping("/getproject/{id}")
	public ResponseEntity<?> getProjectDetails(@PathVariable(value = "id") int id) {
		Rn_Project_Setup bcf_tech_stack = projectSetupService.getById(id);

		return new ResponseEntity<Rn_Project_Setup>(bcf_tech_stack, HttpStatus.OK);

	}

//	get workflowline by id
	@GetMapping("/workflowline/{id}")
	public Workflow_Line getworkflowline(@PathVariable int id) {
		Workflow_Line dash = workflow_lineRepository.findById(id);
		return dash;
	}

//	get workflowline by table_id
	@GetMapping("/workflowlinebytable_id/{id}")
	public Workflow_Line getworkflowlinebytableid(@PathVariable int id) {
		Workflow_Line dash = workflow_lineRepository.findByWorkflow_table_id(id);
		return dash;
	}

//	get workflowtable by id
	@GetMapping("/workflowtable/{workflow_name}")
	public Workflow_table getworkflowtable(@PathVariable String workflow_name) {
		Workflow_table dash = w_repository.findByWorkflow_name(workflow_name);
		return dash;
	}

	@PutMapping("/project/{id}")
	public Rn_Project_Setup updateProject(@PathVariable(value = "id") int id,
			@Valid @RequestBody Rn_Project_Setup project, @RequestParam String git_url) {

		Rn_Project_Setup oldProject = projectSetupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));
		oldProject.setGitea_url(git_url);

		final Rn_Project_Setup updatedProject = projectSetupRepository.save(oldProject);

		return updatedProject;

	}

	@GetMapping("/deplomentprofile_line/{id}")
	public Deplomentprofilelines getdetailsbyname(@PathVariable Long id) {
		Deplomentprofilelines get = lineRepository.findById(id).orElseThrow(null);
		return get;
	}

	@GetMapping("/wfline/{id}")
	public Rn_wf_lines_3 getworkflowline(@PathVariable Integer id) {
		Rn_wf_lines_3 get = wfline_repo.findheader(id).orElseThrow(null);
		return get;
	}

	// GET ALL USER attach from login id

	@GetMapping("/GetAllPortalUser/{id}")
	public ResponseEntity<?> getall(@PathVariable Long id) {
		AppUser u = appUserRepository.findByUserId(id);
		Long account_id = u.getAccount().getAccount_id();

		List<AppUser> li = appUserRepository.getalluser(account_id);
		return new ResponseEntity<>(li, HttpStatus.OK);
	}

	// COPY FROM ANOTHER PUBLIC PROJECT FOR WORKFLOW
	@GetMapping(value = "/Git_copy1/{proj_id}/{copy_from}/{Deployment_profile}/{commit_msg}/{newproject_name}/{repo_cond}"
			+ "/{count}/{workflow_json_id}")
	public ResponseEntity<?> gitcopyproject1(

//					@Valid @RequestBody Rn_Project_Setup projectReq,
			HttpSession session1, @PathVariable Integer proj_id, @PathVariable String copy_from,
			@PathVariable Long Deployment_profile, @PathVariable String commit_msg,
			@PathVariable String newproject_name, @PathVariable String repo_cond, @PathVariable int count,
			@PathVariable int workflow_json_id) throws JsonProcessingException {

		boolean savedProject = projectSetupService.gitcopy1(proj_id, copy_from, newproject_name, Deployment_profile,
				commit_msg, repo_cond, count, workflow_json_id);

		System.out.println("Data by  save project name:" + savedProject);

		if (savedProject) {
			return new ResponseEntity<>(new EntityResponse("git repo completed"), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new EntityResponse("git repo not completed"), HttpStatus.BAD_REQUEST);
		}
	}

	// COPY FROM ANOTHER PUBLIC PROJECT FOR WORKFLOW

	@GetMapping(value = "/Git_copy2/{proj_id}/{copy_from}/{Deployment_profile}/{commit_msg}/{newproject_name}/{repo_cond}"
			+ "/{count}/{workflow_json_id}/{current_state}")
	public ResponseEntity<?> gitcopyproject2(

//					@Valid @RequestBody Rn_Project_Setup projectReq,
			HttpSession session1, @PathVariable Integer proj_id, @PathVariable String copy_from,
			@PathVariable Long Deployment_profile, @PathVariable String commit_msg,
			@PathVariable String newproject_name, @PathVariable String repo_cond, @PathVariable int count,
			@PathVariable int workflow_json_id, @PathVariable int current_state) throws JsonProcessingException {

		boolean savedProject = projectSetup_ServiceImpl.gitcopy2(proj_id, copy_from, newproject_name,
				Deployment_profile, commit_msg, repo_cond, count, workflow_json_id, current_state);

		System.out.println("Data by  save project name:" + savedProject);

		if (savedProject) {
			return new ResponseEntity<>(new EntityResponse("file created"), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new EntityResponse("file not created"), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/searchbykeyword/{name}")
	public ResponseEntity<?> SearchByKeyword(@PathVariable String name) {

		List<AppUser> li = appUserRepository.SearchByKeyword(name);
		return new ResponseEntity<>(li, HttpStatus.OK);
	}

// get deployment line
	@GetMapping("/deplomentprofile/{id}")
	public Long getdetailsbyId1(@PathVariable Long id) {

		Deplomentprofile get = Service.getdetailsbyId(id);
		Long lineid = get.getDeplomentprofilelines().getId();
		return lineid;
	}

//	get workflowline by table_id
	@GetMapping("/lineid_by_tableid/{id}")
	public Integer getworkflowlinebytableid2(@PathVariable int id) {
		Workflow_Line dash = workflow_lineRepository.findByWorkflow_table_id(id);
		Integer lineid = dash.getId();
		return lineid;
	}

	// // UPDATE BUILD STATUS WIREFRAME
	@ApiOperation(value = "Update A Project", response = Rn_Project_Setup.class)
	@GetMapping("/wireframe/{id}")
	public ResponseEntity<?> updateProject(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken,
			@PathVariable(value = "id") int id) {
		Rn_Fb_Header oldHeader = fbHeaderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));

		oldHeader.setBuild(true);

		final Rn_Fb_Header updatedHeader = fbHeaderRepository.save(oldHeader);

		return new ResponseEntity<>(updatedHeader, HttpStatus.OK);
	}

	@PostMapping("/addOneAppUserCustom")
	public ResponseEntity<?> addOneUserCustom(@RequestBody User appUser) {
		System.out.println("user registration");
		System.out.println(appUser);
		AppUser a = userService.addOneUserCustom(appUser);
//		System.out.println(a);
		return new ResponseEntity<>(a, HttpStatus.OK);
	}

	@GetMapping("/dashboard_header/{id}")
	public ResponseEntity<?> getdashheader(@PathVariable Integer id) {
		List<Dashbord_Header> get = header_Repository.findbydashboardmodule(id);

		return new ResponseEntity<>(get, HttpStatus.OK);
	}

	@GetMapping("/dashboard_line/{id}")
	public ResponseEntity<?> getdashline(@PathVariable Integer id) {
		List<Dashbord1_Line> get = dashboard_lineRepository.getByheader(id);

		return new ResponseEntity<>(get, HttpStatus.OK);
	}

	// get list header by moduleid
	@GetMapping("/get_list_header/{module_id}")
	public List<Lb_Header> get_by_module_id(@PathVariable int module_id) {

		List<Lb_Header> module = headerService.get_by_module_id(module_id);
		return module;

	}

	@GetMapping("/updatemodule/{id}")
	public ResponseEntity<?> updateModule(@PathVariable(value = "id") int id) {
		Rn_Module_Setup oldModule = moduleSetupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));

		oldModule.setCopy_baseproj(true);

		final Rn_Module_Setup updatedModule = moduleSetupRepository.save(oldModule);

		return new ResponseEntity<>(updatedModule, HttpStatus.OK);
	}

	@GetMapping("/getbymoduleid/{id}")
	public ResponseEntity<?> getbymoduleid(@PathVariable(value = "id") int id) {
		Rn_Module_Setup oldModule = moduleSetupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));

		return new ResponseEntity<>(oldModule, HttpStatus.OK);
	}

//	 GET ALL BACKEND BY MODULE ID
	@GetMapping("/getbackendbymodule/{moduleId}")
	public ResponseEntity<?> getbackendvymodule(@PathVariable int moduleId) {
		Optional<Rn_Module_Setup> module = moduleSetup_Repository.findById(moduleId);
		if (module.isPresent()) {
			Set<BackendConfig_t> backendConfigs = module.get().getBackendConfig_ts();
			return ResponseEntity.ok(backendConfigs);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	get backend config by id
	@GetMapping("/getbackend_config/{id}")
	public ResponseEntity<?> getbackend_config(@PathVariable Long id) {
		BackendConfig_t back = backendConfig_Repository.findById(id).get();

		return new ResponseEntity<>(back, HttpStatus.OK);
	}

//	get database by id
	@GetMapping("/getdataconfig/{id}")
	public ResponseEntity<?> getdataconfigbybackendid(@PathVariable(value = "id") Long id) {
		Dbconfig_t data = dbconfig_Repository.findById(id).get();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

//	GET FB HEADER BY BACKEND ID
	@GetMapping("/getfbheader/{id}")
	public ResponseEntity<?> getfbheaderbybackendid(@PathVariable(value = "id") Long id) {
		List<Rn_Fb_Header> oldModule = fbHeaderRepository.findbybackendid(id);

		return new ResponseEntity<>(oldModule, HttpStatus.OK);
	}
}
