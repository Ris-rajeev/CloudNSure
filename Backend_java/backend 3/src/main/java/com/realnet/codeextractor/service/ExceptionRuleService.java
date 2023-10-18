package com.realnet.codeextractor.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.realnet.codeextractor.entity.Rn_Bcf_Extractor;
import com.realnet.codeextractor.entity.Rn_Bcf_Extractor_Params;
import com.realnet.codeextractor.entity.Rule_library_keyword;
import com.realnet.codeextractor.repository.Rule_library_keywordRepository;

@Service
public class ExceptionRuleService {

	@Value("${projectPath}")
	private String projectPath;

	public String replacewithstartandend(String path, String file_name, String startstring, String endstring,
			String replaceWith) throws ParseException, IOException {

		StringBuilder frontend = new StringBuilder();

		String finalString = null;
		path = path + File.separator + file_name;
		File staticFile = new File(path);
		String staticFileName = staticFile.getName();

		// System.out.println("========" + staticFileName + "=============");

		String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
		String fileType = FilenameUtils.getExtension(staticFileName);

		fileString.replaceFirst(startstring, replaceWith);
		// don't check empty file for replacement..
		if (!fileString.isEmpty()) {

			// RULE APPLY
			if (endstring.isEmpty()) {
				finalString = stringReplacewithout_endstring(fileString, startstring, replaceWith, fileType);
				System.out.println(finalString.toString());
			} else {
				finalString = stringReplace(fileString, startstring, endstring, replaceWith, fileType);
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
	public String replacesting(String directorypath, String file_name, String keyword, String replaceWith)
			throws ParseException, IOException {

		String staticPath = directorypath.replace(projectPath, "");
		String path = projectPath.concat(staticPath) + File.separator + file_name;

		File staticFile = new File(path);
//			String staticFileName = staticFile.getName();

		String fileString = FileUtils.readFileToString(staticFile, StandardCharsets.UTF_8);
//			String fileType = FilenameUtils.getExtension(staticFileName);

		String replace = fileString.replaceAll(keyword, replaceWith);
		System.out.println(replace);

		BufferedWriter bw = new BufferedWriter(new FileWriter(staticFile, false)); // replaced
																					// string
		bw.write(replace.toString());
		bw.close();

		return replace.toString();

	}

	public String appendline(String linestring, String appendingline, String directoryName, String file_name)
			throws IOException {

		StringBuilder frontend = new StringBuilder();

		// CEATE PACKAGE

		String filePath = directoryName + File.separator + file_name;
		System.out.println("routing path is " + filePath);

		String str = appendingline;

		int length = str.length();

		// CEATE PACKAGE

		File file = null;

		file = new File(filePath);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;

		while ((line = br.readLine()) != null) {
			frontend.append(line + "\n");
//			System.out.println(line);

		}
		fr.close();
		br.close();
		System.out.println(frontend.length());

//		String back = "// buildercomponents";
		String back = linestring;

		int back_l = back.length();

		int ord = StringUtils.ordinalIndexOf(frontend.toString(), back, 1);

		String front = frontend.substring(0, ord + back_l) + "\n" + str + "\n"
				+ frontend.substring(ord + back_l, frontend.length());

		String codee = front.substring(0, front.lastIndexOf("\n")); // remove last line break

//		creating files
		File file1 = new File(filePath);

//		Writing files

		FileWriter fr1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter br1 = new BufferedWriter(fr1);
//		PrintWriter out = new PrintWriter(br1);
//		out.println(r_import);

		br1.write(codee.toString());
		br1.close();
		return codee;

	}

	public String linereplacement(String filepath, String file_name, String keyword, String appendline)
			throws IOException {

		StringBuilder frontend = new StringBuilder();

		String filePath = filepath + File.separator + file_name;
		System.out.println("routing path is " + filePath);

		String str = appendline;
		int length = str.length();

		File file = null;

		file = new File(filePath);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;

		while ((line = br.readLine()) != null) {
			if (line.contains(keyword)) {
				String replaceline = line.replace(line, appendline);
				frontend.append(replaceline + "\n");

			} else {
				frontend.append(line + "\n");

			}
//			System.out.println(line);

		}
		fr.close();
		br.close();
		System.out.println(frontend.length());

		String codee = frontend.substring(0, frontend.lastIndexOf("\n")); // remove last line break

//		creating files
		File file1 = new File(filePath);

//		Writing files

		FileWriter fr1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter br1 = new BufferedWriter(fr1);

		br1.write(codee.toString());
		br1.close();

		return codee;

	}

}
