package com.realnet.bugTracker.Helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Fileuploadhelper {

	@Value("${projectPath}")
	private String projectPath;

	/*
	 * public final String UPLOAD_DIREC=
	 * "C:/Users/user/Desktop/SureServe-ashwini5dec/backend/src/main/resources/images";
	 */

	public final String UPLOAD_DIREC = "/src/main/resources/sshkeys/";

	public String uploadFile(MultipartFile file) {
		boolean f = false;
		String dir = "";
		try {
			Date d = new Date();
			String addString = d.getTime() + "_" + file.getOriginalFilename();

//			String dir =projectPath+UPLOAD_DIREC+addString;
			dir = projectPath + addString;

			byte[] bytes = file.getBytes();

			Path path = Paths.get(dir);
			Path write = Files.write(path, bytes);
			System.out.println(write);

//			f = dir;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return dir;
	}

	public String replaceFile(MultipartFile file, String dir) {
		boolean f = false;
		String dir2 = "";
		try {
			Date d = new Date();
			String addString = d.getTime() + "_" + file.getOriginalFilename();

//			String dir =projectPath+UPLOAD_DIREC+addString;
			dir2 = projectPath + addString;

			byte[] bytes = file.getBytes();
			Path delete = Paths.get(dir);
			Files.delete(delete);
			Path path = Paths.get(dir2);
			Path write = Files.write(path, bytes);
			System.out.println(write);

//			f = dir2;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return dir2;
	}
}
