package com.realnet.codeextractor.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.entity.Rule_library_keyword;
import com.realnet.codeextractor.repository.Rule_library_keywordRepository;

@Service
public class Rule_library_service_gk {

	@Value("${projectPath}")
	private String projectPath;
	@Autowired
	private Rn_Bcf_Extractor_Params_Service extractorParamsService;

	@Autowired
	private Rule_library_keywordRepository keywordrepo;

	public String rule(String path, String start, String end, String replaceWith) throws ParseException, IOException {

		StringBuilder frontend = new StringBuilder();

		String finalString = null;
		File staticFile = new File(path);
		String staticFileName = staticFile.getName();

		// System.out.println("========" + staticFileName + "=============");

		String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
		String fileType = FilenameUtils.getExtension(staticFileName);

		fileString.replaceFirst(start, replaceWith);
		// don't check empty file for replacement..
		if (!fileString.isEmpty()) {

			// RULE APPLY
			if (end.isEmpty()) {
				finalString = stringReplacewithout_endstring(fileString, start, replaceWith, fileType);
				System.out.println(finalString.toString());
			} else {
				finalString = stringReplace(fileString, start, end, replaceWith, fileType);
				System.out.println(finalString.toString());

			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(staticFile, false)); // replaced
																						// string
			bw.write(finalString);
			bw.close();

		}

		return frontend.toString();
	}

	// WHEN END STRING IS PRESENT
	public static String stringReplace(String str, String start, String end, String replaceWith, String file_type) {
		int i = str.indexOf(start);
		while (i != -1) {
			int j = str.indexOf(end);
			if (j != -1) {
				/*
				 * @Include starting and ending string String data = str.substring(0, i +
				 * start.length()) + "\n" + replaceWith + "\n"; String temp = str.substring(j);
				 * 
				 * @Not Include starting and ending string String data = str.substring(0, i) +
				 * "\n" + replaceWith + "\n"; String temp = str.substring(j + end.length());
				 */
//					String data = str.substring(0, i+start.length())  +" "+ replaceWith ;
//					String temp = str.substring(j-end.length() + end.length());

				String data = str.substring(0, i) + start + " " + replaceWith + " " + end;
				String temp = str.substring(j + end.length());
				data += temp;
				str = data;
				i = str.indexOf(start, i + replaceWith.length() + end.length() + 1);
			} else {
				break;
			}
		}

		return str;
	}

	// WHEN END STRING IS NOT PERSENT
	public static String stringReplacewithout_endstring(String str, String start, String replaceWith,
			String file_type) {
		int i = str.indexOf(start);
		while (i != -1) {

			/*
			 * @Include starting and ending string String data = str.substring(0, i +
			 * start.length()) + "\n" + replaceWith + "\n"; String temp = str.substring(j);
			 * 
			 * @Not Include starting and ending string String data = str.substring(0, i) +
			 * "\n" + replaceWith + "\n"; String temp = str.substring(j + end.length());
			 */
//					String data = str.substring(0, i+start.length())  +" "+ replaceWith ;
//					String temp = str.substring(j-end.length() + end.length());

			String data = str.substring(0, i) + start + " " + replaceWith;
			str = data;
			i = str.indexOf(start, i + replaceWith.length());

		}

		return str;
	}

	// replace given keyword to replacement string
	public String rulebykeyword(int paramiid, int keywordid, Rn_Bcf_Extractor extractor)
			throws ParseException, IOException {

		Rule_library_keyword getkey = keywordrepo.findById(keywordid).get();
		String keyword = getkey.getKeyword();
		String replace = "";

		// FROM CODE EXTRACTOR
		

			Rn_Bcf_Extractor_Params param = extractorParamsService.getById(paramiid);
			String name_string = param.getName_string();

			String moved_address_string = param.getMoved_address_string(); // in a same folder

			String staticFileName = "SE_".concat(name_string);
			String staticPath = moved_address_string.replace(projectPath, "");
			staticPath = staticPath.substring(0, staticPath.lastIndexOf("/"));
			staticPath = staticPath.concat("/static_code/"); // static code folder
			staticPath = staticPath.concat(staticFileName); // static filde name

			// projectPath
			String path = projectPath.concat(staticPath);

			String replaceWith = getkey.getReplacement_string();

			File staticFile = new File(path);
//			String staticFileName = staticFile.getName();

			String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
//			String fileType = FilenameUtils.getExtension(staticFileName);

			replace = fileString.replaceAll(keyword, replaceWith);
			System.out.println(replace);

			BufferedWriter bw = new BufferedWriter(new FileWriter(staticFile, false)); // replaced
																						// string
			bw.write(replace.toString());
			bw.close();
		
		return replace.toString();

	}

}
