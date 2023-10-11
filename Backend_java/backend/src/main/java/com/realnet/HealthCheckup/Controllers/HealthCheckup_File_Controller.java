package com.realnet.HealthCheckup.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.realnet.HealthCheckup.Entity.HealthCheckFiles;
import com.realnet.HealthCheckup.Repository.HealthCheckup_file_Repository;
import com.realnet.utils.Constant;
import com.realnet.utils.Port_Constant;

@RequestMapping(value = "/HealthCheckupfile")
@RestController
public class HealthCheckup_File_Controller {

	@Autowired
	private HealthCheckup_file_Repository checkup_file_Repository;

//	get all file
	@GetMapping("/HealthCheckupfile/{health_id}")
	public List<HealthCheckFiles> getdetails(@PathVariable Long health_id) {
		List<HealthCheckFiles> get = checkup_file_Repository.findbyhealthid(health_id);
		return get;
	}

//	read file
	@GetMapping("/readfile/{file_id}")
	public ResponseEntity<?> fileead(@PathVariable Long file_id) throws IOException {
		HealthCheckFiles healthfile = checkup_file_Repository.findById(file_id).get();

		String file_name = healthfile.getFile_name();
		String file_path = healthfile.getFile_path();

		String path = file_path + File.separator + file_name;

		File file = new File(path);

		String string = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

		if (string.isEmpty()) {
			return new ResponseEntity<>("no data", HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<>(string, HttpStatus.OK);

		}

	}

//	REDEPLOY

	@GetMapping("/readeploy/{health_id}")
	public ResponseEntity<?> redeploy(@PathVariable Long health_id) throws IOException {
		List<HealthCheckFiles> files = checkup_file_Repository.findbyhealthid(health_id);

		int i = 0;
		for (HealthCheckFiles healthfile : files) {

			String filename = healthfile.getFile_name();

			if (filename.contains(".sh")) {
				String filepath = "/home/ubuntu" + healthfile.getFile_path();

				Map<String, String> jobdata = new HashMap<String, String>();
//			jobdata.put("parameters", builder.toString());
				jobdata.put("url",
						"http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.PROD_SCRIPTRUNNER_JAVA_8901
								+ "/api/runScript?filepath=" + filepath + "&filename=" + filename);
				jobdata.put("method", "GET");
				jobdata.put("connection_name", "jobprtal");
				jobdata.put("createdBy", "2022");
				jobdata.put("updatedBy", "2022");
				jobdata.put("job_type", "CreatesureprjPrj");
				jobdata.put("ref", health_id.toString());
				System.out.println(jobdata);

				RestTemplate restTemplate = new RestTemplate();
				String jobprourl = "http://" + Port_Constant.LOCAL_HOST + ":" + Port_Constant.JOBPRO_PORT_8087
						+ "/jobpro/pipiline";
				HttpHeaders headers = getHeaders();
				HttpEntity<Object> request = new HttpEntity<Object>(jobdata, headers);
				ResponseEntity<Object> res1 = restTemplate.postForEntity(jobprourl, request, Object.class);
				if (res1.getStatusCodeValue() == 200) {
					System.out.println("script runner data inserted in job pro");
					System.out.println(res1.getBody());
					i++;

				}
			}
		}

		if (i == 0) {
			return new ResponseEntity<>("redeployment not done", HttpStatus.BAD_REQUEST);

		} else {
			return new ResponseEntity<>("redeployment done", HttpStatus.OK);

		}

	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}