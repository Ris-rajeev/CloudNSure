package com.realnet.ProjectManagement.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realnet.ProjectManagement.Documents.Pm_Uploadphoto;
import com.realnet.ProjectManagement.Entity.Pm_Iteration;
import com.realnet.ProjectManagement.Entity.ProjectDto;
import com.realnet.ProjectManagement.Entity.Projectmix;
import com.realnet.ProjectManagement.Repository.IterationRepository;
import com.realnet.ProjectManagement.Service.IterationService;
import com.realnet.ncso.entity1.OrderAttachment;
import com.realnet.ncso.entity1.OrderDetails;

@RestController
public class IterationController {
	
	@Autowired
	private IterationService iterationService;
	
	@Autowired
	private IterationRepository iterationRepository;
	
//	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/imagedata";

	@Value("${projectPath}")
	private String projectPath;
//	@PostMapping("/uplo")
//	public ResponseEntity<?> save(
//			@RequestBody Pm_Iteration iteration,
//			@RequestParam("document") MultipartFile file){
//		
////		Pm_Iteration iteration= new Pm_Iteration();
////		Uploadphoto upload = new Uploadphoto();
//		
//		
//		String uploadPath = projectPath.concat("/src/main/resources/logo/");
//
//			StringBuilder fileNames = new StringBuilder();
//			String filename= file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
//			Path fileNameAndPath =Paths.get(uploadPath,filename);
//			try {
//				Files.write(fileNameAndPath, file.getBytes());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////				upload.setName(filename);
//			iteration.setPhoto(filename);
////			Uploadphoto uploadphoto = iterationRepository.save(upload);
//			Pm_Iteration save = iterationRepository.save(iteration);
//		
//		return  ResponseEntity.status(HttpStatus.CREATED).body(save);
//		
//	}
	
//	    @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,
//			 MediaType.MULTIPART_FORM_DATA_VALUE })
//
//        public Pm_Iteration upload(@RequestPart("user") String user, @RequestPart("file") List<MultipartFile> file) {
//		String uploadPath = projectPath.concat("/src/main/resources/logo/");
//		
//		StringBuilder fileNames = new StringBuilder();
//		String filename= ((MultipartFile) file).getOriginalFilename().substring(((MultipartFile) file).getOriginalFilename().length()-4);
//		Path fileNameAndPath =Paths.get(uploadPath,filename);
//		try {
//			Files.write(fileNameAndPath, ((MultipartFile) file).getBytes());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////			upload.setName(filename);
////		user.setPhoto(filename);
////		Uploadphoto uploadphoto = iterationRepository.save(upload);
////		Pm_Iteration save = iterationRepository.save(iteration);
//
//
//		Pm_Iteration userJson = iterationService.getJson(user, file);
//      return userJson;
//}
	
	
	
//	 @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//	    public @ResponseBody Pm_Iteration upload(@RequestPart("addtionalData") String addtionalData,
//	    		                                 @RequestPart("fileList") List<MultipartFile> fileList) throws IOException {
//
//		 Pm_Iteration pm_Iteration = new ObjectMapper().readValue(addtionalData, Pm_Iteration.class);
//		 pm_Iteration.setContent(fileList);
//	 }
	
	
//	 add data with file
	
	@PreAuthorize("hasAnyRole('SYSADMIN','SCUM_MASTER')")
	@PostMapping("/workflow")
	public ResponseEntity<?> add(@RequestParam String o1,
			@RequestParam Map<String, MultipartFile> attachmentFile) throws Exception {
		//this method is now onward for SAVE so status =P
		Pm_Iteration pm_Iteration;
	
		pm_Iteration = new ObjectMapper().readValue(o1, Pm_Iteration.class);
		Pm_Iteration order = iterationService.create(pm_Iteration);

		if (!attachmentFile.isEmpty()) {
			ArrayList<Pm_Uploadphoto> attachments = new ArrayList<Pm_Uploadphoto>();
			for (Map.Entry<String, MultipartFile> e : attachmentFile.entrySet()) {
				System.out.println(e.getKey());
				Pm_Uploadphoto a = new Pm_Uploadphoto();
				a.setPm_Iteration(order);
				a.setAttachment(e.getValue().getBytes());
				a.setAttachmentType(e.getValue().getContentType());
				a.setAttachmentFilename(e.getValue().getName());
				attachments.add(a);
			}
			iterationService.addAllAtthacments(attachments);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
//	update data with file
	
	@PreAuthorize("hasAnyRole('SYSADMIN','SCUM_MASTER')")
	@PutMapping("/workflow/{id}")
	public ResponseEntity<?> update(@RequestParam String o1,
			@RequestParam Map<String, MultipartFile> attachmentFile,
			@PathVariable int id) throws Exception {
		//this method is now onward for SAVE so status =P
		Pm_Iteration pm_Iteration;
	
		pm_Iteration = new ObjectMapper().readValue(o1, Pm_Iteration.class);
		Pm_Iteration order = iterationService.update(pm_Iteration, id);

		if (!attachmentFile.isEmpty()) {
			ArrayList<Pm_Uploadphoto> attachments = new ArrayList<Pm_Uploadphoto>();
			for (Map.Entry<String, MultipartFile> e : attachmentFile.entrySet()) {
				System.out.println(e.getKey());
				Pm_Uploadphoto a = new Pm_Uploadphoto();
				a.setPm_Iteration(order);
				a.setAttachment(e.getValue().getBytes());
				a.setAttachmentType(e.getValue().getContentType());
				a.setAttachmentFilename(e.getValue().getOriginalFilename());
				attachments.add(a);
			}
			iterationService.addAllAtthacments(attachments);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	

	
	
//	Get all
	@GetMapping("/workflow")
	public ResponseEntity<?> getall(){
		List<Pm_Iteration> pm = iterationService.getall();
	return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	
//	get by id
	@GetMapping("/workflow/{id}")
	public ResponseEntity<?> getbyid(@PathVariable int id){
	Pm_Iteration pm = iterationService.getbyid(id);
		
		List<Pm_Uploadphoto> orderAttachments = iterationService.getallattachmentsbyid(id);
		
		pm.setDoc(orderAttachments);


		return 	new ResponseEntity<>(pm, HttpStatus.OK);
	}
	
	
	
//	delete by id
	@DeleteMapping("/workflow/{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
//		Pm_Iteration order = iterationService.getbyid(id);
//		if(order != null) {
			iterationService.deleteAttachment(id);
			iterationService.deletebyid(id);
//		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		}
	
//	get all iteration upload photo only
	@GetMapping("/getallupload")
	public List<Pm_Uploadphoto> get(){
		 List<Pm_Uploadphoto> pm_Uploadphotos = iterationService.get();
		 return pm_Uploadphotos;
	}
	

//GET ID AND NAME
	 @GetMapping("/iterationname")
	    public ResponseEntity<?> getiteration() {
	    	
	    	List<ProjectDto> s = iterationService.getallpro();
	    	return new ResponseEntity<>(s,HttpStatus.OK);
	    }
	
	
	
	}
	


