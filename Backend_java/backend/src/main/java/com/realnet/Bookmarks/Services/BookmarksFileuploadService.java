package com.realnet.Bookmarks.Services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class BookmarksFileuploadService {
	@Value("${projectPath}")
	private String projectPath;

	public String UPLOAD_DIREC = "/Files";

	public boolean uploadFile(MultipartFile multipartFile, String addString) {
		boolean f = false;

		try {

			if (!UPLOAD_DIREC.isEmpty()) {
//				System.out.println("\\" + ":" + "\\\\");

				UPLOAD_DIREC = UPLOAD_DIREC.replaceAll("\\\\", File.separator);
				UPLOAD_DIREC = UPLOAD_DIREC.replaceAll("//", File.separator);
				if (!UPLOAD_DIREC.startsWith(File.separator)) {
					UPLOAD_DIREC = File.separator + UPLOAD_DIREC;
				}
				ArrayList<Object> list = new ArrayList<>();

				String liString = UPLOAD_DIREC;

				int i = 0;
				do {

					int lastIndexOf = liString.lastIndexOf(File.separator);

					String substring = liString.substring(lastIndexOf + 1);
					list.add(substring);

					System.out.println(substring);

					liString = liString.substring(0, lastIndexOf);

					System.out.println("step " + i + " = " + liString);
					i++;

				} while (liString.contains(File.separator));

				for (int j = list.size() - 1; j >= 0; j--) {
					String Path1 = projectPath + File.separator + list.get(j);
					File projectdir = new File(Path1);
					if (!projectdir.exists()) {
						boolean mkdir = projectdir.mkdir();
						System.out.println(Path1 + "  folder create =  " + mkdir);
					}
				}
			}
			// reading data
			InputStream is = multipartFile.getInputStream();
			byte data[] = new byte[is.available()];
			is.read(data);

			// writing data

			FileOutputStream fos = new FileOutputStream(
					projectPath + UPLOAD_DIREC + File.separator + multipartFile.getOriginalFilename() + addString);
			fos.write(data);
			fos.close();
			fos.flush();
			f = true;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return f;
	}

}
