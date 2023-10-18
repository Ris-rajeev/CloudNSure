//package com.realnet.builders;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonArray;
//
//import com.realnet.actionbuilder.service.Rn_Cff_ActionBuilder_Service;
//import com.realnet.flf.service.FieldTypeService;
//import com.realnet.fnd.service.Rn_LookUp_Service;
//import com.realnet.formdrag.repository.Rn_wf_lines_3Repository;
//import com.realnet.formdrag.entity.Rn_wf_lines_3;
//import com.realnet.wfb.service.Rn_WireFrame_Service;
//
//import io.swagger.annotations.Api;
//@RestController
//@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(tags = { "Master Builder" })
//public class tt_form_only_header_Builder {
//
//
// @Value("${angularProjectPath}")
//	private String angularProjectPath; @Value("${projectPath}")
//	private String projectPath; @Autowired
//	private Rn_WireFrame_Service wireFrameService;
//
//	@Autowired
//	private Rn_LookUp_Service lookUpService;
//@Autowired
//	private Rn_wf_lines_3Repository repo;
//
//	@Autowired
//	private Rn_Cff_ActionBuilder_Service actionBuilderService;
//
//	@Autowired
//	private ModelMapper modelMapper;
//
//	@Autowired
//	private FieldTypeService fieldTypeService;
//
//
//@GetMapping(value = "/tt_form_only_header_Builder/{header_id}")
//	public ResponseEntity<?> createbyjson(@PathVariable Integer header_id) throws IOException {
//		Optional<Rn_wf_lines_3> wireframe = repo.findheader(header_id);
//
//		List<String> tablename = new ArrayList<>();
//		List<String> entityname = new ArrayList<>();
//
//		JsonParser parser = new JsonParser();
//		JsonElement element = parser.parse(wireframe.get().getModel());
//		JsonObject jsonObject = element.getAsJsonObject();
//
//		JsonElement name = jsonObject.get("name");
//		System.out.println(name);
//		tablename.add(name.getAsString());
//
//		JsonElement desc = jsonObject.get("description");
//		System.out.println(desc);
////		keys.add("desc :"+desc.getAsString());
//
//		JsonElement element2 = jsonObject.get("attributes");
//		System.out.println(element2);
//
//		JsonArray jsonArray = element2.getAsJsonArray();
//		System.out.println(jsonArray);
//
//		for (JsonElement ar : jsonArray) {
//
//			JsonObject obj = ar.getAsJsonObject();
//
//			JsonElement type = obj.get("type");
//			System.out.println(type);
////			keys.add("type :"+type.getAsString());
//
//			JsonElement description = obj.get("description");
//			System.out.println(description);
////			keys.add("description :"+description.getAsString());
//
//			JsonElement placeholder = obj.get("placeholder");
////			System.out.println(placeholder);
////			keys.add("placeholder :"+placeholder.getAsString());
//
//			JsonElement label = obj.get("label");
//			System.out.println(label);
//			entityname.add(label.getAsString());
//
//		}
//
//		Date d = new Date();
//		String addString = "_";
//
//		// CALL BACKEND
//		String project_name ="test1";
////////FileWriter fw = null;
//BufferedWriter bw = null;
//String TestModel.java0 = "TestModel.java";
//String  mainstr0 = TestModel.java0;
//String TestModel.java01=mainstr0.replace(".java", "");
//
//
//
// StringBuilder TestModel.java0Code = new StringBuilder();
// TestModel.java0Code.append("package com.realnet." + module_name + ".model;" + "\r\n" + 
//"" + "\r\n" + 
//"import javax.persistence.GeneratedValue;" + "\r\n" + 
//"import javax.persistence.GenerationType;" + "\r\n" + 
//"import javax.persistence.Id;" + "\r\n" + 
//"" + "\r\n" + 
//"public class " + TestModel.java1 + "{"+
//"" + "\r\n" + 
//"	@Id" + "\r\n" + 
//"	@GeneratedValue(strategy = GenerationType.IDENTITY)" + "\r\n" + 
//"	private int id;" + "\r\n" + 
//"	private String name;" + "\r\n" + 
//"	private String email;" + "\r\n" + 
//"	private String mob_no;" + "\r\n" + 
//"}" 
//
//
//	File TestModel.java0File = new File(projectPath + "/Projects/" + project_name + "/" + TestModel.java0);
//	System.out.println("Directory name = " + TestModel.java0File);
//	File TestModel.java0FileParentDir = new File(TestModel.java0File.getParent());
//	if(!TestModel.java0FileParentDir.exists()) {
//	TestModel.java0FileParentDir.mkdirs();
//			}
//	if (!TestModel.java0File.exists()) {
//				TestModel.java0File.createNewFile();
//			}
//			fw = new FileWriter(TestModel.java0File.getAbsoluteFile());
//	bw = new BufferedWriter(fw);
//			bw.write(TestModel.java0Code.toString());
//	bw.close();
//
//
//	return new ResponseEntity<>("created", HttpStatus.CREATED);
//}
// }