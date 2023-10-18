package com.realnet.fnd.controller1;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.BackendConfig.Entity.BackendConfig_t;
import com.realnet.BackendConfig.Repository.BackendConfig_Repository;
import com.realnet.Dbconfig.Entity.Dbconfig_t;
import com.realnet.Dbconfig.Repository.Dbconfig_Repository;
import com.realnet.actionbuilder.entity.Rn_cff_ActionBuilder_Header;
import com.realnet.actionbuilder.entity.Rn_cff_ActionBuilder_Lines;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.entity.Rn_Project_Setup;
import com.realnet.fnd.entity1.Sharewithme;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;
import com.realnet.fnd.repository.Rn_ProjectSetup_Repository;
import com.realnet.fnd.repository1.ShareWithmeRepo;
import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
import com.realnet.icon.service.AgedService;
import com.realnet.icon.service.ArchivedService;
import com.realnet.icon.service.FavouriteService;
import com.realnet.icon.service.FuturisticService;
import com.realnet.icon.service.PinnedService;
import com.realnet.icon.service.StarService;
import com.realnet.icon.service.WatchListService;
import com.realnet.rb.entity.Rn_report_builder;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.repository1.AppUserRepository;
import com.realnet.users.service1.AppUserServiceImpl;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.repository.Rn_Fb_Header_Repository;

@RestController
@RequestMapping("/fnd/project")
@EnableCaching
public class ProjectController {
	@Autowired
	private Rn_ProjectSetup_Repository projectSetupRepository;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetup_Repository;

	@Autowired
	private Rn_Fb_Header_Repository fbheaderrepository;

	@Autowired
	private BackendConfig_Repository backendConfig_Repository;

	@Autowired
	private Dbconfig_Repository dbconfig_Repository;

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
	private Rn_wf_lines_3Repository repo;

	@Autowired
	private ShareWithmeRepo shareWithmeRepo;

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private AppUserRepository appUserRepository;

	// get all project in my project
	// @Cacheable(value = "myproject")
	@GetMapping("/myproject")
	public ResponseEntity<?> getmyproject() {
		AppUser loggedInUser = userService.getLoggedInUser();
		List<Rn_Project_Setup> myproject = projectSetupRepository.getmyproject(loggedInUser.getUserId());
		myproject.forEach(o -> {
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
		});
		return new ResponseEntity<>(myproject, HttpStatus.OK);

	}
	
	@GetMapping("/allproject")
	public ResponseEntity<?> getallproject() {
		Long accountid = userService.getLoggedInUserAccountId();
		List<Rn_Project_Setup> myproject = projectSetupRepository.findByAccountId(accountid);
		myproject.forEach(o -> {
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
		});
		return new ResponseEntity<>(myproject, HttpStatus.OK);

	}

	@GetMapping("/count_wfline3")
	public ResponseEntity<?> countEntitywfline() {
		long count = repo.count();
//		Object count = repo.count_wfline();
		return new ResponseEntity<>(count, HttpStatus.OK);

	}

//	share project with another use
	@PostMapping("/sharewithme")
	public ResponseEntity<?> sharewithme(@RequestBody Sharewithme sh) {
		AppUser loggedInUser = userService.getLoggedInUser();

		Long userId = loggedInUser.getUserId();
		sh.setFromuserId(userId);
		sh.setProject_name(projectSetupRepository.findById(sh.getProject_id()).get().getProjectName());
		Sharewithme sharewithme = shareWithmeRepo.getauserbyuseridnadprjid(sh.getProject_id(), sh.getTouserId());
		Sharewithme fromsharewithme = shareWithmeRepo.getauserbyuseridnadfromuserid(userId, sh.getTouserId());

		Sharewithme save;
		if (sharewithme == null && fromsharewithme == null) {
			save = shareWithmeRepo.save(sh);
			return new ResponseEntity<>(save, HttpStatus.OK);

		} else if (sharewithme == null) {
			return new ResponseEntity<>("project already shared", HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<>("dont share yourself", HttpStatus.BAD_REQUEST);

		}

	}

//	get all shared project
	@GetMapping("/sharewithme")
	public ResponseEntity<?> getsharewithme() {
		AppUser loggedInUser = userService.getLoggedInUser();

		ArrayList<Rn_Project_Setup> myproject = new ArrayList<>();
		List<Sharewithme> projects = shareWithmeRepo.getallprjbytouserid(loggedInUser.getUserId());

		for (Sharewithme sh : projects) {
			Integer project_id = sh.getProject_id();
			Rn_Project_Setup rn_Project_Setup = projectSetupRepository.findById(project_id).get();
			myproject.add(rn_Project_Setup);
		}

		myproject.forEach(o -> {
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
		});
		return new ResponseEntity<>(myproject, HttpStatus.OK);

	}

//	get all backend and db config
	@GetMapping("/getallconfig/{moduleid}")
	public ResponseEntity<?> getbackendanddb(@PathVariable Integer moduleid) {

		Map<String, List<String>> map = new HashMap<>();
		List<String> backend = new ArrayList<>();
		List<String> dbconfig = new ArrayList<>();

		Optional<Rn_Module_Setup> module = moduleSetup_Repository.findById(moduleid);
		if (module.isPresent()) {
			Set<BackendConfig_t> backendConfigs = module.get().getBackendConfig_ts();

			backendConfigs.forEach(i -> backend.add(i.getBackend_service_name()));

			for (BackendConfig_t back : backendConfigs) {
				Long db_id = back.getDb_id();
				if (db_id != null) {

					Dbconfig_t dbconfig_t = dbconfig_Repository.findById(db_id).get();
					if (!dbconfig.contains(dbconfig_t.getDb_name())) {
						dbconfig.add(dbconfig_t.getDb_name());
					}
				}

			}

		}

		map.put("backend", backend);
		map.put("dbconfig", dbconfig);

		return new ResponseEntity<>(map, HttpStatus.OK);

	}

//	 SEARCH FEATURE FOR PROJECT, MODULE , COMPONENT AND USERS
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> getAllserchFeature(@PathVariable String keyword) {
		Map<String, List<Object>> resultMap = new HashMap<>();

		// Project Filter
		List<Rn_Project_Setup> projects = projectSetupRepository.findAccessibleProjectsByNameContaining(keyword);
		resultMap.put("projects", new ArrayList<>(projects));

		// Module Filter
		List<Rn_Module_Setup> modules = moduleSetup_Repository.findModulesByNameContainingKeyword(keyword);
		resultMap.put("modules", new ArrayList<>(modules));

		// Header Filter
		List<Rn_Fb_Header> headersrn = fbheaderrepository.findWireframesByHeaderNameContainingKeyword(keyword);
		resultMap.put("headers", new ArrayList<>(headersrn));

		// User Filter
		List<AppUser> filteredusers = appUserRepository.findByFullNameContaining(keyword);
		resultMap.put("users", new ArrayList<>(filteredusers));

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

//
//	COPY MODULE FROM EXISTING MODULE
	@GetMapping("/copy_module/{id}")
	public ResponseEntity<?> getNewModuleBasedOnExistingModule(@PathVariable Integer id) {
		Optional<Rn_Module_Setup> optModel = moduleSetup_Repository.findById(id);
		if (!optModel.isPresent()) {
			return new ResponseEntity<>("Invalid Module id : " + id, HttpStatus.BAD_REQUEST);
		}
		AppUser loggedInUser = userService.getLoggedInUser();

		Rn_Module_Setup existingModel = optModel.get();
		Rn_Module_Setup newModel = new Rn_Module_Setup();
		newModel.setCreatedBy(loggedInUser.getUserId());
		newModel.setCreatedAt(Date.valueOf(LocalDate.now()));
		newModel.setModuleName(existingModel.getModuleName());
		newModel.setDescription(existingModel.getDescription());
		newModel.setModulePrefix(existingModel.getModulePrefix());
		newModel.setTechnologyStack(existingModel.getTechnologyStack());
		newModel.setProjectName(existingModel.getProjectName());
		newModel.setCopyTo(existingModel.getCopyTo());
		newModel.setTags(existingModel.getTags());
		newModel.setReadme(existingModel.isReadme());
		newModel.setDbName(existingModel.getDbName());
		newModel.setDbUserName(existingModel.getDbUserName());
		newModel.setDbPassword(existingModel.getDbPassword());
		newModel.setPortNumber(existingModel.getPortNumber());
		newModel.setMicroservice(existingModel.isMicroservice());
		newModel.setPortaldeployment(existingModel.isPortaldeployment());
		newModel.setParentrepo(existingModel.getParentrepo());
		newModel.setProjectName(existingModel.getProjectName());
		newModel.setProjectId(existingModel.getProjectId());
		newModel.setProject(existingModel.getProject());
		newModel.setCopy_baseproj(existingModel.isCopy_baseproj());
		newModel.setLoginservice(existingModel.isLoginservice());
		newModel.setMicroservice(existingModel.isMicroservice());
		newModel.setTesting(existingModel.isTesting());

//Headers Creation
		List<Rn_Fb_Header> existingHeaders = existingModel.getRn_fb_headers();
		if (existingHeaders != null && existingHeaders.size() != 0) {
			for (Rn_Fb_Header existingHeader : existingHeaders) {
				Rn_Fb_Header newHeader = new Rn_Fb_Header();
newHeader.setCreatedBy(loggedInUser.getUserId());
newHeader.setCreatedAt(Date.valueOf(LocalDate.now()));
				newHeader.setTechStack(existingHeader.getTechStack());
				newHeader.setObjectType(existingHeader.getObjectType());
				newHeader.setSubObjectType(existingHeader.getSubObjectType());
				newHeader.setUiName(existingHeader.getUiName());
				newHeader.setFormType(existingHeader.getFormType());
				newHeader.setTableName(existingHeader.getTableName());
				newHeader.setLineTableName(existingHeader.getLineTableName());
				newHeader.setMultilineTableName(existingHeader.getMultilineTableName());
				newHeader.setFormCode(existingHeader.getFormCode());
				newHeader.setJspName(existingHeader.getJspName());
				newHeader.setControllerName(existingHeader.getControllerName());
				newHeader.setServiceName(existingHeader.getServiceName());
				newHeader.setServiceImplName(existingHeader.getServiceImplName());
				newHeader.setDaoName(existingHeader.getDaoName());
				newHeader.setDaoImplName(existingHeader.getDaoImplName());
				newHeader.setBuild(existingHeader.isBuild());
				newHeader.setUpdated(existingHeader.isUpdated());
				newHeader.setMenuName(existingHeader.getMenuName());
				newHeader.setHeaderName(existingHeader.getHeaderName());
				newHeader.setConvertedTableName(existingHeader.getConvertedTableName());
				newHeader.setBackend_id(existingHeader.getBackend_id());// ---------
				newHeader.setTesting(existingHeader.isTesting());
				newHeader.setChild_form(existingHeader.isChild_form());

//// Headers ------  (Rn Fb Lines)
//				List<Rn_Fb_Line> existingLines = existingHeader.getRn_fb_lines();
//
//				for (Rn_Fb_Line existingLine : existingLines) {
//
//					Rn_Fb_Line newLine = new Rn_Fb_Line();
//
//					newLine.setFieldName(existingLine.getFieldName());
//					newLine.setMapping(existingLine.getMapping());
//					newLine.setDataType(existingLine.getDataType());
//					newLine.setFormCode(existingLine.getFormCode());
//					newLine.setKey1(existingLine.getKey1());
//					newLine.setType1(existingLine.getType1());
//					newLine.setMandatory(existingLine.isMandatory());
//					newLine.setHidden(existingLine.isHidden());
//					newLine.setReadonly(existingLine.isReadonly());
//					newLine.setDependent(existingLine.isDependent());
//					newLine.setDependent_on(existingLine.getDependent_on());
//					newLine.setDependent_sp(existingLine.getDependent_sp());
//					newLine.setDependent_sp_param(existingLine.getDependent_sp_param());
//					newLine.setValidation_1(existingLine.isValidation_1());
//					newLine.setVal_type(existingLine.getVal_type());
//					newLine.setVal_sp(existingLine.getVal_sp());
//					newLine.setVal_sp_param(existingLine.getVal_sp_param());
//					newLine.setSequence(existingLine.isSequence());
//					newLine.setSeq_name(existingLine.getSeq_name());
//					newLine.setSeq_sp(existingLine.getSeq_sp());
//					newLine.setSeq_sp_param(existingLine.getSeq_sp_param());
//					newLine.setDefault_1(existingLine.isDefault_1());
//					newLine.setDefault_type(existingLine.getDefault_type());
//					newLine.setDefault_value(existingLine.getDefault_value());
//					newLine.setDefault_sp(existingLine.getDefault_sp());
//					newLine.setDefault_sp_param(existingLine.getDefault_sp_param());
//					newLine.setCalculated_field(existingLine.isCalculated_field());
//					newLine.setCal_sp(existingLine.getCal_sp());
//					newLine.setCal_sp_param(existingLine.getCal_sp_param());
//					newLine.setAdd_to_grid(existingLine.getAdd_to_grid());
//					newLine.setSp_for_autocomplete(existingLine.getSp_for_autocomplete());
//					newLine.setSp_for_dropdown(existingLine.getSp_for_dropdown());
//					newLine.setSp_name_for_autocomplete(existingLine.getSp_name_for_autocomplete());
//					newLine.setSp_name_for_dropdown(existingLine.getSp_name_for_dropdown());
//					newLine.setType_field(existingLine.getType_field());
//					newLine.setMethodName(existingLine.getMethodName());
//					newLine.setSeq(existingLine.getSeq());
//					newLine.setForm_type(existingLine.getForm_type());
//					newLine.setSection_num(existingLine.getSection_num());
//					newLine.setButton_num(existingLine.getButton_num());
//					newLine.setType2(existingLine.getType2());
//					newLine.setLine_table_name(existingLine.getLine_table_name());
//					newLine.setLine_table_no(existingLine.getLine_table_no());
//					newLine.setAction(existingLine.getAction());
//					newLine.setRequest_param(existingLine.getRequest_param());
//					newLine.setAction_uiname(existingLine.getAction_uiname());
//
//					// Set the relationship (rn_fb_header)
//					if (newHeader.getRn_fb_lines() != null) {
//						newHeader.getRn_fb_lines().add(newLine);
//					} else {
//						newHeader.setRn_fb_lines(new ArrayList<>());
//						newHeader.getRn_fb_lines().add(newLine);
//					}
//					newLine.setRn_fb_header(newHeader);
//
//				}

//Rn_cff_ActionBuilder_Header--------
				List<Rn_cff_ActionBuilder_Header> existingRn_cffff = existingHeader.getRn_cff_actionBuilder();
				if (existingRn_cffff != null) {
					for (Rn_cff_ActionBuilder_Header existingRn_cff : existingRn_cffff) {

						Rn_cff_ActionBuilder_Header newRn_cff_ActionBuilder_Header = new Rn_cff_ActionBuilder_Header();
                        newRn_cff_ActionBuilder_Header.setCreatedBy(loggedInUser.getUserId());
						newRn_cff_ActionBuilder_Header.setTechnologyStack(existingRn_cff.getTechnologyStack());
						newRn_cff_ActionBuilder_Header.setControllerName(existingRn_cff.getControllerName());
						newRn_cff_ActionBuilder_Header.setMethodName(existingRn_cff.getMethodName());
						newRn_cff_ActionBuilder_Header.setActionName(existingRn_cff.getActionName());
						newRn_cff_ActionBuilder_Header.setFileLocation(existingRn_cff.getFileLocation());

						// Action builder Lines
						List<Rn_cff_ActionBuilder_Lines> actionBuilderLinesexit = existingRn_cff
								.getActionBuilderLines();

						for (Rn_cff_ActionBuilder_Lines existLine : actionBuilderLinesexit) {
							Rn_cff_ActionBuilder_Lines newActionBuilderline = new Rn_cff_ActionBuilder_Lines();
newActionBuilderline.setCreatedBy(loggedInUser.getUserId());
newActionBuilderline.setCreatedAt(Date.valueOf(LocalDate.now()));
							newActionBuilderline.setActionType1(existLine.getActionType1());
							newActionBuilderline.setActionType2(existLine.getActionType2());
							newActionBuilderline.setDataType(existLine.getDataType());
							newActionBuilderline.setVariableName(existLine.getVariableName());
							newActionBuilderline.setAssignment(existLine.getAssignment());
							newActionBuilderline.setMessage(existLine.getMessage());
							newActionBuilderline.setConditions(existLine.getConditions());
							newActionBuilderline.setForward(existLine.getForward());
							newActionBuilderline.setEquation(existLine.getEquation());
							newActionBuilderline.setSeq(existLine.getSeq());
							newActionBuilderline.setAction(existLine.getAction());
							newActionBuilderline.setGroupId(existLine.getGroupId());
							if (newRn_cff_ActionBuilder_Header.getActionBuilderLines() != null) {
								newRn_cff_ActionBuilder_Header.getActionBuilderLines().add(newActionBuilderline);
							} else {
								newRn_cff_ActionBuilder_Header.setActionBuilderLines(new ArrayList<>());
								newRn_cff_ActionBuilder_Header.getActionBuilderLines().add(newActionBuilderline);
							}
							newActionBuilderline.setRn_cff_actionBuilderHeader(newRn_cff_ActionBuilder_Header);
						}

						// Set the relationship (rn_fb_header)
						if (newHeader.getRn_cff_actionBuilder() != null) {
							newHeader.getRn_cff_actionBuilder().add(newRn_cff_ActionBuilder_Header);
						} else {
							newHeader.setRn_cff_actionBuilder(new ArrayList<>());
							newHeader.getRn_cff_actionBuilder().add(newRn_cff_ActionBuilder_Header);
						}
						newRn_cff_ActionBuilder_Header.setRn_fb_header(newHeader);
					}
				}

				if (newModel.getRn_fb_headers() != null) {
					newModel.getRn_fb_headers().add(newHeader);
				} else {
					newModel.setRn_fb_headers(new ArrayList<>());
					newModel.getRn_fb_headers().add(newHeader);
				}
				newHeader.setModule(newModel);
			}
		}
//Report Builder
		List<Rn_report_builder> existingBulders = existingModel.getRn_report_builder();
		if (existingBulders != null && existingBulders.size() != 0) {
			for (Rn_report_builder b : existingBulders) {
				Rn_report_builder newBuilder = new Rn_report_builder();
newBuilder.setCreatedBy(loggedInUser.getUserId());
newBuilder.setCreatedAt(Date.valueOf(LocalDate.now()));
				newBuilder.setReport_name(b.getReport_name());
				newBuilder.setDescription(b.getDescription());
				newBuilder.setReport_tags(b.getReport_tags());
				newBuilder.setDate_string(b.getDate_string());
				newBuilder.setAdd_param_string(b.getAdd_param_string());
				newBuilder.setMaster_select(b.getMaster_select());
				newBuilder.setGrid_headers(b.getGrid_headers());
				newBuilder.setStd_param_view(b.getStd_param_view());
				newBuilder.setGrid_values(b.getGrid_values());
				newBuilder.setModel_string(b.getModel_string());
				// newBuilder.setModule_id(b.getModule_id());
				newBuilder.setUiname(b.getUiname());
				newBuilder.setServicename(b.getServicename());
				newBuilder.setReporttype(b.getReporttype());
				newBuilder.setAccount_id(b.getAccount_id());
				newBuilder.setProject_id(b.getProject_id());
				newBuilder.setIs_build(b.getIs_build());
				newBuilder.setIs_updated(b.getIs_updated());

				newBuilder.setModule(newModel);
				if (newModel.getRn_report_builder() != null) {
					newModel.getRn_report_builder().add(newBuilder);
				} else {
					newModel.setRn_report_builder(new ArrayList<>());
					newModel.getRn_report_builder().add(newBuilder);
				}

			}
		}

		Rn_Module_Setup savedModel = moduleSetup_Repository.save(newModel);
		if (savedModel.getRn_report_builder() != null) {
			for (Rn_report_builder report : savedModel.getRn_report_builder()) {
				report.setModule_id(savedModel.getId());
			}
		}

//database Config

		Set<BackendConfig_t> backends = existingModel.getBackendConfig_ts();
		for (BackendConfig_t oldbacked : backends) {
			BackendConfig_t newbackend = new BackendConfig_t();
			newbackend.setBackend_service_name(oldbacked.getBackend_service_name());
			newbackend.setTechstack(oldbacked.getTechstack());
			newbackend.setDescription(oldbacked.getDescription());
			newbackend.setProj_id(oldbacked.getProj_id());
			newbackend.setIsprimary(newbackend.isIsprimary());
			newbackend.getModule_Setups().add(newModel);
			newModel.getBackendConfig_ts().add(newbackend);

			BackendConfig_t savedbackend = backendConfig_Repository.save(newbackend);
			if (newModel.getRn_fb_headers() != null) {
				for (Rn_Fb_Header head : newModel.getRn_fb_headers()) {
					if (head.getBackend_id() == oldbacked.getId()) {
						head.setBackend_id(savedbackend.getId());
					}
				}
			}
		}

		savedModel = moduleSetup_Repository.save(newModel);

		return new ResponseEntity<>(newModel, HttpStatus.OK);
	}
	
	//GET ALL PUBLIC PROJECT
	@GetMapping("/getall_public_project")
	public ResponseEntity<?> getallPublic_proj(){
		List<Rn_Project_Setup> public_projects = projectSetupRepository.findProjectsByAccessibilityFalse();
		return new ResponseEntity(public_projects,HttpStatus.OK);
	}

//COPY FROM  ONLY PUBLIC PROJECT
	@GetMapping("/copypublic_proj/{id}")
	public ResponseEntity<?> getNewProjectBasedOnExistingProject(@PathVariable Integer id) {
		Optional<Rn_Project_Setup> optProject = projectSetupRepository.findById(id);
		if (!optProject.isPresent()) {
			return new ResponseEntity<>("Invalid Project id : " + id, HttpStatus.BAD_REQUEST);
		}
		Rn_Project_Setup existingProject = optProject.get();
		if (existingProject.getAccessibility() != null && existingProject.getAccessibility()) {
			return new ResponseEntity<>("This is a Private Project : " + id, HttpStatus.BAD_REQUEST);
		}
		AppUser loggedInUser = userService.getLoggedInUser();
		Rn_Project_Setup newProject = new Rn_Project_Setup();
        newProject.setCreatedBy(loggedInUser.getUserId());
        newProject.setOwned_by(loggedInUser.getUserId());
        newProject.setCreatedAt(Date.valueOf(LocalDate.now()));
		newProject.setProjectName(existingProject.getProjectName());
		newProject.setDescription(existingProject.getDescription());
		newProject.setCopyTo(existingProject.getCopyTo());
		newProject.setTechnologyStack(existingProject.getTechnologyStack());
		newProject.setProjectPrefix(existingProject.getProjectPrefix());
		newProject.setDbName(existingProject.getDbName());
		newProject.setDbUserName(existingProject.getDbUserName());
		newProject.setDbPassword(existingProject.getDbPassword());
		newProject.setPortNumber(existingProject.getPortNumber());
		newProject.setNamespace(existingProject.getNamespace());
		newProject.setTags(existingProject.getTags());
		newProject.setCategory(existingProject.getCategory());
		newProject.setAccessibility(existingProject.getAccessibility());
		newProject.setIs_archived(existingProject.getIs_archived());
		newProject.setIs_aged(existingProject.getIs_aged());
		newProject.setIs_fav(existingProject.getIs_fav());
		newProject.setFavCnt(existingProject.getFavCnt());
		newProject.setIs_stared(existingProject.getIs_stared());
		newProject.setStaredCnt(existingProject.getStaredCnt());
		newProject.setIs_watchlisted(existingProject.getIs_watchlisted());
		newProject.setWatchlistedCnt(existingProject.getWatchlistedCnt());
		newProject.setIs_futuristic(existingProject.getIs_futuristic());
		newProject.setFuturisticCnt(existingProject.getFuturisticCnt());
		newProject.setIs_pinned(existingProject.getIs_pinned());
		newProject.setPinnedCnt(existingProject.getPinnedCnt());
		newProject.setWorkflow_id(existingProject.getWorkflow_id());
		newProject.setGitea_url(existingProject.getGitea_url());

		// Copy the related modules (assuming each module has a reference to the
		// project)
		Rn_Project_Setup savedProject = projectSetupRepository.save(newProject);

		// ----------------------------------------------------------------------------------

		List<Rn_Module_Setup> existingmodules = existingProject.getModules();

		for (Rn_Module_Setup existingModel : existingmodules) {

			Rn_Module_Setup newModel = new Rn_Module_Setup();
newModel.setCreatedBy(loggedInUser.getUserId());
newModel.setCreatedAt(Date.valueOf(LocalDate.now()));
			newModel.setModuleName(existingModel.getModuleName());
			newModel.setDescription(existingModel.getDescription());
			newModel.setModulePrefix(existingModel.getModulePrefix());
			newModel.setTechnologyStack(existingModel.getTechnologyStack());
			newModel.setProjectName(savedProject.getProjectName());
			newModel.setCopyTo(existingModel.getCopyTo());
			newModel.setTags(existingModel.getTags());
			newModel.setReadme(existingModel.isReadme());
			newModel.setDbName(existingModel.getDbName());
			newModel.setDbUserName(existingModel.getDbUserName());
			newModel.setDbPassword(existingModel.getDbPassword());
			newModel.setPortNumber(existingModel.getPortNumber());
			newModel.setMicroservice(existingModel.isMicroservice());
			newModel.setPortaldeployment(existingModel.isPortaldeployment());
			newModel.setParentrepo(existingModel.getParentrepo());
			newModel.setProjectId(savedProject.getId());
			newModel.setProject(savedProject);
			newModel.setCopy_baseproj(existingModel.isCopy_baseproj());
			newModel.setLoginservice(existingModel.isLoginservice());
			newModel.setMicroservice(existingModel.isMicroservice());
			newModel.setTesting(existingModel.isTesting());

			// Headers Creation
			List<Rn_Fb_Header> existingHeaders = existingModel.getRn_fb_headers();
			if (existingHeaders != null && existingHeaders.size() != 0) {
				for (Rn_Fb_Header existingHeader : existingHeaders) {
					Rn_Fb_Header newHeader = new Rn_Fb_Header();
newHeader.setCreatedBy(loggedInUser.getUserId());
newHeader.setCreatedAt(Date.valueOf(LocalDate.now()));
					newHeader.setTechStack(existingHeader.getTechStack());
					newHeader.setObjectType(existingHeader.getObjectType());
					newHeader.setSubObjectType(existingHeader.getSubObjectType());
					newHeader.setUiName(existingHeader.getUiName());
					newHeader.setFormType(existingHeader.getFormType());
					newHeader.setTableName(existingHeader.getTableName());
					newHeader.setLineTableName(existingHeader.getLineTableName());
					newHeader.setMultilineTableName(existingHeader.getMultilineTableName());
					newHeader.setFormCode(existingHeader.getFormCode());
					newHeader.setJspName(existingHeader.getJspName());
					newHeader.setControllerName(existingHeader.getControllerName());
					newHeader.setServiceName(existingHeader.getServiceName());
					newHeader.setServiceImplName(existingHeader.getServiceImplName());
					newHeader.setDaoName(existingHeader.getDaoName());
					newHeader.setDaoImplName(existingHeader.getDaoImplName());
					newHeader.setBuild(existingHeader.isBuild());
					newHeader.setUpdated(existingHeader.isUpdated());
					newHeader.setMenuName(existingHeader.getMenuName());
					newHeader.setHeaderName(existingHeader.getHeaderName());
					newHeader.setConvertedTableName(existingHeader.getConvertedTableName());
					newHeader.setBackend_id(existingHeader.getBackend_id());
					newHeader.setTesting(existingHeader.isTesting());
					newHeader.setChild_form(existingHeader.isChild_form());

//					// Headers ------ (Rn Fb Lines)
//					List<Rn_Fb_Line> existingLines = existingHeader.getRn_fb_lines();
//
//					for (Rn_Fb_Line existingLine : existingLines) {
//
//						Rn_Fb_Line newLine = new Rn_Fb_Line();
//
//						newLine.setFieldName(existingLine.getFieldName());
//						newLine.setMapping(existingLine.getMapping());
//						newLine.setDataType(existingLine.getDataType());
//						newLine.setFormCode(existingLine.getFormCode());
//						newLine.setKey1(existingLine.getKey1());
//						newLine.setType1(existingLine.getType1());
//						newLine.setMandatory(existingLine.isMandatory());
//						newLine.setHidden(existingLine.isHidden());
//						newLine.setReadonly(existingLine.isReadonly());
//						newLine.setDependent(existingLine.isDependent());
//						newLine.setDependent_on(existingLine.getDependent_on());
//						newLine.setDependent_sp(existingLine.getDependent_sp());
//						newLine.setDependent_sp_param(existingLine.getDependent_sp_param());
//						newLine.setValidation_1(existingLine.isValidation_1());
//						newLine.setVal_type(existingLine.getVal_type());
//						newLine.setVal_sp(existingLine.getVal_sp());
//						newLine.setVal_sp_param(existingLine.getVal_sp_param());
//						newLine.setSequence(existingLine.isSequence());
//						newLine.setSeq_name(existingLine.getSeq_name());
//						newLine.setSeq_sp(existingLine.getSeq_sp());
//						newLine.setSeq_sp_param(existingLine.getSeq_sp_param());
//						newLine.setDefault_1(existingLine.isDefault_1());
//						newLine.setDefault_type(existingLine.getDefault_type());
//						newLine.setDefault_value(existingLine.getDefault_value());
//						newLine.setDefault_sp(existingLine.getDefault_sp());
//						newLine.setDefault_sp_param(existingLine.getDefault_sp_param());
//						newLine.setCalculated_field(existingLine.isCalculated_field());
//						newLine.setCal_sp(existingLine.getCal_sp());
//						newLine.setCal_sp_param(existingLine.getCal_sp_param());
//						newLine.setAdd_to_grid(existingLine.getAdd_to_grid());
//						newLine.setSp_for_autocomplete(existingLine.getSp_for_autocomplete());
//						newLine.setSp_for_dropdown(existingLine.getSp_for_dropdown());
//						newLine.setSp_name_for_autocomplete(existingLine.getSp_name_for_autocomplete());
//						newLine.setSp_name_for_dropdown(existingLine.getSp_name_for_dropdown());
//						newLine.setType_field(existingLine.getType_field());
//						newLine.setMethodName(existingLine.getMethodName());
//						newLine.setSeq(existingLine.getSeq());
//						newLine.setForm_type(existingLine.getForm_type());
//						newLine.setSection_num(existingLine.getSection_num());
//						newLine.setButton_num(existingLine.getButton_num());
//						newLine.setType2(existingLine.getType2());
//						newLine.setLine_table_name(existingLine.getLine_table_name());
//						newLine.setLine_table_no(existingLine.getLine_table_no());
//						newLine.setAction(existingLine.getAction());
//						newLine.setRequest_param(existingLine.getRequest_param());
//						newLine.setAction_uiname(existingLine.getAction_uiname());
//
//						// Set the relationship (rn_fb_header)
//						newLine.setRn_fb_header(newHeader);
//						if (newHeader.getRn_fb_lines() != null) {
//							newHeader.getRn_fb_lines().add(newLine);
//						} else {
//							newHeader.setRn_fb_lines(new ArrayList<>());
//							newHeader.getRn_fb_lines().add(newLine);
//						}
//						newLine.setRn_fb_header(newHeader);
//
//					}

					// Rn_cff_ActionBuilder_Header--------
					List<Rn_cff_ActionBuilder_Header> existingRn_cffff = existingHeader.getRn_cff_actionBuilder();
					if (existingRn_cffff != null) {
						for (Rn_cff_ActionBuilder_Header existingRn_cff : existingRn_cffff) {

							Rn_cff_ActionBuilder_Header newRn_cff_ActionBuilder_Header = new Rn_cff_ActionBuilder_Header();
newRn_cff_ActionBuilder_Header.setCreatedBy(loggedInUser.getUserId());
newRn_cff_ActionBuilder_Header.setCreatedAt(Date.valueOf(LocalDate.now()));
							newRn_cff_ActionBuilder_Header.setTechnologyStack(existingRn_cff.getTechnologyStack());
							newRn_cff_ActionBuilder_Header.setControllerName(existingRn_cff.getControllerName());
							newRn_cff_ActionBuilder_Header.setMethodName(existingRn_cff.getMethodName());
							newRn_cff_ActionBuilder_Header.setActionName(existingRn_cff.getActionName());
							newRn_cff_ActionBuilder_Header.setFileLocation(existingRn_cff.getFileLocation());

							// Action builder Lines
							List<Rn_cff_ActionBuilder_Lines> actionBuilderLinesexit = existingRn_cff
									.getActionBuilderLines();

							for (Rn_cff_ActionBuilder_Lines existLine : actionBuilderLinesexit) {
								Rn_cff_ActionBuilder_Lines newActionBuilderline = new Rn_cff_ActionBuilder_Lines();
newActionBuilderline.setCreatedBy(loggedInUser.getUserId());
newActionBuilderline.setCreatedAt(Date.valueOf(LocalDate.now()));
								newActionBuilderline.setActionType1(existLine.getActionType1());
								newActionBuilderline.setActionType2(existLine.getActionType2());
								newActionBuilderline.setDataType(existLine.getDataType());
								newActionBuilderline.setVariableName(existLine.getVariableName());
								newActionBuilderline.setAssignment(existLine.getAssignment());
								newActionBuilderline.setMessage(existLine.getMessage());
								newActionBuilderline.setConditions(existLine.getConditions());
								newActionBuilderline.setForward(existLine.getForward());
								newActionBuilderline.setEquation(existLine.getEquation());
								newActionBuilderline.setSeq(existLine.getSeq());
								newActionBuilderline.setAction(existLine.getAction());
								newActionBuilderline.setGroupId(existLine.getGroupId());

								if (newRn_cff_ActionBuilder_Header.getActionBuilderLines() != null) {
									newRn_cff_ActionBuilder_Header.getActionBuilderLines().add(newActionBuilderline);
								} else {
									newRn_cff_ActionBuilder_Header.setActionBuilderLines(new ArrayList<>());
									newRn_cff_ActionBuilder_Header.getActionBuilderLines().add(newActionBuilderline);
								}
								newActionBuilderline.setRn_cff_actionBuilderHeader(newRn_cff_ActionBuilder_Header);
							}

							// Set the relationship (rn_fb_header)
							newRn_cff_ActionBuilder_Header.setRn_fb_header(newHeader);

							if (newHeader.getRn_cff_actionBuilder() != null) {
								newHeader.getRn_cff_actionBuilder().add(newRn_cff_ActionBuilder_Header);
							} else {
								newHeader.setRn_cff_actionBuilder(new ArrayList<>());
								newHeader.getRn_cff_actionBuilder().add(newRn_cff_ActionBuilder_Header);
							}

						}
					}

					newHeader.setModule(newModel);
					if (newModel.getRn_fb_headers() != null) {
						newModel.getRn_fb_headers().add(newHeader);
					} else {
						newModel.setRn_fb_headers(new ArrayList<>());
						newModel.getRn_fb_headers().add(newHeader);
					}
				}
			}
			// Rn_Module_Setup savedModel = null;
			if (newModel != null) {
				// savedModel = moduleSetup_Repository.save(newModel);
			} else {
				break;
			}
			// Report Builder
			List<Rn_report_builder> existingBulders = existingModel.getRn_report_builder();
			if (existingBulders != null && existingBulders.size() != 0) {
				for (Rn_report_builder b : existingBulders) {
					Rn_report_builder newBuilder = new Rn_report_builder();
newBuilder.setCreatedBy(loggedInUser.getUserId());
newBuilder.setCreatedAt(Date.valueOf(LocalDate.now()));
					newBuilder.setReport_name(b.getReport_name());
					newBuilder.setDescription(b.getDescription());
					newBuilder.setReport_tags(b.getReport_tags());
					newBuilder.setDate_string(b.getDate_string());
					newBuilder.setAdd_param_string(b.getAdd_param_string());
					newBuilder.setMaster_select(b.getMaster_select());
					newBuilder.setGrid_headers(b.getGrid_headers());
					newBuilder.setStd_param_view(b.getStd_param_view());
					newBuilder.setGrid_values(b.getGrid_values());
					newBuilder.setModel_string(b.getModel_string());
					// ------------------------------------------------
					// newBuilder.setModule_id(savedModel.getId());
					newBuilder.setUiname(b.getUiname());
					newBuilder.setServicename(b.getServicename());
					newBuilder.setReporttype(b.getReporttype());
					newBuilder.setAccount_id(b.getAccount_id());
					newBuilder.setProject_id(b.getProject_id());
					newBuilder.setIs_build(b.getIs_build());
					newBuilder.setIs_updated(b.getIs_updated());

					newBuilder.setModule(newModel);
					if (newModel.getRn_report_builder() != null) {
						newModel.getRn_report_builder().add(newBuilder);
					} else {
						newModel.setRn_report_builder(new ArrayList<>());
						newModel.getRn_report_builder().add(newBuilder);
					}

				}
			}

			// Backend Config
			Set<BackendConfig_t> backends = existingModel.getBackendConfig_ts();
			for (BackendConfig_t oldbacked : backends) {
				BackendConfig_t newbackend = new BackendConfig_t();
				newbackend.setBackend_service_name(oldbacked.getBackend_service_name());
				newbackend.setTechstack(oldbacked.getTechstack());
				newbackend.setDescription(oldbacked.getDescription());
				newbackend.setProj_id(oldbacked.getProj_id());
				newbackend.setIsprimary(newbackend.isIsprimary());
				newbackend.setProj_id(savedProject.getId());
				newbackend.getModule_Setups().add(newModel);
				newModel.getBackendConfig_ts().add(newbackend);

				BackendConfig_t savedbackend = backendConfig_Repository.save(newbackend);
				if (newModel.getRn_fb_headers() != null) {
					for (Rn_Fb_Header head : newModel.getRn_fb_headers()) {
						if (head.getBackend_id() == oldbacked.getId()) {
							head.setBackend_id(savedbackend.getId());
						}
					}
				}
			}
			newModel.setProjectId(savedProject.getId());
			newModel.setProject(savedProject);
			if (savedProject.getModules() != null) {
				savedProject.getModules().add(newModel);
			} else {
				List<Rn_Module_Setup> list = new ArrayList<>();
				list.add(newModel);
				savedProject.setModules(list);
			}
			Rn_Module_Setup savedModule = moduleSetup_Repository.save(newModel);

			if (savedModule.getRn_report_builder() != null) {
				// ------------------------------------------------
				for (Rn_report_builder report : newModel.getRn_report_builder()) {
					report.setModule_id(savedModule.getId());
				}
			}
		}

		Rn_Project_Setup finalsavedProject = projectSetupRepository.save(savedProject);

		return new ResponseEntity<>(finalsavedProject, HttpStatus.OK);

	}

}
