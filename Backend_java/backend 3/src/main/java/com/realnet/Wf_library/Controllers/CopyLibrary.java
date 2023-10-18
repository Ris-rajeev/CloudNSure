package com.realnet.Wf_library.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.realnet.Wf_library.Entity.WfLib_Lb_Header;
import com.realnet.Wf_library.Entity.Wf_lib_Lb_Line;
import com.realnet.Wf_library.Entity.Wf_library_t;
import com.realnet.Wf_library.Repository.Wf_lib_LbHeader_Repository;
import com.realnet.Wf_library.Repository.Wf_library_Repository;
import com.realnet.fnd.entity.Rn_Module_Setup;
import com.realnet.fnd.repository.Rn_ModuleSetup_Repository;
import com.realnet.formdrag.entity.Rn_wf_lines_3;
import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
import com.realnet.listbuilder.Entity.Lb_Header;
import com.realnet.listbuilder.Entity.Lb_Line;
import com.realnet.listbuilder.Repository.Lb_HeaderRepository;
import com.realnet.users.entity1.AppUser;
import com.realnet.users.service1.AppUserService;
import com.realnet.wfb.entity.Rn_Fb_Header;
import com.realnet.wfb.repository.Rn_Fb_Header_Repository;

@RestController
@RequestMapping("/wflibrary/copylib")
public class CopyLibrary {

	@Autowired
	private Rn_wf_lines_3Repository repo;

	@Autowired
	private Rn_Fb_Header_Repository header_Repository;

	@Autowired
	private AppUserService userService;

	@Autowired
	private Wf_library_Repository library_Repository;

	@Autowired
	private Rn_ModuleSetup_Repository moduleSetup_Repository;

	@Autowired
	private Rn_wf_lines_3Repository wfline_repo;

	@Autowired
	private Lb_HeaderRepository lb_headerRepository;

	@Autowired
	private Wf_lib_LbHeader_Repository wf_lib_LbHeader_Repository;

	// GET ALL WF_LIBRARY
	@GetMapping("/getall_wf_lib")
	public ResponseEntity<?> getAllWfLibrary() {
		List<Wf_library_t> lib_list = library_Repository.findAll();
		return new ResponseEntity<>(lib_list, HttpStatus.OK);
	}

//	COPY WIREFRAME TO LIBRARY or ADD TO LIBRARY
	@GetMapping("/copy_library/{id}")
	public Wf_library_t copytolibrary(@PathVariable Integer id) {

		AppUser loggedInUser = userService.getLoggedInUser();
		Rn_Fb_Header oldHeader = header_Repository.findById(id).get();
		Long backend_id = oldHeader.getBackend_id();
		Integer moduleid = oldHeader.getModule().getId();
		Wf_library_t library_t = new Wf_library_t();

		library_t.setCreatedBy(loggedInUser.getUserId());
		library_t.setTechStack(oldHeader.getTechStack());
		library_t.setObjectType(oldHeader.getObjectType());
		library_t.setSubObjectType(oldHeader.getSubObjectType());
		library_t.setUiName(oldHeader.getUiName());
		library_t.setFormType(oldHeader.getFormType());
		library_t.setFormCode(oldHeader.getFormCode());
		library_t.setTableName(oldHeader.getTableName());
		library_t.setLineTableName(oldHeader.getLineTableName());
		library_t.setMultilineTableName(oldHeader.getMultilineTableName());
		library_t.setJspName(oldHeader.getJspName());
		library_t.setControllerName(oldHeader.getControllerName());
		library_t.setServiceName(oldHeader.getServiceName());
		library_t.setServiceImplName(oldHeader.getServiceImplName());
		library_t.setDaoName(oldHeader.getDaoName());
		library_t.setDaoImplName(oldHeader.getDaoImplName());
		library_t.setBuild(false);
		library_t.setUpdated(oldHeader.isUpdated());
		library_t.setMenuName(oldHeader.getMenuName());
		library_t.setHeaderName(oldHeader.getHeaderName());
		library_t.setConvertedTableName(oldHeader.getControllerName());
		library_t.setTesting(true);
		library_t.setIschild(false);

		Rn_wf_lines_3 wf_lines_3 = repo.findheader(oldHeader.getId()).orElseThrow(null);
		String model = wf_lines_3.getModel();
		library_t.setModel(model);
		Wf_library_t save = library_Repository.save(library_t);

		JsonParser parser = new JsonParser();
		JsonElement model_element = parser.parse(model);
		JsonObject jsonObject = model_element.getAsJsonObject();
		JsonElement dash_element = jsonObject.get("dashboard");

		if (dash_element != null && !dash_element.isJsonNull() && !dash_element.isJsonPrimitive()) {

			JsonArray jsonArray = dash_element.getAsJsonArray();
			System.out.println(jsonArray);

			for (JsonElement ar : jsonArray) {

				JsonObject obj1 = ar.getAsJsonObject();

				String field_value1 = obj1.get("charttitle").getAsString();
				String field2 = field_value1.replaceAll(" ", "_");
				System.out.println(field_value1);

				String type = obj1.get("type").getAsString();
				String data_type1 = type.replaceAll(" ", "_");

				if (field_value1.equalsIgnoreCase("OnetoOne") || field_value1.equalsIgnoreCase("OnetoMany")
						|| field_value1.equalsIgnoreCase("ManytoMany")) {
					String child_table = obj1.get("toWireframe").getAsString();

					List<Rn_Fb_Header> getallwfforchild = header_Repository.getallwfforchild(moduleid, backend_id);

					for (Rn_Fb_Header header : getallwfforchild) {
						Rn_wf_lines_3 wf = wfline_repo.findheader(header.getId()).get();
						String string = parser.parse(wf.getModel()).getAsJsonObject().get("name").toString()
								.replaceAll("\"", "").replaceAll(" ", "_");

						if (child_table.equalsIgnoreCase(string)) {

							Wf_library_t lib = new Wf_library_t();

							lib.setIschild(true);
							lib.setLibraryId(save.getId());
							lib.setCreatedBy(loggedInUser.getUserId());
							lib.setTechStack(header.getTechStack());
							lib.setObjectType(header.getObjectType());
							lib.setSubObjectType(header.getSubObjectType());
							lib.setUiName(header.getUiName());
							lib.setFormType(header.getFormType());
							lib.setFormCode(header.getFormCode());
							lib.setTableName(header.getTableName());
							lib.setLineTableName(header.getLineTableName());
							lib.setMultilineTableName(header.getMultilineTableName());
							lib.setJspName(header.getJspName());
							lib.setControllerName(header.getControllerName());
							lib.setServiceName(header.getServiceName());
							lib.setServiceImplName(header.getServiceImplName());
							lib.setDaoName(header.getDaoName());
							lib.setDaoImplName(header.getDaoImplName());
							lib.setBuild(false);
							lib.setUpdated(header.isUpdated());
							lib.setMenuName(header.getMenuName());
							lib.setHeaderName(header.getHeaderName());
							lib.setConvertedTableName(header.getControllerName());
							lib.setTesting(true);

							Rn_wf_lines_3 wf_line = repo.findheader(header.getId()).orElseThrow(null);
							String model1 = wf_line.getModel();
							library_t.setModel(model1);
							library_Repository.save(library_t);

						}

					}

				}

				else if (data_type1.equalsIgnoreCase("select")) {
					String listname = obj1.get("dynamicList").getAsString();
					Lb_Header lb_header = lb_headerRepository.getbylistname(moduleid, listname);

					ArrayList<Wf_lib_Lb_Line> wflb_line = new ArrayList<>();

					if (lb_header != null) {

						WfLib_Lb_Header header = new WfLib_Lb_Header();
						header.setDescription(lb_header.getDescription());
						header.setLb_name(lb_header.getLb_name());
						header.setMenuName(lb_header.getMenuName());
						header.setObject_type(lb_header.getObject_type());
						header.setSub_object_type(lb_header.getSub_object_type());
						header.setSecuirity_profile(lb_header.getSecuirity_profile());
						header.setLibrary_id(save.getId());

						List<Lb_Line> lb_Line = lb_header.getLb_Line();

						for (Lb_Line l : lb_Line) {

							Wf_lib_Lb_Line line = new Wf_lib_Lb_Line();

							line.setModel(l.getModel());
							wflb_line.add(line);

						}

						header.setLb_Line(wflb_line);
						wf_lib_LbHeader_Repository.save(header);

					}

				}

			}
		}

		return save;
	}

//	COPY   WIREFRAME FROM LIBRARY
	@GetMapping("/copy_library/{libraryid}/{module_id}")
	public Rn_Fb_Header copytowireframe(@PathVariable Long libraryid, @PathVariable Integer module_id) {

		AppUser loggedInUser = userService.getLoggedInUser();
		Long userId = loggedInUser.getUserId();

		Wf_library_t oldHeader = library_Repository.findById(libraryid).get();
		Rn_Fb_Header newHeader = new Rn_Fb_Header();

		newHeader.setCreatedBy(userId);
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

		Rn_Module_Setup module_Setup = moduleSetup_Repository.findById(module_id).get();
		newHeader.setModule(module_Setup); // change

		Rn_Fb_Header savedHeader = header_Repository.save(newHeader);
		try {

			Rn_wf_lines_3 lines_3 = new Rn_wf_lines_3();
			lines_3.setHeader_id(savedHeader.getId());
			lines_3.setModel(oldHeader.getModel());
			repo.save(lines_3);
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<Wf_library_t> list = library_Repository.getallby_libid(libraryid);

		for (Wf_library_t w : list) {
			Rn_Fb_Header newh = new Rn_Fb_Header();

			newh.setCreatedBy(userId);
			newh.setTechStack(w.getTechStack());
			newh.setObjectType(w.getObjectType());
			newh.setSubObjectType(w.getSubObjectType());
			newh.setUiName(w.getUiName());
			newh.setFormType(w.getFormType());
			newh.setFormCode(w.getFormCode());
			newh.setTableName(w.getTableName());
			newh.setLineTableName(w.getLineTableName());
			newh.setMultilineTableName(w.getMultilineTableName());
			newh.setJspName(w.getJspName());
			newh.setControllerName(w.getControllerName());
			newh.setServiceName(w.getServiceName());
			newh.setServiceImplName(w.getServiceImplName());
			newh.setDaoName(w.getDaoName());
			newh.setDaoImplName(w.getDaoImplName());
			newh.setBuild(false);
			newh.setUpdated(w.isUpdated());
			newh.setMenuName(w.getMenuName());
			newh.setHeaderName(w.getHeaderName());
			newh.setConvertedTableName(w.getControllerName());
			newh.setTesting(true);

			newh.setModule(module_Setup); // change

			Rn_Fb_Header savedHeader1 = header_Repository.save(newHeader);
			try {

				Rn_wf_lines_3 lines = new Rn_wf_lines_3();
				lines.setHeader_id(savedHeader1.getId());
				lines.setModel(w.getModel());
				repo.save(lines);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		List<WfLib_Lb_Header> libid = wf_lib_LbHeader_Repository.getallby_libid(libraryid);

		for (WfLib_Lb_Header wfLib_Lb_Header : libid) {

			ArrayList<Lb_Line> lb_line = new ArrayList<>();

			Lb_Header header = new Lb_Header();

			header.setDescription(wfLib_Lb_Header.getDescription());
			header.setLb_name(wfLib_Lb_Header.getLb_name());
			header.setMenuName(wfLib_Lb_Header.getMenuName());
			header.setObject_type(wfLib_Lb_Header.getObject_type());
			header.setSub_object_type(wfLib_Lb_Header.getSub_object_type());
			header.setSecuirity_profile(wfLib_Lb_Header.getSecuirity_profile());

			List<Wf_lib_Lb_Line> lb_Line = wfLib_Lb_Header.getLb_Line();

			for (Wf_lib_Lb_Line l : lb_Line) {

				Lb_Line line = new Lb_Line();

				line.setModel(l.getModel());
				lb_line.add(line);

			}

			header.setLb_Line(lb_line);
			lb_headerRepository.save(header);

		}

		return savedHeader;
	}

}
