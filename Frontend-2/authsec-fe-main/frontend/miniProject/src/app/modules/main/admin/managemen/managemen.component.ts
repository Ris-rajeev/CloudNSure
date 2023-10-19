import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ExcelService } from '../../../../services/excel.service';
import * as moment from 'moment';
import { MyworkspaceService } from 'src/app/services/admin/myworkspace.service';

@Component({
  selector: 'app-managemen',
  templateUrl: './managemen.component.html',
  styleUrls: ['./managemen.component.scss']
})
export class ManagemenComponent implements OnInit {
  loading = false;
  allteam;
  teamid;
  userid;
  public teamForm: FormGroup;
  public addteamForm:FormGroup;
  modalteam=false;
  allacciduser;
  search;
  constructor(private mywork:MyworkspaceService,
    private toastr:ToastrService,
    private excel: ExcelService,
    private route: ActivatedRoute,
    private _fb: FormBuilder,) { }

  ngOnInit(): void {
    this.teamid = this.route.snapshot.params["id"];
    console.log("update with id = ", this.teamid);
    this.getall(this.teamid);
    this.teamForm = this._fb.group({
      name:[null] ,
        });
        this.addteamForm=this._fb.group({
          team_id: [null],
          member_id: [null],
          member_type:[null],
          access_days: [null],
          access_start_date: [null],
          member_name: [null]
        })
  }

  getall(id){
this.mywork.getallteammeme(this.teamid).subscribe((data)=>{
  this.allteam=data;
  console.log(this.allteam)
})
  }

  goToAdd() {
    this.modalteam=true;
    this.getuserallacid();
    //this.router.navigate(["../add"], { relativeTo: this.route });
  }
  useradd(data){
this.addteamForm.value.team_id=this.teamid;
this.addteamForm.value.member_id=data.userId;
this.addteamForm.value.member_name=data.username;
this.userid=data.userId;
console.log(this.userid);
this.oncreateteam();
this.modalteam=false;
  }
  getuserallacid(){
    this.mywork.getallusertosameaccid().subscribe((data)=>{
      this.allacciduser=data;
      console.log(this.allacciduser);
    })
  }
  oncreateteam(){
    this.mywork.addteammem(this.teamid,this.userid,this.addteamForm.value).subscribe((data)=>{
      console.log(data);
      if (data) {
        this.toastr.success(' Added successfully');
            }
    },(error) => {
      console.log('Error in adding data...',+error);
      if(error){
        this.toastr.error('Not added  Getting Some Error');
      }
 })
  }
  onExport() {
    this.excel.exportAsExcelFile(this.allteam, 'user_',
      moment().format('YYYYMMDD_HHmmss'))
  }
}
