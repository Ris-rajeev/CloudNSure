package com.realnet.codeextractor.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.entity.Rule_library_keyword;
import com.realnet.codeextractor.repository.Rule_library_keywordRepository;
import com.realnet.codeextractor.service.Rn_Bcf_Exception_Rule_Library_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Params_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Extractor_Service;
import com.realnet.codeextractor.service.Rn_Bcf_Rule_Library_Service;
import com.realnet.codeextractor.service.Rule_library_keywordService;
import com.realnet.codeextractor.service.Rule_library_service_gk;
import com.realnet.fnd.entity.Error;
import com.realnet.fnd.entity.ErrorPojo;
import com.realnet.fnd.entity.Success;
import com.realnet.fnd.entity.SuccessPojo;
import com.realnet.logging.LogExecutionTime;
import com.realnet.utils.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/token/codeextractor/dynamic", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Code Extractor" })
public class DynamicCodeExtractionController {
	@Value("${projectPath}")
	private String projectPath;

	@Autowired
	private Rn_Bcf_Extractor_Service rn_bcf_extractor_service;

	@Autowired
	private Rule_library_keywordRepository keywordRepository;

	@Autowired
	private Rule_library_service_gk rule_library_service_gk;

//	@Autowired
//	private RealNetUtils utils;

	@Autowired
	Rn_Bcf_Exception_Rule_Library_Service rn_bcf_rule_exception_library_service;

	@LogExecutionTime
	@ApiOperation(value = "Dynamic Code Extraction")
	@GetMapping(value = "/dynamic_code_extraction")
	public ResponseEntity<?> dynamicCodeExtraction(@RequestParam(value = "header_id") Integer headerId)
			throws IOException, ParseException {

		Rn_Bcf_Extractor extractor = rn_bcf_extractor_service.getById(headerId);
		String technology_stack = extractor.getTech_stack();
		String object_type = extractor.getObject_type();
		String sub_object_type = extractor.getSub_object_type();
		String service = extractor.getService();

		System.out.println("FROM Rn_Bcf_Extractor = " + technology_stack + " || " + service + " || " + object_type
				+ " || " + sub_object_type + "||" + headerId);

		// PARAMS
		List<Rn_Bcf_Extractor_Params> params = extractor.getRn_bcf_extractor_Params();

		// STATIC FILES ( SE_FILE_NAME.EXT)
		ArrayList<String> staticFiles = new ArrayList<String>();

		try {

			// PARAMETERS TABLE VALUE
			for (Rn_Bcf_Extractor_Params param : params) {
//				System.out.println("---Exception rule for loop--");
				boolean is_extraction_enabled = param.isIs_extraction_enabled();
				boolean is_creation_enabled = param.isIs_creation_enabled();
				String path = param.getMoved_address_string();

				File file = new File(path);
				String parentPath = file.getParent();

				String name = file.getName();
				String ConvertedFileName = "SE_" + name;

				// STATIC FILES DIRECTORY
				String staticFileParentDir = parentPath + File.separator + "static_code";
				File staticFile = new File(staticFileParentDir + File.separator + ConvertedFileName);

				// STATIC FILE PATH
				String staticFileDir = staticFile.getAbsolutePath().replace("\\", "/");
				// ADD STATIC FILE PATH TO ARRAYLIST
				if (is_extraction_enabled && is_creation_enabled) {
					// System.out.println("STATIC FILE DIR ADDED TO ARRAYLIST = " + staticFileDir);
					staticFiles.add(staticFileDir);

				}

				// ** APPLY RULES ****
				List<Rule_library_keyword> rules = keywordRepository.findByTech_stack(technology_stack,service,object_type,sub_object_type);

				
				for (Rule_library_keyword keyword : rules) {

					Integer keyword_id = keyword.getId();
					keyword.getTech_stack();
					keyword.getObject_type();
					keyword.getSub_object_type();
					keyword.getService();

//					if (technology_stack.equalsIgnoreCase(key_technology_stack)
//							&& object_type.equalsIgnoreCase(key_object_type)
//							&& sub_object_type.equalsIgnoreCase(key_sub_object_type)
//							&& service.equalsIgnoreCase(key_service)) {
						rule_library_service_gk.rulebykeyword(param.getId(), keyword_id, extractor);
//					}

				}
			}

			// package part MODULE_NAME ADD

//		
//			for (String staticDir : staticFiles) 
//			{
//				
//				File file = new File(staticDir);
//				Path path = Paths.get(staticDir);
//				StringBuilder code = new StringBuilder();
//				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
//			
//			
//				for (String line : lines) 
//				{
//					
//					if (line.startsWith("\"package")) 
//					{
//						int i = line.lastIndexOf(".");
//						String head = line.substring(0, i + 1);
//						String tail = line.substring(i);
//						String moduleName = "\" + module_name + \"";
//						line = head + moduleName + tail;
//					}				
//						code.append(line + "\n");
//					
//				}
//				
//				//System.out.println(code);
//				BufferedWriter br = new BufferedWriter(new FileWriter(file)); // replaced string
//				br.write(code.toString());
//				br.close();
//				
//			}

//			int count = 0;
//			for (String staticDir : staticFiles)
//			{
//				File file1 = new File(staticDir);
//				Path path1 = Paths.get(staticDir);
//				StringBuilder code = new StringBuilder();
//				StringBuilder code2 = new StringBuilder();
//				List<String> lines = Files.readAllLines(path1, StandardCharsets.UTF_8);
//				for (String line : lines)
//				{
//					if (line.startsWith("\"public class"))
//					{
////						
//						String file_name_var=params.get(count).getFile_name_var().replaceAll(".java", "");
//						
////						System.out.println("file namevar "+file_name_var);
//									
//									
//						line = "\"public class \" + "+file_name_var+""+1+" + \"{\"+";
//					}
//					
//					
//					
//					 if (line.startsWith("\"public interface"))
//					{
////						
//						String file_name_var=params.get(count).getFile_name_var();
//						
////						System.out.println("file namevar "+file_name_var);
//									
//									
//						line = "\"public interface \" + "+file_name_var+""+1+" + \"{\"+";
//					} 
//					 
//				 
//					 if (line.startsWith("\"  templateUrl"))
//					{
////							"  templateUrl: './readonly.component.html'," + "\r\n" + 	
//								String file_name_var=params.get(count).getFile_name_var();
//								
////								System.out.println("file namevar "+file_name_var);
//											
//											
//								line = " \" templateUrl: './\"+"+file_name_var+""+1+"+\".html',\"" + "\r\n \n" +"+"  ;
//					}
//					 
//					 if (line.startsWith("\"  styleUrls"))
//						{
////							"  styleUrls: ['./readonly.component.scss']" + "\r\n" + 	
//								String file_name_var=params.get(count).getFile_name_var();
//								
////								System.out.println("file namevar "+file_name_var);
//											
//											
//								line = " \"  styleUrls: ['./\"+"+file_name_var+""+1+"+\".scss']\"" + "\r\n \n"+"+"   ;
//						}
//					 
//					 if (line.startsWith("\"export class"))
//					{
////					"export class ReadonlyComponent implements OnInit {" + "\r\n" + 	
//						String file_name_var=params.get(count).getFile_name_var();
//						
////						System.out.println("file namevar "+file_name_var);
//									
//									
//						line = "\"export class \"+"+file_name_var+""+2+"+\"Component implements OnInit {\"" + "\r\n"+"+"  ;
//					}
//				
//					
//					
//					code.append(line + "\n");
//					
//				}
//				count++;
//				BufferedWriter br = new BufferedWriter(new FileWriter(file1)); // replaced string
//				br.write(code.toString());
//				br.close();
//			}

			// APPLY ALL RULES PRESENT HERE
////			List<Rn_Bcf_Rules> rules = bcfRuleLibraryService.getAll();
////
////			int rulesOuterCount = 0;
////			for (Rn_Bcf_Rules rule : rules) {
////				rulesOuterCount++;
////				// System.out.println("RULES FOR COUNT = " + ++rulesOuterCount);
////				String start = rule.getIdentifier_start_string();
////				String end = rule.getIdentifier_end_string();
////				String replaceWith = rule.getReplacement_string();
////
////				String tech_stack = rule.getTech_stack();
////				String sub_object_type2 = rule.getSub_object_type();
////				String object_type2 = rule.getObject_type();
////				replaceWith = replaceWith.concat("");
////
////				if (technology_stack.equals(tech_stack) && sub_object_type.equals(sub_object_type2)
////						&& object_type.equals(object_type2)) {
//////					System.out.println("main tech stack"+technology_stack+"  new "+tech_stack);
//////					System.out.println("main subobject  "+sub_object_type+"  new "+sub_object_type2);
//////					System.out.println("main object  "+object_type+"  new "+object_type2);
////
////					int dirCount = 0;
////					for (String staticDir : staticFiles) {
////						dirCount++;
////
////						File staticFile = new File(staticDir);
////						String staticFileName = staticFile.getName();
////
////						// System.out.println("========" + staticFileName + "=============");
////						String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
////						String fileType = FilenameUtils.getExtension(staticFileName);
////
////						// don't check empty file for replacement..
////						if (!fileString.isEmpty()) {
////
////							// RULE APPLY
////							String finalString = RealNetUtils.stringReplace(fileString, start, end, replaceWith,
////									fileType);
////
////							BufferedWriter bw = new BufferedWriter(new FileWriter(staticFile, false)); // replaced
////																										// string
////							bw.write(finalString);
////							bw.close();
////
////						}
////					}
////				}
//
////				System.out.println("loop count::"+dirCount);
//
//			}
//			System.out.println("rules outer countt::" + rulesOuterCount);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ErrorPojo errorPojo = new ErrorPojo();
			Error error = new Error();
			error.setTitle(Constant.EXTRACTOR_API_TITLE);
			error.setMessage(Constant.DYNAMIC_EXTRACTION_FAILED);
			errorPojo.setError(error);
			return new ResponseEntity<ErrorPojo>(errorPojo, HttpStatus.EXPECTATION_FAILED);
		}

		SuccessPojo successPojo = new SuccessPojo();
		Success success = new Success();
		success.setTitle(Constant.EXTRACTOR_API_TITLE);
		success.setMessage(Constant.DYNAMIC_EXTRACTION_SUCCESS);
		successPojo.setSuccess(success);
		log.debug("Response {} ", successPojo);
		return new ResponseEntity<SuccessPojo>(successPojo, HttpStatus.OK);

	}

}