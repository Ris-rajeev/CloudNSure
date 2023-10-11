package com.realnet.Builders;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.realnet.entitybuilder.Services.Index_Service_be;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;
import com.realnet.entitybuilder.response.EntityResponse;
import io.swagger.annotations.Api;
@RestController
@RequestMapping(value = "/token/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = { "Master Builder" })
public class Shital_api_only_line_Builder__1682666429827_1 {


 @Value("${projectPath}")
	private String projectPath;
	@Autowired
	private Index_Service_be index_Service;@GetMapping(value = "/Shital_api_only_line_Builder__1682666429827_1/{proj_id}/{prj_name}/{repo_name}/{table_name}")
	public ResponseEntity<?> createbyjson(@PathVariable Integer proj_id,@PathVariable String prj_name,@PathVariable String repo_name,@PathVariable String table_name,@RequestParam String entityname) throws IOException {
			int j =2;
String copy_from ="";
		String Copy_to_path="";String project_name = repo_name;
		String object_name =table_name;
	entityname = entityname.toString().replace("@", "{").replace("$", "}").replaceAll("\"", "");

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(entityname);
		JsonObject obj = element.getAsJsonObject();
		Set<Entry<String, JsonElement>> entrySet = obj.entrySet();


FileWriter fw = null;
BufferedWriter bw = null;


String Gnyandipta_t0 = object_name +".component.css";

copy_from ="/data/cns-portal/code-extractor/builders/"+proj_id+"/"+proj_id+"/"+repo_name+"/";
		Copy_to_path =
 /Gnyandipta_t.component.css		
		index_Service.build_index(proj_id,prj_name, j, copy_from,Copy_to_path);
j++;


		String Gnyandipta_t1 = object_name +".component.css.map";

copy_from ="/data/cns-portal/code-extractor/builders/"+proj_id+"/"+proj_id+"/"+repo_name+"/";
		Copy_to_path =
 /Gnyandipta_t.component.css.map		
		index_Service.build_index(proj_id,prj_name, j, copy_from,Copy_to_path);
j++;


		String Gnyandipta_t2 = object_name +".component.html";

copy_from ="/data/cns-portal/code-extractor/builders/"+proj_id+"/"+proj_id+"/"+repo_name+"/";
		Copy_to_path =
 /Gnyandipta_t.component.html		
		index_Service.build_index(proj_id,prj_name, j, copy_from,Copy_to_path);
j++;


		String Gnyandipta_t3 = object_name +".component.scss";

copy_from ="/data/cns-portal/code-extractor/builders/"+proj_id+"/"+proj_id+"/"+repo_name+"/";
		Copy_to_path =
 /Gnyandipta_t.component.scss		
		index_Service.build_index(proj_id,prj_name, j, copy_from,Copy_to_path);
j++;


		String Gnyandipta_t4 = object_name +".component.ts";

copy_from ="/data/cns-portal/code-extractor/builders/"+proj_id+"/"+proj_id+"/"+repo_name+"/";
		Copy_to_path =
 /Gnyandipta_t.component.ts		
		index_Service.build_index(proj_id,prj_name, j, copy_from,Copy_to_path);
j++;


		String Gnyandipta_t5 = object_name +".service.ts";

copy_from ="/data/cns-portal/code-extractor/builders/"+proj_id+"/"+proj_id+"/"+repo_name+"/";
		Copy_to_path =
 /Gnyandipta_t.service.ts		
		index_Service.build_index(proj_id,prj_name, j, copy_from,Copy_to_path);
j++;


		
 StringBuilder Gnyandipta_t0Code = new StringBuilder();
 Gnyandipta_t0Code.append("input[type=text], [type=date], select {" + "\r\n" + 
"  width: 100%;" + "\r\n" + 
"  padding: 12px 20px;" + "\r\n" + 
"  margin: 8px 0;" + "\r\n" + 
"  display: inline-block;" + "\r\n" + 
"  border: 1px solid #ccc;" + "\r\n" + 
"  border-radius: 4px;" + "\r\n" + 
"  box-sizing: border-box;" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
".required-field {" + "\r\n" + 
"  color: red;" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
".horizontal {" + "\r\n" + 
"  width: 50%;" + "\r\n" + 
"  padding: 10px;" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
".td-title {" + "\r\n" + 
"  text-align: center;" + "\r\n" + 
"  color: white;" + "\r\n" + 
"  font-weight: bold;" + "\r\n" + 
"  background-color: rgba(63, 122, 231, 0.863);" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
"th {" + "\r\n" + 
"  background-color: rgb(170, 169, 169);" + "\r\n" + 
"  font-weight: bold;" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
".td-content {" + "\r\n" + 
"  text-align: left;" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
".delete, .heading {" + "\r\n" + 
"  text-align: center;" + "\r\n" + 
"  color: red;" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
".section p {" + "\r\n" + 
"  background-color: rgb(206, 201, 201);" + "\r\n" + 
"  padding: 10px;" + "\r\n" + 
"  font-size: 18px;" + "\r\n" + 
"}/*# sourceMappingURL=Gnyandipta_t.component.css.map */" );

	File Gnyandipta_t0File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/" + Gnyandipta_t0);
	System.out.println("Directory name = " + Gnyandipta_t0File);
	File Gnyandipta_t0FileParentDir = new File(Gnyandipta_t0File.getParent());
	if(!Gnyandipta_t0FileParentDir.exists()) {
	Gnyandipta_t0FileParentDir.mkdirs();
			}
	if (!Gnyandipta_t0File.exists()) {
				Gnyandipta_t0File.createNewFile();
			}
			fw = new FileWriter(Gnyandipta_t0File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(Gnyandipta_t0Code.toString());
	bw.close();
	fw.close();


 StringBuilder Gnyandipta_t1Code = new StringBuilder();
 Gnyandipta_t1Code.append("{\"version\":3,\"sources\":[\"Gnyandipta_t.component.scss\",\"Gnyandipta_t.component.css\"],\"names\":[],\"mappings\":\"AAAA;EACE,WAAA;EACA,kBAAA;EACA,aAAA;EACA,qBAAA;EACA,sBAAA;EACA,kBAAA;EACA,sBAAA;ACCF;;ADCA;EACE,UAAA;ACEF;;ADCA;EACE,UAAA;EACA,aAAA;ACEF;;ADCA;EACE,kBAAA;EACF,YAAA;EACE,iBAAA;EACA,2CAAA;ACEF;;ADCA;EACE,oCAAA;EACA,iBAAA;ACEF;;ADAA;EACE,gBAAA;ACGF;;ADDA;EACE,kBAAA;EACA,UAAA;ACIF;;ADFA;EACA,oCAAA;EACE,aAAA;EACA,eAAA;ACKF\",\"file\":\"Gnyandipta_t.component.css\"}" );

	File Gnyandipta_t1File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/" + Gnyandipta_t1);
	System.out.println("Directory name = " + Gnyandipta_t1File);
	File Gnyandipta_t1FileParentDir = new File(Gnyandipta_t1File.getParent());
	if(!Gnyandipta_t1FileParentDir.exists()) {
	Gnyandipta_t1FileParentDir.mkdirs();
			}
	if (!Gnyandipta_t1File.exists()) {
				Gnyandipta_t1File.createNewFile();
			}
			fw = new FileWriter(Gnyandipta_t1File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(Gnyandipta_t1Code.toString());
	bw.close();
	fw.close();


 StringBuilder Gnyandipta_t2Code = new StringBuilder();
 Gnyandipta_t2Code.append("<ol class=\"breadcrumb breadcrumb-arrow font-trirong\">" + "\r\n" + 
"  <li><a href=\"javascript://\"> Gnyandipta_t</a></li>" + "\r\n" + 
"</ol>" + "\r\n" + 
"<div class=\"dg-wrapper\">" + "\r\n" + 
"  <div class=\"clr-row\">" + "\r\n" + 
"    <div class=\"clr-col-8\">" + "\r\n" + 
"      <h3>list </h3>" + "\r\n" + 
"    </div>" + "\r\n" + 
"    <div class=\"clr-col-4\" style=\"text-align: right;\">" + "\r\n" + 
"      <button id=\"add\" class=\"btn btn-primary\" (click)=\"goToAdd(product)\" >" + "\r\n" + 
"        <clr-icon shape=\"plus\"></clr-icon>ADD" + "\r\n" + 
"      </button>" + "\r\n" + 
"    </div></div>" + "\r\n" + 
"  <clr-datagrid [clrDgLoading]=\"loading\" [(clrDgSelected)]=\"selected\">" + "\r\n" + 
"    <clr-dg-placeholder>user not found!</clr-dg-placeholder>" + "\r\n" + 
"    <clr-dg-column [clrDgField]=\"'firstname'\"> <ng-container *clrDgHideableColumn=\"{hidden: false}\">" + "\r\n" + 
"firstname" + "\r\n" + 
"    </ng-container></clr-dg-column>" + "\r\n" + 
"    <clr-dg-column [clrDgField]=\"'lastname'\"> <ng-container *clrDgHideableColumn=\"{hidden: false}\">" + "\r\n" + 
"lastname" + "\r\n" + 
"    </ng-container></clr-dg-column>" + "\r\n" + 
"    <clr-dg-row *clrDgItems=\"let user of product\" [clrDgItem]=\"user\">" + "\r\n" + 
"      <clr-dg-cell>{{user.firstname}}</clr-dg-cell>" + "\r\n" + 
"      <clr-dg-cell>{{user.lastname}}</clr-dg-cell>" + "\r\n" + 
"      <clr-dg-action-overflow>" + "\r\n" + 
"        <button class=\"action-item\" (click)=\"onEdit(user)\">Edit</button>" + "\r\n" + 
"        <button class=\"action-item\" (click)=\"onDelete(user)\">Delete</button>" + "\r\n" + 
"      </clr-dg-action-overflow>" + "\r\n" + 
"         </clr-dg-row>" + "\r\n" + 
"    <clr-dg-footer>" + "\r\n" + 
"      <clr-dg-pagination #pagination [clrDgPageSize]=\"10\">" + "\r\n" + 
"        <clr-dg-page-size [clrPageSizeOptions]=\"[10,20,50,100]\">Users per page</clr-dg-page-size>" + "\r\n" + 
"        {{pagination.firstItem + 1}} - {{pagination.lastItem + 1}}" + "\r\n" + 
"        of {{pagination.totalItems}} users" + "\r\n" + 
"      </clr-dg-pagination>" + "\r\n" + 
"    </clr-dg-footer>" + "\r\n" + 
"  </clr-datagrid>" + "\r\n" + 
"</div>" + "\r\n" + 
"<clr-modal [(clrModalOpen)]=\"modalEdit\" [clrModalSize]=\"'lg'\" [clrModalStaticBackdrop]=\"true\">" + "\r\n" + 
"  <h3 class=\"modal-title\">User Edit</h3>" + "\r\n" + 
"  <div class=\"modal-body\" *ngIf=\"rowSelected.id\">" + "\r\n" + 
"    <h2 class=\"heading\">{{rowSelected.id}}</h2>" + "\r\n" + 
"    <form clrForm (ngSubmit)=\"onUpdate(rowSelected.id)\">" + "\r\n" + 
"      <clr-input-container>" + "\r\n" + 
"        <label>firstname<span class=\"required-field\">*</span></label>" + "\r\n" + 
"        <input id=\"name\" clrInput type=\"text\" [(ngModel)]=\"rowSelected.firstname\" name=\"firstname\" />" + "\r\n" + 
"      </clr-input-container>" + "\r\n" + 
"      <clr-input-container>" + "\r\n" + 
"        <label>lastname<span class=\"required-field\">*</span></label>" + "\r\n" + 
"        <input id=\"name\" clrInput type=\"text\" [(ngModel)]=\"rowSelected.lastname\" name=\"lastname\" />" + "\r\n" + 
"      </clr-input-container>" + "\r\n" + 
"      <div class=\"modal-footer\">" + "\r\n" + 
"      <button type=\"button\" class=\"btn btn-outline\" (click)=\"modalEdit = false\">Cancel</button>" + "\r\n" + 
"      <button type=\"submit\" class=\"btn btn-primary\" >Update</button>" + "\r\n" + 
"      </div>" + "\r\n" + 
"    </form>" + "\r\n" + 
"  </div>" + "\r\n" + 
"</clr-modal>" + "\r\n" + 
"<clr-modal [(clrModalOpen)]=\"modaldelete\" [clrModalSize]=\"'lg'\" [clrModalStaticBackdrop]=\"true\">" + "\r\n" + 
"  <div class=\"modal-body\" *ngIf=\"rowSelected.id\">" + "\r\n" + 
"    <h1 class=\"delete\">Are You Sure Want to delete?</h1>" + "\r\n" + 
"    <h2 class=\"heading\">{{rowSelected.id}}</h2>" + "\r\n" + 
"    <div class=\"modal-footer\">" + "\r\n" + 
"      <button type=\"button\" class=\"btn btn-outline\" (click)=\"modaldelete = false\">Cancel</button>" + "\r\n" + 
"    <button type=\"button\" (click)=\"delete(rowSelected.id)\" class=\"btn btn-primary\" >Delete</button>" + "\r\n" + 
"    </div>" + "\r\n" + 
"  </div>" + "\r\n" + 
"</clr-modal>" + "\r\n" + 
"<clr-modal [(clrModalOpen)]=\"modalAdd\" [clrModalSize]=\"'xl'\" [clrModalStaticBackdrop]=\"true\">" + "\r\n" + 
"  <h3 class=\"modal-title\">ENTRY FORM</h3>" + "\r\n" + 
"  <div class=\"modal-body\">" + "\r\n" + 
"     <form [formGroup]=\"entryForm\" (ngSubmit)=\"onSubmit()\">" + "\r\n" + 
"   <div class=\"clr-row\" style=\"height: fit-content;\">" + "\r\n" + 
"      <div class=\"clr-col-md-3 clr-col-sm-12\" style=\"margin-bottom: 20px;\">" + "\r\n" + 
"          <label> firstname:</label>" + "\r\n" + 
"          <input type=\"text\" clrCheckbox formControlName=\"firstname\" />" + "\r\n" + 
"        </div>" + "\r\n" + 
"      <div class=\"clr-col-md-3 clr-col-sm-12\" style=\"margin-bottom: 20px;\">" + "\r\n" + 
"          <label> lastname:</label>" + "\r\n" + 
"          <input type=\"text\" clrCheckbox formControlName=\"lastname\" />" + "\r\n" + 
"        </div>" + "\r\n" + 
"      </div>" + "\r\n" + 
" <div class=\"modal-footer\">" + "\r\n" + 
"<button type=\"button\" class=\"btn btn-outline\" (click)=\"modalAdd = false\">Cancel</button>" + "\r\n" + 
"        <button type=\"submit\" class=\"btn btn-primary\" >ADD</button>" + "\r\n" + 
"        </div>" + "\r\n" + 
"</form>" + "\r\n" + 
"  </div>" + "\r\n" + 
"</clr-modal>" );

	File Gnyandipta_t2File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/" + Gnyandipta_t2);
	System.out.println("Directory name = " + Gnyandipta_t2File);
	File Gnyandipta_t2FileParentDir = new File(Gnyandipta_t2File.getParent());
	if(!Gnyandipta_t2FileParentDir.exists()) {
	Gnyandipta_t2FileParentDir.mkdirs();
			}
	if (!Gnyandipta_t2File.exists()) {
				Gnyandipta_t2File.createNewFile();
			}
			fw = new FileWriter(Gnyandipta_t2File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(Gnyandipta_t2Code.toString());
	bw.close();
	fw.close();


 StringBuilder Gnyandipta_t3Code = new StringBuilder();
 Gnyandipta_t3Code.append("input[type=text],[type=date], select {" + "\r\n" + 
"  width: 100%;" + "\r\n" + 
"  padding: 12px 20px;" + "\r\n" + 
"  margin: 8px 0;" + "\r\n" + 
"  display: inline-block;" + "\r\n" + 
"  border: 1px solid #ccc;" + "\r\n" + 
"  border-radius: 4px;" + "\r\n" + 
"  box-sizing: border-box;" + "\r\n" + 
"}" + "\r\n" + 
".required-field{" + "\r\n" + 
"  color: red;" + "\r\n" + 
"" + "\r\n" + 
"}" + "\r\n" + 
".horizontal{" + "\r\n" + 
"  width: 50%;" + "\r\n" + 
"  padding: 10px;" + "\r\n" + 
"}" + "\r\n" + 
"" + "\r\n" + 
".td-title {" + "\r\n" + 
"  text-align: center;" + "\r\n" + 
"color: white;" + "\r\n" + 
"  font-weight: bold;" + "\r\n" + 
"  background-color: rgba(63, 122, 231, 0.863);" + "\r\n" + 
"  //color: rgb(24, 13, 13);" + "\r\n" + 
"}" + "\r\n" + 
"th{" + "\r\n" + 
"  background-color:rgb(170, 169, 169);" + "\r\n" + 
"  font-weight: bold;" + "\r\n" + 
"}" + "\r\n" + 
".td-content{" + "\r\n" + 
"  text-align: left;" + "\r\n" + 
"}" + "\r\n" + 
".delete,.heading{" + "\r\n" + 
"  text-align: center;" + "\r\n" + 
"  color: red;" + "\r\n" + 
"}" + "\r\n" + 
".section p {" + "\r\n" + 
"background-color: rgb(206, 201, 201);" + "\r\n" + 
"  padding: 10px;" + "\r\n" + 
"  font-size: 18px;" + "\r\n" + 
"}" );

	File Gnyandipta_t3File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/" + Gnyandipta_t3);
	System.out.println("Directory name = " + Gnyandipta_t3File);
	File Gnyandipta_t3FileParentDir = new File(Gnyandipta_t3File.getParent());
	if(!Gnyandipta_t3FileParentDir.exists()) {
	Gnyandipta_t3FileParentDir.mkdirs();
			}
	if (!Gnyandipta_t3File.exists()) {
				Gnyandipta_t3File.createNewFile();
			}
			fw = new FileWriter(Gnyandipta_t3File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(Gnyandipta_t3Code.toString());
	bw.close();
	fw.close();


 StringBuilder Gnyandipta_t4Code = new StringBuilder();
 Gnyandipta_t4Code.append("import { Component, OnInit } from '@angular/core';" + "\r\n" + 
"import { ToastrService } from 'ngx-toastr';" + "\r\n" + 
"import { AlertService } from 'src/app/services/alert.service';" + "\r\n" + 
"import { Gnyandipta_tservice} from './Gnyandipta_t.service';" + "\r\n" + 
"import { FormArray, FormBuilder, FormGroup } from '@angular/forms';" + "\r\n" + 
"@Component({" + "\r\n" + 
"  selector: 'app-Gnyandipta_t'," + "\r\n" + 
"  templateUrl: './Gnyandipta_t.component.html'," + "\r\n" + 
"  styleUrls: ['./Gnyandipta_t.component.scss']" + "\r\n" + 
"})" + "\r\n" + 
"export class Gnyandipta_tComponent implements OnInit {" + "\r\n" + 
"  rowSelected :any= {};" + "\r\n" + 
"  modaldelete=false;" + "\r\n" + 
"  modalEdit=false;" + "\r\n" + 
"  modalAdd= false;" + "\r\n" + 
"  public entryForm: FormGroup;" + "\r\n" + 
"  loading = false;" + "\r\n" + 
"  product;" + "\r\n" + 
"  modalOpenedforNewLine = false;" + "\r\n" + 
"  newLine:any;" + "\r\n" + 
" selected: any[] = []; constructor(" + "\r\n" + 
"    private mainService:Gnyandipta_tservice," + "\r\n" + 
"    private alertService: AlertService," + "\r\n" + 
"    private toastr: ToastrService," + "\r\n" + 
"    private _fb: FormBuilder," + "\r\n" + 
"  ) { }" + "\r\n" + 
"  ngOnInit(): void {" + "\r\n" + 
"    this.getData();" + "\r\n" + 
"    this.entryForm = this._fb.group({" + "\r\n" + 
"  firstname: [null]," + "\r\n" + 
"  lastname: [null]," + "\r\n" + 
"    });" + "\r\n" + 
"  }" + "\r\n" + 
"  getData() {" + "\r\n" + 
"    this.mainService.getAll().subscribe((data) => {" + "\r\n" + 
"      console.log(data);" + "\r\n" + 
"      this.product = data;" + "\r\n" + 
"     " + "\r\n" + 
"    });" + "\r\n" + 
"  }" + "\r\n" + 
"  onEdit(row) {" + "\r\n" + 
"    this.rowSelected = row;" + "\r\n" + 
"    this.modalEdit = true;" + "\r\n" + 
"  }" + "\r\n" + 
"   onDelete(row) {" + "\r\n" + 
"    this.rowSelected = row;" + "\r\n" + 
"     this.modaldelete=true;" + "\r\n" + 
"  }" + "\r\n" + 
"  delete(id)" + "\r\n" + 
"  {" + "\r\n" + 
"    this.modaldelete = false;" + "\r\n" + 
"    console.log(\"in delete  \"+id);" + "\r\n" + 
"    this.mainService.delete(id).subscribe(" + "\r\n" + 
"      (data) => {" + "\r\n" + 
"        console.log(data);" + "\r\n" + 
"        this.ngOnInit();" + "\r\n" + 
"  if (data) {				      this.toastr.success('Deleted successfully');      }" + "\r\n" + 
"    });" + "\r\n" + 
"  }" + "\r\n" + 
"    onUpdate(id) {" + "\r\n" + 
"      this.modalEdit = false;" + "\r\n" + 
"         //console.log(\"in update\");" + "\r\n" + 
"      console.log(\"id  \"+id);" + "\r\n" + 
"      console.log( this.rowSelected );" + "\r\n" + 
"      //console.log(\"out update\");" + "\r\n" + 
"      this.mainService.update(id,this.rowSelected).subscribe(" + "\r\n" + 
"        (data) => {" + "\r\n" + 
"          console.log(data);" + "\r\n" + 
"        }," + "\r\n" + 
"      );" + "\r\n" + 
"      if (id) {" + "\r\n" + 
"        this.toastr.success('Updated successfully');" + "\r\n" + 
"              }" + "\r\n" + 
"  }" + "\r\n" + 
"  goToAdd(row) {" + "\r\n" + 
"this.modalAdd = true;" + "\r\n" + 
"  }" + "\r\n" + 
"onSubmit() {" + "\r\n" + 
"  console.log(this.entryForm.value);" + "\r\n" + 
"  if (this.entryForm.invalid) {" + "\r\n" + 
"    return;" + "\r\n" + 
"  }" + "\r\n" + 
"  this.onCreate();" + "\r\n" + 
"}" + "\r\n" + 
"onCreate() {" + "\r\n" + 
"     this.modalAdd=false;" + "\r\n" + 
"  this.mainService.create(this.entryForm.value).subscribe(" + "\r\n" + 
"    (data) => {" + "\r\n" + 
"      console.log(data);" + "\r\n" + 
"      this.ngOnInit();" + "\r\n" + 
"    }," + "\r\n" + 
"  );" + "\r\n" + 
"  if (this.entryForm.value) {" + "\r\n" + 
"    this.toastr.success('Added successfully');" + "\r\n" + 
"  }" + "\r\n" + 
"}" + "\r\n" + 
"}" );

	File Gnyandipta_t4File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/" + Gnyandipta_t4);
	System.out.println("Directory name = " + Gnyandipta_t4File);
	File Gnyandipta_t4FileParentDir = new File(Gnyandipta_t4File.getParent());
	if(!Gnyandipta_t4FileParentDir.exists()) {
	Gnyandipta_t4FileParentDir.mkdirs();
			}
	if (!Gnyandipta_t4File.exists()) {
				Gnyandipta_t4File.createNewFile();
			}
			fw = new FileWriter(Gnyandipta_t4File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(Gnyandipta_t4Code.toString());
	bw.close();
	fw.close();


 StringBuilder Gnyandipta_t5Code = new StringBuilder();
 Gnyandipta_t5Code.append("import { Injectable } from '@angular/core';" + "\r\n" + 
"import { HttpParams } from \"@angular/common/http\";" + "\r\n" + 
"import { Observable } from \"rxjs\";" + "\r\n" + 
"import { ApiRequestService } from \"../../../../services/api/api-request.service\";" + "\r\n" + 
"@Injectable({" + "\r\n" + 
"  providedIn: 'root'" + "\r\n" + 
"})" + "\r\n" + 
"export class Gnyandipta_tservice{" + "\r\n" + 
"  private baseURL = \"Gnyandipta/Gnyandipta\" ;  constructor(" + "\r\n" + 
"    private apiRequest: ApiRequestService," + "\r\n" + 
"  ) { }" + "\r\n" + 
"  getAll(page?: number, size?: number): Observable<any> {" + "\r\n" + 
"    return this.apiRequest.get(this.baseURL);" + "\r\n" + 
"  }" + "\r\n" + 
"  getById(id: number): Observable<any> {" + "\r\n" + 
"    const _http = this.baseURL + \"/\" + id;" + "\r\n" + 
"    return this.apiRequest.get(_http);" + "\r\n" + 
"  }" + "\r\n" + 
"  create(data: any): Observable<any> {" + "\r\n" + 
"    return this.apiRequest.post(this.baseURL, data);" + "\r\n" + 
"  }" + "\r\n" + 
"  update(id: number, data: any): Observable<any> {" + "\r\n" + 
"    const _http = this.baseURL + \"/\" + id;" + "\r\n" + 
"    return this.apiRequest.put(_http, data);" + "\r\n" + 
"  }" + "\r\n" + 
"  delete(id: number): Observable<any> {" + "\r\n" + 
"    const _http = this.baseURL + \"/\" + id;" + "\r\n" + 
"    return this.apiRequest.delete(_http);" + "\r\n" + 
"  }" + "\r\n" + 
"}" );

	File Gnyandipta_t5File = new File(projectPath + "/cns-portal/code-extractor/builders/"+ proj_id + "/"+proj_id+ "/" + project_name + "/" + Gnyandipta_t5);
	System.out.println("Directory name = " + Gnyandipta_t5File);
	File Gnyandipta_t5FileParentDir = new File(Gnyandipta_t5File.getParent());
	if(!Gnyandipta_t5FileParentDir.exists()) {
	Gnyandipta_t5FileParentDir.mkdirs();
			}
	if (!Gnyandipta_t5File.exists()) {
				Gnyandipta_t5File.createNewFile();
			}
			fw = new FileWriter(Gnyandipta_t5File.getAbsoluteFile());
	bw = new BufferedWriter(fw);
			bw.write(Gnyandipta_t5Code.toString());
	bw.close();
	fw.close();




	return new ResponseEntity<>(new EntityResponse("Spring boot file created") , HttpStatus.CREATED);
}
 }