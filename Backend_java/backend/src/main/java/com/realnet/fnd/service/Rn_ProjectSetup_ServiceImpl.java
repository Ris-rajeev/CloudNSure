package com.realnet.fnd.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.SureConnect.Entities.Sure_Connect;
import com.realnet.SureConnect.Repository.SureRepository;
import com.realnet.Workspaceuser.Entity.Sec_workspace;
import com.realnet.Workspaceuser.Repository.WorkspaceRepository;
import com.realnet.exceptions.ResourceNotFoundException;
import com.realnet.flf.entity.Rn_Bcf_Technology_Stack;
import com.realnet.fnd.entity.DropDownDTO;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.formdrag.entity.Rn_wf_lines_3;
import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
import com.realnet.formio.Services.BoardService;
import com.realnet.formio.entity.Board;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.utils.Constant;
import com.realnet.utils.Port_Constant;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.service.Rn_WireFrame_Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Rn_ProjectSetup_ServiceImpl implements Rn_ProjectSetup_Service {

	Logger log = LoggerFactory.getLogger(Rn_ProjectSetup_ServiceImpl.class);

	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private com.realnet.flf.service.Rn_Bcf_TechnologyStack_Service technologyStackService;

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private WorkspaceRepository workspaceRepository;

	@Autowired
	private BoardService boardService;

	@Autowired
	private Rn_ModuleSetup_Service moduleSetupService;

	@Autowired
	private Rn_WireFrame_Service wireframeService;

	@Autowired
	private Rn_wf_lines_3Repository repo;

	@Autowired
	private SureRepository connectorreRepository;

	@Override
	public List<Rn_Project_Setup> getAll() {
		return projectSetupRepository.findAll();
	}

	@Override
//	@Cacheable(value = "projects")
	public Page<Rn_Project_Setup> getAll(Pageable page) {
		return projectSetupRepository.findAll(page);
	}

	@Override

	public Rn_Project_Setup getById(int id) {
		Rn_Project_Setup bcf_extractor = projectSetupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));
		return bcf_extractor;
	}

//  START FROM SCRATCH PROJECT		
	@Override
	public Rn_Project_Setup save(Rn_Project_Setup project) {
		AppUser loggedInUser = userService.getLoggedInUser();
		long userId = loggedInUser.getUserId();
		long accId = loggedInUser.getAccount().getAccount_id();
		project.setCreatedBy(userId);
		project.setUpdatedBy(userId);

//		Integer technology_stack = Integer.parseInt(project.getTechnologyStack());
//		Rn_Bcf_Technology_Stack technologyStack = technologyStackService.getById(technology_stack);

//		if (!project.getProjectPrefix().isEmpty()) {
//			String projectprefix = project.getProjectPrefix();
//			project.setProjectName(projectprefix + "_" + project.getProjectName());
//
//		}
		project.setAccountId(accId);
		project.setOwned_by(userId);
		project.setOwner(loggedInUser.getFullName());
		project.setWorkflow_id(53);
		Rn_Project_Setup savedProject = projectSetupRepository.save(project);

		Sec_workspace workspace = new Sec_workspace();
		workspace.setAccountId(accId);
		workspace.setName(project.getProjectName());
		workspace.setOwner_id(loggedInUser.getFullName());
		workspace.setProject_id(savedProject.getId());
		workspaceRepository.save(workspace);

		Board board = new Board();
		board.setbName(project.getProjectName());
		board.setType("project");
		board.setProject_id(savedProject.getId());
//		Board newboard = boardRepository.save(board);
		boardService.createBoardClmnsPrj(board);

//		ADD DEFAULT MODULE
//		Rn_Module_Setup moduleReq = new Rn_Module_Setup();
//		moduleReq.setModuleName(savedProject.getProjectName());
//		moduleReq.setCreatedBy(loggedInUser.getUserId());
//		moduleReq.setUpdatedBy(loggedInUser.getUserId());
//		moduleReq.setAccountId(loggedInUser.getUserId());
//
//		moduleReq.setTechnologyStack(savedProject.getTechnologyStack());
//		moduleReq.setProject(savedProject);
//
//		moduleReq.setDbName(savedProject.getDbName());
//		moduleReq.setDbPassword(savedProject.getDbPassword());
//		moduleReq.setDbUserName(savedProject.getDbUserName());
//		moduleReq.setCopy_baseproj(true);
//		moduleReq.setLoginservice(false);
//		Rn_Module_Setup savedModule = moduleSetupService.save(moduleReq);

		// MAKE REPOSITORY IN GITEA

		String job_url = "http://" + Port_Constant.GITEA_IP_ADDRESS + ":" + Port_Constant.GITEA_PORT
				+ "/api/v1/user/repos";

		// ADD DATA IN GITEA
		StringBuilder builder = new StringBuilder();

		builder.append("{\n" + "  \"default_branch\": \"main\",\n" + "  \"description\": \"string\",\n"
				+ "  \"name\": \"" + savedProject.getProjectName() + "\",\n" + "  \"private\": " + ""
				+ savedProject.getAccessibility() + "" + ",\n" + "  \"trust_model\": \"default\"\n" + "}");
		System.out.println(builder.toString());

		Sure_Connect connect = connectorreRepository.findByConnection_name("test_gitea");
		String token = connect.getAccess_token();

		int status_code = 500;

		try {

			RestTemplate restTemplate = new RestTemplate();

			String resourceUrl = job_url;
			String token1 = "Bearer " + token;
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", token1);
			HttpEntity<Object> request = new HttpEntity<Object>(builder.toString(), headers);
			ResponseEntity<String> res = restTemplate.postForEntity(resourceUrl, request, String.class);
//				ResponseEntity<String> res = restTemplate.exchange(resourceUrl, HttpMethod.POST, request, String.class);
			int status = res.getStatusCodeValue();
			if (status >= 200 && status <= 209) {

				log.info("Gitea repo make");
				String resp_body = res.getBody();
				System.out.println(resp_body);

				String giteaurl = geturl(resp_body);

				Rn_Project_Setup oldProject = projectSetupRepository.findById(savedProject.getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								Constant.NOT_FOUND_EXCEPTION + " :" + savedProject.getId()));
				oldProject.setGitea_url(giteaurl);

				projectSetupRepository.save(oldProject);

				status_code = res.getStatusCodeValue();
				String res_body = res.getBody().toString();

				if (status_code <= 209) {
					log.info("Gitea data inserted in job pro");
					System.out.println(res_body);

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		int count = 2;
		int current_state = 1;

		String next_url = "http://" + Port_Constant.FARM_IP_ADDRESS + ":" + Port_Constant.SUREOPS_PORT_9090_AWS
				+ "/sureops/createproject/" + savedProject.getId() + "/" + count + "/" + current_state + "/";

		System.out.println("next url : " + next_url);
		String Save_work_url = "http://" + Port_Constant.FARM_IP_ADDRESS + ":" + Port_Constant.SUREOPS_PORT_9090_AWS
				+ "/sureops/savework?id=" + savedProject.getId() + "&count=" + count + "&job_url=" + next_url
				+ "&job_type=" + "createproject";
		GET(Save_work_url);

		return savedProject;
	}

	// COPY FROM ANOTHER PUBLIC PROJECT
	@Override
	public Rn_Project_Setup gitcopy(Integer copy_from, String newprojectj_name, Long Deployment_profile,
			String commit_msg, String repo_cond) throws JsonProcessingException {
		AppUser loggedInUser = userService.getLoggedInUser();
		long userId = loggedInUser.getUserId();
		long accId = loggedInUser.getAccount().getAccount_id();

		Rn_Project_Setup project = new Rn_Project_Setup();

		Rn_Project_Setup copyfromprj = projectSetupRepository.findById(copy_from).orElseThrow(null);
		String copyprojectName = copyfromprj.getProjectName();

		project.setCreatedBy(userId);
		project.setUpdatedBy(userId);
		project.setAccountId(accId);
		project.setOwned_by(userId);
		project.setOwner(loggedInUser.getFullName());
		project.setWorkflow_id(53);

		project.setArchived(copyfromprj.getArchived());
		project.setCategory(copyfromprj.getCategory());
		project.setCopyTo(copyfromprj.getCopyTo());
		project.setDbName(copyfromprj.getDbName());
		project.setDbPassword(copyfromprj.getDbPassword());
		project.setDbUserName(copyfromprj.getDbUserName());
		project.setDescription(copyfromprj.getDescription());
		project.setFavCnt(copyfromprj.getFavCnt());

		project.setCopyTo(copyfromprj.getCopyTo());
		project.setTechnologyStack(copyfromprj.getTechnologyStack());
//		Integer technology_stack = Integer.parseInt(project.getTechnologyStack());
		// Rn_Bcf_Technology_Stack technologyStack =
		// technologyStackService.getById(technology_stack);
		// project.setTechStackId(copyfromprj.getTechStackId());
		project.setProjectPrefix(copyfromprj.getProjectPrefix());

		project.setPortNumber(copyfromprj.getPortNumber());
		project.setNamespace(copyfromprj.getNamespace());
		project.setAccessibility(copyfromprj.getAccessibility());
		project.setTags(copyfromprj.getTags());

		project.setProjectName(newprojectj_name);

		project.setGitea_url(copyfromprj.getGitea_url());

		Rn_Project_Setup savedProject = projectSetupRepository.save(project);
		List<Rn_Module_Setup> modules = copyfromprj.getModules();
		for (Rn_Module_Setup oldModule : modules) {
			Rn_Module_Setup newModule = new Rn_Module_Setup();
			newModule.setCreatedBy(userId);
//			newModule.setAccountId(accId);
			newModule.setModuleName(oldModule.getModuleName());
			newModule.setDescription(oldModule.getDescription());
			newModule.setModulePrefix(oldModule.getModulePrefix());
			newModule.setCopyTo(oldModule.getCopyTo());
			newModule.setTechnologyStack(oldModule.getTechnologyStack());
			newModule.setProject(savedProject); // change

			newModule.setDbName(oldModule.getDbName());
			newModule.setDbPassword(oldModule.getDbPassword());
			newModule.setDbUserName(oldModule.getDbUserName());
			newModule.setMicroservice(oldModule.isMicroservice());
			newModule.setPortaldeployment(oldModule.isPortaldeployment());
			newModule.setReadme(oldModule.isReadme());
			newModule.setParentrepo(oldModule.getParentrepo());

			Rn_Module_Setup savedModule = moduleSetupService.save(newModule);

			// no need if first statement works
			List<Rn_Fb_Header> rn_fb_header = oldModule.getRn_fb_headers();
			for (Rn_Fb_Header oldHeader : rn_fb_header) {
				Rn_Fb_Header newHeader = new Rn_Fb_Header();
				newHeader.setCreatedBy(userId);
//				newHeader.setAccountId(accId);
				newHeader.setTechStack(oldHeader.getTechStack());
				newHeader.setObjectType(oldHeader.getObjectType());
				newHeader.setSubObjectType(oldHeader.getSubObjectType());
				newHeader.setUiName(oldHeader.getUiName());
				newHeader.setFormType(oldHeader.getFormType());
				newHeader.setFormCode(oldHeader.getFormCode());
				newHeader.setTableName(oldHeader.getTableName());
				newHeader.setLineTableName(oldHeader.getLineTableName());
				newHeader.setMultilineTableName(oldHeader.getMultilineTableName());
				newHeader.setJspName(oldHeader.getJspName());
				newHeader.setControllerName(oldHeader.getControllerName());
				newHeader.setServiceName(oldHeader.getServiceName());
				newHeader.setServiceImplName(oldHeader.getServiceImplName());
				newHeader.setDaoName(oldHeader.getDaoName());
				newHeader.setDaoImplName(oldHeader.getDaoImplName());
				newHeader.setBuild(false);
				newHeader.setUpdated(oldHeader.isUpdated());
				newHeader.setMenuName(oldHeader.getMenuName());
				newHeader.setHeaderName(oldHeader.getHeaderName());
				newHeader.setConvertedTableName(oldHeader.getControllerName());
				newHeader.setTesting(true);
				newHeader.setModule(savedModule); // change
				// newHeader.setRn_cff_actionBuilder(oldHeader.getRn_cff_actionBuilder());

				Rn_Fb_Header savedHeader = wireframeService.save(newHeader);

//				List<Rn_Fb_Line> oldLines = oldHeader.getRn_fb_lines();
//				for (Rn_Fb_Line line : oldLines) {
//					Rn_Fb_Line newLine = new Rn_Fb_Line();
//					newLine.setCreatedBy(userId);
////					newLine.setAccountId(accId);
//					newLine.setForm_type(line.getForm_type());
//					newLine.setFieldName(line.getFieldName());
//					newLine.setMapping(line.getMapping());
//					newLine.setDataType(line.getDataType());
//					newLine.setFormCode(line.getFormCode());
//					newLine.setKey1(line.getKey1());
//					newLine.setType_field(line.getType_field());
//					newLine.setMethodName(line.getMethodName());
//					newLine.setSeq(line.getSeq());
//					newLine.setSection_num(line.getSection_num());
//					newLine.setButton_num(line.getButton_num());
//					newLine.setType1(line.getType1());
//					newLine.setType2(line.getType2());
//					newLine.setMandatory(line.isMandatory());
//					newLine.setHidden(line.isHidden());
//					newLine.setReadonly(line.isReadonly());
//					newLine.setDependent(line.isDependent());
//					newLine.setDependent_on(line.getDependent_on());
//					newLine.setDependent_sp(line.getDependent_sp());
//					newLine.setDependent_sp_param(line.getDependent_sp_param());
//					newLine.setValidation_1(line.isValidation_1());
//					newLine.setVal_type(line.getVal_type());
//					newLine.setVal_sp(line.getVal_sp());
//					newLine.setVal_sp_param(line.getVal_sp_param());
//					newLine.setSequence(line.isSequence());
//					newLine.setSeq_name(line.getSeq_name());
//					newLine.setSeq_sp(line.getSeq_sp());
//					newLine.setSeq_sp_param(line.getSeq_sp_param());
//					newLine.setDefault_1(line.isDefault_1());
//					newLine.setDefault_type(line.getDefault_type());
//					newLine.setDefault_value(line.getDefault_value());
//					newLine.setDefault_sp(line.getDefault_sp());
//					newLine.setDefault_sp_param(line.getDefault_sp_param());
//					newLine.setCalculated_field(line.isCalculated_field());
//					newLine.setCal_sp(line.getCal_sp());
//					newLine.setCal_sp_param(line.getCal_sp_param());
//					newLine.setAdd_to_grid(line.getAdd_to_grid());
//					newLine.setSp_name_for_autocomplete(line.getSp_name_for_autocomplete());
//					newLine.setSp_for_autocomplete(line.getSp_for_autocomplete());
//					newLine.setSp_name_for_dropdown(line.getSp_name_for_dropdown());
//					newLine.setSp_for_dropdown(line.getSp_for_dropdown());
//					newLine.setLine_table_name(line.getLine_table_name());
//					newLine.setLine_table_no(line.getLine_table_no());
//					// newLine.setRn_fb_header(line.getRn_fb_header());
//					newLine.setRn_fb_header(savedHeader);
//					wireframeService.saveLine(newLine);
//
//				}
				try {

					Rn_wf_lines_3 wf_lines_3 = repo.findheader(oldHeader.getId()).orElseThrow(null);
					Rn_wf_lines_3 lines_3 = new Rn_wf_lines_3();
					lines_3.setHeader_id(savedHeader.getId());
					lines_3.setModel(wf_lines_3.getModel());
					repo.save(lines_3);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}

		Sec_workspace workspace = new Sec_workspace();
		workspace.setAccountId(accId);
		workspace.setName(newprojectj_name);
		workspace.setOwner_id(loggedInUser.getFullName());
		workspace.setProject_id(savedProject.getId());
		workspaceRepository.save(workspace);

		Board board = new Board();
		board.setbName(newprojectj_name);
		board.setType("project");
		board.setProject_id(savedProject.getId());
//		Board newboard = boardRepository.save(board);
		boardService.createBoardClmnsPrj(board);

		int count = 4;

		// data inserted in workflow

		String job_url1 = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191
				+ "/token/fnd1/callingsureops/Git_copy1/" + savedProject.getId() + "/" + copyprojectName + "/"
				+ Deployment_profile + "/" + commit_msg + "/" + newprojectj_name + "/" + repo_cond + "/" + count + "/";

		System.out.println("next url : " + job_url1);
		String Save_work_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
				+ "/sureops/savework?id=" + savedProject.getId() + "&count=" + count + "&job_url=" + job_url1
				+ "&job_type=" + "copy_prj";
		GET(Save_work_url);

		System.out.println("sureops_url : " + Save_work_url);
		return savedProject;
	}

	@Override
	public boolean gitcopy1(Integer proj_id, String copy_from, String newprojectj_name, Long Deployment_profile,
			String commit_msg, String repo_cond, int count, int workflow_json_id) throws JsonProcessingException {

		Integer current_state = 1;

		String job_url = "http://" + Port_Constant.GITEA_IP_ADDRESS + ":" + Port_Constant.GITEA_PORT
				+ "/api/v1/user/repos";

		// ADD DATA IN GITEA
		StringBuilder builder = new StringBuilder();

		builder.append("{\n" + "  \"default_branch\": \"main\",\n" + "  \"description\": \"string\",\n"
				+ "  \"name\": \"" + newprojectj_name + "\",\n" + "  \"private\": " + repo_cond + ",\n"
				+ "  \"trust_model\": \"default\"\n" + "}");
		System.out.println(builder.toString());

		Sure_Connect connect = connectorreRepository.findByConnection_name("test_gitea");
		String token = connect.getAccess_token();

		int status_code = 500;

		try {

			RestTemplate restTemplate = new RestTemplate();

			String resourceUrl = job_url;
			String token1 = "Bearer " + token;
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", token1);
			HttpEntity<Object> request = new HttpEntity<Object>(builder.toString(), headers);
			ResponseEntity<String> res = restTemplate.postForEntity(resourceUrl, request, String.class);
//			ResponseEntity<String> res = restTemplate.exchange(resourceUrl, HttpMethod.POST, request, String.class);
			int status = res.getStatusCodeValue();
			if (status >= 200 && status <= 209) {
				current_state++;

				log.info("Gitea repo make");
				String resp_body = res.getBody();
				System.out.println(resp_body);

				String giteaurl = geturl(resp_body);

				Rn_Project_Setup oldProject = projectSetupRepository.findById(proj_id).orElseThrow(
						() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + proj_id));
				oldProject.setGitea_url(giteaurl);

				projectSetupRepository.save(oldProject);

				status_code = res.getStatusCodeValue();
				String res_body = res.getBody().toString();

				String next_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191
						+ "/token/fnd1/callingsureops/Git_copy2/" + proj_id + "/" + copy_from + "/" + Deployment_profile
						+ "/" + commit_msg + "/" + newprojectj_name + "/" + repo_cond + "/" + count + "/"
						+ workflow_json_id + "/" + current_state;

				String update_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
						+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count
						+ "&current_state=" + current_state + "&status=" + status_code + "&job_url=" + next_url
						+ "&resp_body=" + res_body;
				GET(update_url);

			} else {
				status_code = res.getStatusCodeValue();
				String res_body = res.getBody().toString();

				System.out.println("error is : " + res_body);
				String next_url = "no url";

				String update_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
						+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count
						+ "&current_state=" + current_state + "&status=" + status_code + "&job_url=" + next_url
						+ "&resp_body=" + res_body;
				GET(update_url);
				return false;
			}

		} catch (RestClientException e) {

			String resp_body = e.toString().replace("{", "").replace("}", "");
			System.out.println(resp_body);

			System.out.println("rest client error");

			String job_url1 = "no url";

			// for update workflow json
			String update_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + job_url1 + "&resp_body=" + resp_body;
			GET(update_url);

			return false;

		}
		return true;
	}

	public boolean gitcopy2(Integer proj_id, String copy_from, String newprojectj_name, Long Deployment_profile,
			String commit_msg, String repo_cond, int count, int workflow_json_id, Integer current_state) {

		String workflow_id = "51";
		String job_url = "http://" + Port_Constant.FARM_IP_ADDRESS + ":" + Port_Constant.SUREOPS_PORT_9090_AWS
				+ "/sureops/script_initilizer/" + proj_id + "/" + "1/" + count + "/" + workflow_json_id + "/"
				+ current_state + "/" + Deployment_profile + "/" + copy_from + "/" + commit_msg + "/" + workflow_id
				+ "?Sure_filepath=/home/ubuntu/data";

		Sure_Connect connect = connectorreRepository.findByConnection_name("test_gitea");
		String token = connect.getAccess_token();

//		String next_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
//			+ "/sureops/script_initilizer/" + proj_id + "/" + "1?copy_from=" + copy_from + "&Deployment_profile="
//			+ Deployment_profile + "&commit_msg=" + commit_msg + "&Sure_filepath=/data";
		int status_code = 500;
		current_state++;

		try {

			RestTemplate restTemplate = new RestTemplate();
			String resourceUrl = job_url;
			String token1 = "Bearer " + token;
			HttpHeaders headers = getHeaders();
			headers.set("Authorization", token1);
			HttpEntity<Object> request = new HttpEntity<Object>(headers);
			ResponseEntity<Object> res = restTemplate.exchange(resourceUrl, HttpMethod.GET, request, Object.class);

			int status = res.getStatusCodeValue();
			if (status >= 200 && status <= 209) {
				log.info("file created");
				System.out.println(res.getBody());

//			status_code = res.getStatusCodeValue();
//			String resp_body = res.toString().replace("{", "").replace("}", "");
//
//			String update_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
//					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
//					+ current_state + "&status=" + status_code + "&job_url=" + next_url + "&resp_body=" + resp_body;
//			GET(update_url);

			}

		} catch (RestClientException e) {

			String resp_body = e.toString().replace("{", "").replace("}", "");
			System.out.println(resp_body);

			System.out.println("rest client error");

			String job_url1 = "no url";

			// for update workflow json
			String update_url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.SUREOPS_PORT_9090
					+ "/sureops/updatework?work_entityid=" + workflow_json_id + "&count=" + count + "&current_state="
					+ current_state + "&status=" + status_code + "&job_url=" + job_url1 + "&resp_body=" + resp_body;
			GET(update_url);
			return false;

		}

		return true;
	}

//	@Override
//	public Rn_Project_Setup save(Rn_Project_Setup project) {
//		AppUser loggedInUser = userService.getLoggedInUser();
//		long userId = loggedInUser.getUserId();
////		long accId = loggedInUser.getSys_account().getId();
//		project.setCreatedBy(userId);
//		project.setUpdatedBy(userId);
//		Rn_Bcf_Technology_Stack technologyStack = technologyStackService.getById(project.getTechStackId());
//		project.setTechnologyStack(technologyStack.getTech_stack());
////		project.setAccountId(accId);
//		Rn_Project_Setup savedProject = projectSetupRepository.save(project);
//		return savedProject;
//	}

	@Override
	public Rn_Project_Setup updateById(int id, Rn_Project_Setup projectRequest) {
		AppUser loggedInUser = userService.getLoggedInUser();
		long userId = loggedInUser.getUserId();

		Rn_Project_Setup oldProject = projectSetupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));
		oldProject.setProjectName(projectRequest.getProjectName());
		oldProject.setDescription(projectRequest.getDescription());

//		oldProject.setModules(projectRequest.getModules());

		oldProject.setCopyTo(projectRequest.getCopyTo());
		oldProject.setTechnologyStack(projectRequest.getTechnologyStack());
		// oldProject.setTechStackId(projectRequest.getTechStackId());
		oldProject.setProjectPrefix(projectRequest.getProjectPrefix());
		oldProject.setDbName(projectRequest.getDbName());
		oldProject.setDbUserName(projectRequest.getDbUserName());
		oldProject.setDbPassword(projectRequest.getDbPassword());
		oldProject.setPortNumber(projectRequest.getPortNumber());
		oldProject.setUpdatedBy(userId);
		oldProject.setNamespace(projectRequest.getNamespace());
		oldProject.setAccessibility(projectRequest.getAccessibility());
		oldProject.setCategory(projectRequest.getCategory());
		oldProject.setTags(projectRequest.getTags());
		final Rn_Project_Setup updatedProject = projectSetupRepository.save(oldProject);
		return updatedProject;
	}

	@Override
	public boolean deleteById(int id) {
		if (!projectSetupRepository.existsById(id)) {
			throw new ResourceNotFoundException(Constant.NOT_EXIST_EXCEPTION);
		}
		Rn_Project_Setup bcf_extractor = projectSetupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION + " :" + id));
		projectSetupRepository.delete(bcf_extractor);
		return true;
	}

	@Override
	public boolean moveUploadedTechnologyToBaseProjectDir(Rn_Project_Setup project) {
		String projectName = project.getProjectName();
		String dbName = project.getDbName();
		String dbPassword = project.getDbPassword();
		String dbUserName = project.getDbUserName();
		String portNumber = project.getPortNumber();
		System.out.println(project.getTechnologyStack());
		Integer technology_stack = Integer.parseInt(project.getTechnologyStack());
		Rn_Bcf_Technology_Stack technologyStack = technologyStackService.getById(technology_stack);
		// int techId = project.getTechStackId();
		// Rn_Bcf_Technology_Stack technologyStack =
		// technologyStackService.getById(techId);
		project.setTechnologyStack(technologyStack.getTech_stack());// String project_name = technologyStack.get
		String base_prj_folder_name = technologyStack.getBase_prj_file_name();
		System.out.println("Base project folder name = " + base_prj_folder_name);

		String techStack = technologyStack.getTech_stack();
		String dynamicPath = projectPath + "/BaseProject/" + techStack + "/" + base_prj_folder_name;
		System.out.println("Dynamic Path by ganesh bute= " + dynamicPath);
//		try {
//			// add base source code
//			File source = new File(dynamicPath);
//			File dest = new File(projectPath + "/Projects/" + projectName); // need to modifyyy the project name
//
//			System.out.println("SOURCE = " + source + "\nDEST = " + dest);
//			FileUtils.copyDirectory(source, dest);
//
//			StringBuilder properties = new StringBuilder();
//
//			properties.append("server.contextPath=/\r\n"
//					+ "server.port=9119\r\n"
//					+ "spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,classpath:/webui/\r\n"
//					+ "\r\n"
//					+ "spring.banner.location=classpath:banner_txt.txt\r\n"
//					+ "\r\n"
//					+ "\r\n"
//					+ "springfox.documentation.swagger.v2.path=/api-docs\r\n"
//					+ "spring.jackson.date-format=yyyy-MM-dd\r\n"
//					+ "\r\n"
//					+ "spring.datasource.url=jdbc:mysql://localhost:"+portNumber+"/"+dbName+"\r\n"
//					+ "spring.datasource.username="+dbUserName+"\r\n"
//					+ "spring.datasource.password="+dbPassword+"\r\n"
//					+ "spring.datasource.driverClassName = com.mysql.cj.jdbc.Driver\r\n"
//					+ "#spring.jpa.show-sql=true\r\n"
//					+ "spring.jpa.hibernate.ddl-auto=update\r\n"
//					+ "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect\r\n"
//					+ "\r\n"
//					+ "\n projectPath=" + projectPath + "/Projects/" + projectName
//					+ "\r\n"
//					+"\n angularProjectPath="+ projectPath + "/Projects/" + projectName+"/webui"
//					+ "\r\n"
//					+ "#***** MAIL SENDER\r\n"
//					+ "spring.mail.host=smtp.gmail.com\r\n"
//					+ "spring.mail.username=ganesh.bute@dekatc.com\r\n"
//					+ "spring.mail.password=real@123");
//			
////			properties.append("\njdbc.driverClassName = com.mysql.jdbc.Driver" + "\njdbc.url = jdbc:mysql://@localhost:"
////					+ portNumber + "/" + dbName + "\njdbc.username = " + dbUserName + "\njdbc.password = " + dbPassword
////					+ "\nhibernate.dialect = org.hibernate.dialect.MySQLDialect" + "\nhibernate.show_sql = true"
////					+ "\nhibernate.format_sql = true6" + "\nhibernate.use_sql_comments = true"
////					+ "\nhibernate.hbm2ddl.auto=update" + "\nhibernate.enable_lazy_load_no_trans=true"
////					+ "\nprojectPath=" + projectPath + "/Projects/" + projectName); // Dest
//
//			File file0 = new File(
//					projectPath + "/Projects/" + projectName + "/src/main/resources/application.properties");
//			File parentDir = new File(file0.getParent());
//			parentDir.mkdirs();
//			if (!file0.exists()) {
//				file0.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file0.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(properties.toString());
//			bw.close();
//		} catch (FileNotFoundException e) {
//			log.error("File not found exception Handled", e.getMessage());
//			return false;
//		} catch (IOException e) {
//			log.error("IO Exception exception Handled", e.getMessage());
//			return false;
//		}
		log.info("successfully executed...");
		return true;
	}

	@Override
	public List<DropDownDTO> getprojectsForDropDown() {
		// List<Rn_Project_Setup> projectList =
		// projectSetupRepository.findProjectsForDropDown();
		List<Rn_Project_Setup> projectList = this.getAll();

		ArrayList<DropDownDTO> dtos = new ArrayList<DropDownDTO>();
		for (Rn_Project_Setup project : projectList) {
			DropDownDTO dto = new DropDownDTO();
			dto.setId(project.getId());
			dto.setName(project.getProjectName());
			dtos.add(dto);
		}
		return dtos;
	}

//	@Override
//	public List<Rn_Project_Setup> copyProject(String from_tech_stack, String from_object_type,
//			String from_sub_object_type) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Rn_Project_Setup> getAllByUserId(Long created_by) {
		// TODO Auto-generated method stub
		List<Rn_Project_Setup> bcf_extractor = projectSetupRepository.findByCreatedBy(created_by);
		// .orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION
		// + " :" + created_by));
		return bcf_extractor;
	}

	@Override
	public List<Rn_Project_Setup> getAllRecentByCreatedBy(Long created_by) {
		// TODO Auto-generated method stub
		List<Rn_Project_Setup> bcf_extractor = projectSetupRepository.findRecentByCreatedBy(created_by);
		// .orElseThrow(() -> new ResourceNotFoundException(Constant.NOT_FOUND_EXCEPTION
		// + " :" + created_by));
		return bcf_extractor;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

	public ResponseEntity<Object> POST(String joburl, Object user, String token) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = joburl;
		String token1 = "Bearer " + token;
		HttpHeaders headers = getHeaders();
		headers.set("Authorization", token1);
		HttpEntity<Object> request = new HttpEntity<Object>(user, headers);
		ResponseEntity<Object> res = restTemplate.postForEntity(resourceUrl, request, Object.class);

		return res;

	}

	// CONNECTOR CALL
	public String callconnector(String name) throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.BACKEND_PORT_9191
				+ "/token/Sure_Connectbyname/" + name;

//			String token1 = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTWVNBRE1JTiIsInNjb3BlcyI6IlJPTEVfU1lTQURNSU4iLCJpYXQiOjE2NjUwNTQzNDIsImV4cCI6MTY2NTA3MjM0Mn0.1gfJUBCDaYqdgW_JKMjzsTxKqUWNCnKi_O1NguJryDE";
//			HttpHeaders headers = getHeaders();
//			headers.set("Authorization", token1);
//			HttpEntity<Object> request = new HttpEntity<Object>(headers);
//			ResponseEntity<Object> u = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);
//			
		ResponseEntity<Object> u = restTemplate.getForEntity(url, Object.class);
		Object object = u.getBody();
		System.out.println(object);

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(object);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(str);

		JsonObject obj = element.getAsJsonObject();
		JsonElement token = obj.get("access_token");
		System.out.println(token);
		return token.getAsString();
	}

	// GET URL FROM RESPONSE BODY
	public String geturl(String object) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(object);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));// print
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(object);

		JsonObject obj = element.getAsJsonObject();
		JsonElement url = obj.get("clone_url");
		System.out.println(url);

//				JsonObject ownerobject = ownerjson.getAsJsonObject();
//				System.out.println(ownerobject);
//				JsonElement url = ownerobject.get("avatar_url");
//				System.out.println(url.getAsString());

		return url.getAsString();
	}

	// update for project
	public ResponseEntity<Object> updateproject(String url, Object user) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = url;

		HttpHeaders headers = getHeaders();

		HttpEntity<Object> request = new HttpEntity<Object>(user, headers);
//				ResponseEntity<Object> res = restTemplate.put(resourceUrl, request, Object.class);
		ResponseEntity<Object> res = restTemplate.exchange(resourceUrl, HttpMethod.PUT, request, Object.class);

		return res;

	}

	public ResponseEntity<Object> GET(String get) {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> u = restTemplate.getForEntity(get, Object.class);

		return u;

	}
}
