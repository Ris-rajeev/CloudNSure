import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ExcelService } from '../../../../services/excel.service';
import * as moment from 'moment';
import {LogconfigService} from '../../../../services/admin/logconfig.service';
import { MenuGroupService } from 'src/app/services/admin/menu-group.service';
import { UserInfoService } from 'src/app/services/user-info.service';
import { ToastrService } from 'ngx-toastr';
import { UsermaintanceService } from 'src/app/services/admin/usermaintance.service';
@Component({
  selector: 'app-logconfig',
  templateUrl: './logconfig.component.html',
  styleUrls: ['./logconfig.component.scss']
})
export class LogconfigComponent implements OnInit {
  loading = false;
  public entryForm: FormGroup;
  givendata;
  modalAdd= false;
  modaldelete=false;
  rowSelected :any= {};
  filename:any;
  username;
  level;
  mcreate;
  mdelete;
  showdata;
  error;
  constructor(private excel: ExcelService,
    private _fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,private userservice:UsermaintanceService,
    private mainservice:LogconfigService,private userInfoService:UserInfoService,
    private menuGroupService: MenuGroupService, private toastr: ToastrService,
    ) { }
    retrievedImage: File;
  base64Data: any;
  retrieveResonse: any;
  user_name;
  userdata1;
  ngOnInit(): void {
    this.user_name = this.userInfoService.getUserName();
    console.log('user name: ' + this.user_name);
  this.showdata = this.menuGroupService.getdata();
  console.log(this.showdata);
    this.mcreate=this.showdata.mcreate;
    console.log(this.mcreate);
    this.mdelete=this.showdata.mdelete
    console.log(this.mdelete);

    this.getdata();
    this.getData();

  }
  getData(){
    this.userservice.getAll().subscribe((data) => {
      console.log(data);
      this.userdata1 = data;
      if(this.userdata1.length==0){
        this.error="No data Available";
        console.log(this.error)
      }

    },(error) => {
      console.log(error);
      if(error){
       this.error="Server Error";
     }
    });
  }
  getdata(){
    this.mainservice.getAll().subscribe((data)=>{
      this.givendata=data;
      console.log(this.givendata);
      if(this.givendata.length==0){
        this.error="No data Available plz add if Required";
        console.log(this.error)
      }
    },(error) => {
      console.log(error);
      if(error){
       this.error="server Error";
     }
    });
    this.entryForm = this._fb.group({
      user:[null] ,
      loglevel:[null] ,

      });

  }
  onExport() {
    this.excel.exportAsExcelFile(this.givendata, 'user_',
      moment().format('YYYYMMDD_HHmmss'))
  }
  goToAdd() {
       this.modalAdd=!this.modalAdd;

      }
      da;
      onSubmit() {
        this.mainservice.create(this.entryForm.value.user,this.entryForm.value.loglevel).subscribe(data=>{
          console.log(data);
          this.da=data;
          this.ngOnInit();
          if(data){
            this.toastr.success(this.da.message)
          }
        },
        (error)=>{
          console.log(error);
        }
        );
        this.modalAdd=false;
      }
      modaldel(row){
        this.rowSelected = row;
        this.modaldelete=true;
      }
      delete(name)
      {
        console.log("in delete  "+name);
        this.mainservice.delete(name).subscribe(
          (data) => {
            console.log(data);
            this.sa=data;
            if(data){
              this.toastr.success(this.sa.message)
            }
            //this.ngOnInit();
          }, );
          this.modaldelete=false;
      }
      download(filename){
        this.mainservice.downloadfile(filename).subscribe((data)=>{
          this.retrieveResonse = data;
          console.log(this.retrieveResonse);
          // this.base64Data = this.retrieveResonse.picByte;
          // this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        })
      }
      start(){
        this.modalAdd=!this.modalAdd;

      }
      sa;
      stop(){
        this.mainservice.delete(this.user_name).subscribe(
          (data) => {
            console.log(data);
            this.sa=data;
            if(data){
              this.toastr.success(this.sa.message)
            }
            //this.ngOnInit();
          }, );
      }
      logid;
      read(data){
console.log(data);
this.logid=data.logId;
this.router.navigate(["/cns-portal/logreader"],{ relativeTo: this.route, queryParams: {id: this.logid } });

      }
}
