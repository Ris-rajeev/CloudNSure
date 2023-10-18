package com.realnet.entitybuilder.Servie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FrontEndBuilderService {

	@Value("${projectPath}")
	private String projectPath;

	public void buildFrontend(List<String> tablename, List<String> entityname,
			String entity_name_f) throws IOException {

//		Date d = new Date();	
//		String addString = "_" +d.getTime() ;

		String name2 = tablename.get(0);
		String table_name1 = name2.replaceAll(" ", "_");
		String table_name = table_name1.substring(0, 1).toUpperCase() + table_name1.substring(1);


		StringBuilder html = new StringBuilder();
		StringBuilder servie = new StringBuilder();
		StringBuilder scss = new StringBuilder();
		StringBuilder ts = new StringBuilder();

		// MAKE HTML
		html.append("<ol class=\"breadcrumb breadcrumb-arrow font-trirong\">\r\n" + "  <li><a href=\"javascript://\"> "
				+ entity_name_f + "</a></li>\r\n" + "</ol>\r\n" + "<div class=\"dg-wrapper\">\r\n"
				+ "  <div class=\"clr-row\">\r\n" + "    <div class=\"clr-col-8\">\r\n" + "      <h3>list </h3>\r\n"
				+ "    </div>\r\n" + "    <div class=\"clr-col-4\" style=\"text-align: right;\">\r\n"

				+ "      <button id=\"add\" class=\"btn btn-primary\" (click)=\"goToAdd(product)\" >\r\n"
				+ "        <clr-icon shape=\"plus\"></clr-icon>ADD\r\n" + "      </button>\r\n" + "    </div></div>\r\n"
				+ "  <clr-datagrid [clrDgLoading]=\"loading\" [(clrDgSelected)]=\"selected\">\r\n"
				+ "    <clr-dg-placeholder>user not found!</clr-dg-placeholder>\r\n");

		for (int i = 0; i < entityname.size(); i++) {
			String lowerCase = entityname.get(i).replaceAll(" ", "_").toLowerCase();
			html.append("    <clr-dg-column [clrDgField]=\"'" + lowerCase
					+ "'\"> <ng-container *clrDgHideableColumn=\"{hidden: false}\">\r\n" + lowerCase + "\r\n"
					+ "    </ng-container></clr-dg-column>\r\n");
		}

		html.append("    <clr-dg-row *clrDgItems=\"let user of product\" [clrDgItem]=\"user\">\r\n");

		for (int i = 0; i < entityname.size(); i++) {
			String lowerCase = entityname.get(i).replaceAll(" ", "_").toLowerCase();

			html.append("      <clr-dg-cell>{{user." + lowerCase + "}}</clr-dg-cell>\r\n");
		}
		html.append("      <clr-dg-action-overflow>\r\n"
				+ "        <button class=\"action-item\" (click)=\"onEdit(user)\">Edit</button>\r\n"
				+ "        <button class=\"action-item\" (click)=\"onDelete(user)\">Delete</button>\r\n"
				+ "      </clr-dg-action-overflow>\r\n" + "         </clr-dg-row>\r\n" + "    <clr-dg-footer>\r\n"
				+ "      <clr-dg-pagination #pagination [clrDgPageSize]=\"10\">\r\n"
				+ "        <clr-dg-page-size [clrPageSizeOptions]=\"[10,20,50,100]\">Users per page</clr-dg-page-size>\r\n"
				+ "        {{pagination.firstItem + 1}} - {{pagination.lastItem + 1}}\r\n"
				+ "        of {{pagination.totalItems}} users\r\n" + "      </clr-dg-pagination>\r\n"
				+ "    </clr-dg-footer>\r\n" + "  </clr-datagrid>\r\n" + "</div>\r\n"
				+ "<clr-modal [(clrModalOpen)]=\"modalEdit\" [clrModalSize]=\"'lg'\" [clrModalStaticBackdrop]=\"true\">\r\n"
				+ "  <h3 class=\"modal-title\">User Edit</h3>\r\n"
				+ "  <div class=\"modal-body\" *ngIf=\"rowSelected.id\">\r\n"
				+ "    <h2 class=\"heading\">{{rowSelected.id}}</h2>\r\n"
				+ "    <form clrForm (ngSubmit)=\"onUpdate(rowSelected.id)\">\r\n");

		for (int i = 0; i < entityname.size(); i++) {
			String lowerCase = entityname.get(i).replaceAll(" ", "_").toLowerCase();

			html.append("      <clr-input-container>\r\n" + "        <label>" + lowerCase
					+ "<span class=\"required-field\">*</span></label>\r\n"
					+ "        <input id=\"name\" clrInput type=\"text\" [(ngModel)]=\"rowSelected." + lowerCase
					+ "\" name=\"" + lowerCase + "\" />\r\n" + "      </clr-input-container>\r\n");
		}
		html.append("      <div class=\"modal-footer\">\r\n"
				+ "      <button type=\"button\" class=\"btn btn-outline\" (click)=\"modalEdit = false\">Cancel</button>\r\n"
				+ "      <button type=\"submit\" class=\"btn btn-primary\" >Update</button>\r\n" + "      </div>\r\n"
				+ "    </form>\r\n" + "  </div>\r\n" + "</clr-modal>\r\n"
				+ "<clr-modal [(clrModalOpen)]=\"modaldelete\" [clrModalSize]=\"'lg'\" [clrModalStaticBackdrop]=\"true\">\r\n"
				+ "  <div class=\"modal-body\" *ngIf=\"rowSelected.id\">\r\n"
				+ "    <h1 class=\"delete\">Are You Sure Want to delete?</h1>\r\n"
				+ "    <h2 class=\"heading\">{{rowSelected.id}}</h2>\r\n" + "    <div class=\"modal-footer\">\r\n"
				+ "      <button type=\"button\" class=\"btn btn-outline\" (click)=\"modaldelete = false\">Cancel</button>\r\n"
				+ "    <button type=\"button\" (click)=\"delete(rowSelected.id)\" class=\"btn btn-primary\" >Delete</button>\r\n"
				+ "    </div>\r\n" + "  </div>\r\n" + "</clr-modal>\r\n"
				+ "<clr-modal [(clrModalOpen)]=\"modalAdd\" [clrModalSize]=\"'xl'\" [clrModalStaticBackdrop]=\"true\">\r\n"
				+ "  <h3 class=\"modal-title\">ENTRY FORM</h3>\r\n" + "  <div class=\"modal-body\">\r\n"
				+ "     <form [formGroup]=\"entryForm\" (ngSubmit)=\"onSubmit()\">\r\n"
				+ "   <div class=\"clr-row\" style=\"height: fit-content;\">\r\n");

//				List<EntityBuild> ent = table.getEntity_name();
		for (int i = 0; i < entityname.size(); i++) {
			String lowerCase = entityname.get(i).replaceAll(" ", "_").toLowerCase();

			html.append("      <div class=\"clr-col-md-3 clr-col-sm-12\" style=\"margin-bottom: 20px;\">\r\n"
					+ "          <label> " + lowerCase + ":</label>\r\n"
					+ "          <input type=\"text\" clrCheckbox formControlName=\"" + lowerCase + "\" />\r\n"
					+ "        </div>\r\n");
		}

		html.append("      </div>\r\n" + " <div class=\"modal-footer\">\r\n"
				+ "<button type=\"button\" class=\"btn btn-outline\" (click)=\"modalAdd = false\">Cancel</button>\r\n"
				+ "        <button type=\"submit\" class=\"btn btn-primary\" >ADD</button>\r\n" + "        </div>\r\n"
				+ "</form>\r\n" + "  </div>\r\n" + "</clr-modal>");
		System.out.println("html class created");

		// MAKE SCSS

		scss.append("input[type=text],[type=date], select {\r\n" + "  width: 100%;\r\n" + "  padding: 12px 20px;\r\n"
				+ "  margin: 8px 0;\r\n" + "  display: inline-block;\r\n" + "  border: 1px solid #ccc;\r\n"
				+ "  border-radius: 4px;\r\n" + "  box-sizing: border-box;\r\n" + "}\r\n" + ".required-field{\r\n"
				+ "  color: red;\r\n" + "\r\n" + "}\r\n" + ".horizontal{\r\n" + "  width: 50%;\r\n"
				+ "  padding: 10px;\r\n" + "}\r\n" + "\r\n" + ".td-title {\r\n" + "  text-align: center;\r\n"
				+ "color: white;\r\n" + "  font-weight: bold;\r\n"
				+ "  background-color: rgba(63, 122, 231, 0.863);\r\n" + "  //color: rgb(24, 13, 13);\r\n" + "}\r\n"
				+ "th{\r\n" + "  background-color:rgb(170, 169, 169);\r\n" + "  font-weight: bold;\r\n" + "}\r\n"
				+ ".td-content{\r\n" + "  text-align: left;\r\n" + "}\r\n" + ".delete,.heading{\r\n"
				+ "  text-align: center;\r\n" + "  color: red;\r\n" + "}\r\n" + ".section p {\r\n"
				+ "background-color: rgb(206, 201, 201);\r\n" + "  padding: 10px;\r\n" + "  font-size: 18px;\r\n"
				+ "}\r\n" + "");
		System.out.println("scss class created");

		// MAKE TS
		ts.append("import { Component, OnInit } from '@angular/core';\r\n"
				+ "import { ToastrService } from 'ngx-toastr';\r\n"
				+ "import { AlertService } from 'src/app/services/alert.service';\r\n" + "import { " + entity_name_f
				+  "service} from './" + entity_name_f  + ".service" + "';\r\n"

				+ "import { FormArray, FormBuilder, FormGroup } from '@angular/forms';\r\n" + "@Component({\r\n"
				+ "  selector: 'app-" + entity_name_f  + "',\r\n" + "  templateUrl: './" + entity_name_f
				+  ".component.html',\r\n" + "  styleUrls: ['./" + entity_name_f 
				+ ".component.scss']\r\n" + "})\r\n" + "export class " + entity_name_f 
				+ "Component implements OnInit {\r\n" + "  rowSelected :any= {};\r\n" + "  modaldelete=false;\r\n"
				+ "  modalEdit=false;\r\n" + "  modalAdd= false;\r\n" + "  public entryForm: FormGroup;\r\n"
				+ "  loading = false;\r\n" + "  product;\r\n" + "  modalOpenedforNewLine = false;\r\n"
				+ "  newLine:any;\r\n" + " selected: any[] = []; " + "constructor(\r\n" + "    private mainService:"
				+ entity_name_f  + "service,\r\n" + "    private alertService: AlertService,\r\n"
				+ "    private toastr: ToastrService,\r\n" + "    private _fb: FormBuilder,\r\n" + "  ) { }\r\n"
				+ "  ngOnInit(): void {\r\n" + "    this.getData();\r\n" + "    this.entryForm = this._fb.group({\r\n");
		for (int i = 0; i < entityname.size(); i++) {
			String lowerCase = entityname.get(i).replaceAll(" ", "_").toLowerCase();

			ts.append("  " + lowerCase + ": [null],\r\n");
		}
		ts.append("    });\r\n" + "  }\r\n" + "  getData() {\r\n"
				+ "    this.mainService.getAll().subscribe((data) => {\r\n" + "      console.log(data);\r\n"
				+ "      this.product = data;\r\n" + "     \r\n" + "    });\r\n" + "  }\r\n" + "  onEdit(row) {\r\n"
				+ "    this.rowSelected = row;\r\n" + "    this.modalEdit = true;\r\n" + "  }\r\n"
				+ "   onDelete(row) {\r\n" + "    this.rowSelected = row;\r\n" + "     this.modaldelete=true;\r\n"
				+ "  }\r\n" + "  delete(id)\r\n" + "  {\r\n" + "    this.modaldelete = false;\r\n"
				+ "    console.log(\"in delete  \"+id);\r\n" + "    this.mainService.delete(id).subscribe(\r\n"
				+ "      (data) => {\r\n" + "        console.log(data);\r\n" + "        this.ngOnInit();\r\n "
				+ " if (data) {" + "				      this.toastr.success('Deleted successfully');"
				+ "      }\r\n    });\r\n" + "  }\r\n" + "    onUpdate(id) {\r\n" + "      this.modalEdit = false;\r\n"
				+ "         //console.log(\"in update\");\r\n" + "      console.log(\"id  \"+id);\r\n"
				+ "      console.log( this.rowSelected );\r\n" + "      //console.log(\"out update\");\r\n"
				+ "      this.mainService.update(id,this.rowSelected).subscribe(\r\n" + "        (data) => {\r\n"
				+ "          console.log(data);\r\n" + "        },\r\n" + "      );\r\n" + "      if (id) {\r\n"
				+ "        this.toastr.success('Updated successfully');\r\n" + "              }\r\n" + "  }\r\n"

				+ "  goToAdd(row) {\r\n" + "this.modalAdd = true;\r\n" + "  }\r\n" + "onSubmit() {\r\n"
				+ "  console.log(this.entryForm.value);\r\n" + "  if (this.entryForm.invalid) {\r\n" + "    return;\r\n"
				+ "  }\r\n" + "  this.onCreate();\r\n" + "}\r\n" + "onCreate() {\r\n" + "     this.modalAdd=false;\r\n"
				+ "  this.mainService.create(this.entryForm.value).subscribe(\r\n" + "    (data) => {\r\n"
				+ "      console.log(data);\r\n" + "      this.ngOnInit();\r\n" + "    },\r\n" + "  );\r\n"
				+ "  if (this.entryForm.value) {\r\n" + "    this.toastr.success('Added successfully');\r\n" + "  }\r\n"
				+ "}\r\n" + "}");
		System.out.println("ts class created");

		// MAKE SERVICE CLASS
		servie.append("import { Injectable } from '@angular/core';\r\n"
				+ "import { HttpParams } from \"@angular/common/http\";\r\n"
				+ "import { Observable } from \"rxjs\";\r\n"
				+ "import { ApiRequestService } from \"../../../../services/api/api-request.service\";\r\n"

				+ "@Injectable({\r\n" + "  providedIn: 'root'\r\n" + "})\r\n" + "export class " + entity_name_f 
				+ "service" + "{\r\n" + "  private baseURL = " + "\""   + table_name + "/"+table_name+"\" ;"
				+ "  constructor(\r\n" + "    private apiRequest: ApiRequestService,\r\n" + "  ) { }\r\n"
				+ "  getAll(page?: number, size?: number): Observable<any> {\r\n"
				+ "    return this.apiRequest.get(this.baseURL);\r\n" + "  }\r\n"
				+ "  getById(id: number): Observable<any> {\r\n" + "    const _http = this.baseURL + \"/\" + id;\r\n"
				+ "    return this.apiRequest.get(_http);\r\n" + "  }\r\n"
				+ "  create(data: any): Observable<any> {\r\n"
				+ "    return this.apiRequest.post(this.baseURL, data);\r\n" + "  }\r\n"
				+ "  update(id: number, data: any): Observable<any> {\r\n"
				+ "    const _http = this.baseURL + \"/\" + id;\r\n"
				+ "    return this.apiRequest.put(_http, data);\r\n" + "  }\r\n"
				+ "  delete(id: number): Observable<any> {\r\n" + "    const _http = this.baseURL + \"/\" + id;\r\n"
				+ "    return this.apiRequest.delete(_http);\r\n" + "  }\r\n" + "}");
		System.out.println("servie class created");

		// CEATE PACKAGE
		String directoryName = directoryName();

		String Path1 = directoryName + File.separator + "BuilderComponents" + File.separator + entity_name_f 
				+ "front";
		System.out.println(Path1);
		File staticMainDir = new File(Path1);
		if (!staticMainDir.exists()) {
			boolean mkdir = staticMainDir.mkdir();
			System.out.println("frontend folder created = " + mkdir);
		}

		// CREATING html,sccss and ts CLASS
		String Path = Path1 + File.separator + entity_name_f  + ".component.html";
		String servicePath = Path1 + File.separator + entity_name_f + ".service.ts";
		String tspath = Path1 + File.separator + entity_name_f  + ".component.ts";
		String scsspath = Path1 + File.separator + entity_name_f  + ".component.scss";

		System.out.println("component = " + Path);
		System.out.println("service = " + servicePath);

		System.out.println("ts = " + tspath);

		System.out.println("scss = " + scsspath);

//		creating files
		File file0 = new File(Path);
		File file1 = new File(servicePath);

		File file2 = new File(tspath);
		File file3 = new File(scsspath);

		if (!file0.exists()) {
			boolean createNewFile = file0.createNewFile();
			System.out.println("componenet file created = " + createNewFile);

		}
		if (!file1.exists()) {
			boolean createNewFile = file1.createNewFile();
			System.out.println("service file created = " + createNewFile);

		}
		if (!file2.exists()) {
			boolean createNewFile = file2.createNewFile();
			System.out.println("ts file created = " + createNewFile);

		}
		if (!file3.exists()) {
			boolean createNewFile = file3.createNewFile();
			System.out.println("scss file created = " + createNewFile);

		}
//			Writing files

		FileWriter fw = new FileWriter(file0.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(html.toString());
		bw.close();

		FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		bw1.write(servie.toString());
		bw1.close();

		FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
		BufferedWriter bw2 = new BufferedWriter(fw2);
		bw2.write(ts.toString());
		bw2.close();

		FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
		BufferedWriter bw3 = new BufferedWriter(fw3);
		bw3.write(scss.toString());
		bw3.close();

		// routing module.ts
		String rout_import = "import { " + entity_name_f  + "Component } from './BuilderComponents/"
				+ entity_name_f  + "front" + "/" + entity_name_f  + ".component';";
		String rout_path = "{path:'" + entity_name_f  + "',component:" + entity_name_f  + "Component},"
				+ "\n";
		String file_name = "main-routing.module.ts";

		buildfront2(tablename, rout_import, rout_path, file_name);

		// module.ts
		String module_import = "import { " + entity_name_f  + "Component } from './BuilderComponents/"
				+ entity_name_f  + "front" + "/" + entity_name_f  + ".component';";
		String com_name = entity_name_f  + "Component," + "\n";
		String module_file_name = "main.module.ts";

		buildfront2(tablename, module_import, com_name, module_file_name);

	}

	public String directoryName() {

		String frontendpath = File.separator + "frontend" + File.separator + "angular-ui" + File.separator + "src"
				+ File.separator + "app" + File.separator + "modules"+File.separator+"main";

		StringBuilder Dir = new StringBuilder();
//		Properties properties = System.getProperties();

		Dir.append(projectPath);
		System.out.println(projectPath);
		int lastIndexOf = projectPath.lastIndexOf(File.separator);
		String prj_path = projectPath.substring(0, lastIndexOf);
		String new_path = prj_path + frontendpath;

		System.out.println(new_path);

//		StringBuilder directory = new StringBuilder();
//		StringBuilder newDir = new StringBuilder();
//		directory.append(Dir.reverse());
//		int i = 0, c = 0;
//		i = directory.charAt(c);
//		System.out.println(directory.charAt(7) + "  " + i);
//		while (i != 92) {
//			c++;
//			i = directory.charAt(c);
//		}
//
//		newDir.append(directory, c + 1, directory.length());
//		newDir = newDir.reverse();
//		newDir.append(frontendpath);
//
//		System.out.println(newDir);
		return new_path;
	}

	public void buildfront2(List<String> tablename, String r_import, String path, String file_name)
			throws IOException {

		StringBuilder frontend = new StringBuilder();

		String name2 = tablename.get(0);
		String entity_name_f = name2.replaceAll(" ", "_").toLowerCase(); // MAKE HTML

//		frontend.append("import { " + entity_name_f + addString + "Component } from './BuilderComponents/" + entity_name_f + addString
//				 + "/" + entity_name_f + addString + ".component';");

		// CEATE PACKAGE
		String directoryName = directoryName();
		String filePath = directoryName + File.separator + file_name;

		String str = path;

		int length = str.length();

		// CEATE PACKAGE

		File file = null;

		file = new File(filePath);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;

		while ((line = br.readLine()) != null) {
			frontend.append(line + "\n");
			System.out.println(line);

		}
		fr.close();
		br.close();
		System.out.println(frontend.length());

		String back = "// buildercomponents";
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
		PrintWriter out = new PrintWriter(br1);
		out.println(r_import);

		br1.write(codee.toString());
		br1.close();

	}

}
