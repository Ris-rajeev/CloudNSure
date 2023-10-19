import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserProfileService } from 'src/app/services/admin/user-profile.service';
import { UserInfoService } from 'src/app/services/user-info.service';
import { Sys_Account } from 'src/app/services/admin/user-registration.service';
import {MyworkspaceService} from '../../../../services/admin/myworkspace.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-myworkspace',
  templateUrl: './myworkspace.component.html',
  styleUrls: ['./myworkspace.component.scss']
})
export class MyworkspaceComponent implements OnInit {
  loading = false;
  selected: any[] = [];
  sys_account: Sys_Account;

  userEmail: string;
  companyName: string;
  workspace: string;
  gstNumber: string;
  modalteam=false;
  modaluser=false;
  modalguest=false;
  allworkmodal=false;
  allworkspacedata;
  alluser;
  allguest;
  allwdata;
 public teamForm: FormGroup;
 public userForm:FormGroup;
 public guestForm:FormGroup;
  public entryForm: FormGroup;
  constructor(private router: Router,
    private route: ActivatedRoute,
    private userProfileService: UserProfileService,
    private userInfoService: UserInfoService,
    private _fb: FormBuilder,
    private toastr:ToastrService,
    private mywork:MyworkspaceService) { }

  ngOnInit(): void {
    //addteam
    this.teamForm = this._fb.group({
      name:[null] ,
        });
//add user
this.userForm=this._fb.group({
  email:[null],
});
//add guest
this.guestForm=this._fb.group({
  email:[null],
  access_duration:[null],
})
    this.getUserAccount();
    this.getUserRoles();
    this.getallmyworkspace();
    this.getalluser();
    this.getallguest();
  }
  getUserAccount() {
    this.userProfileService.getUserAccountDetails().subscribe(resp => {
      this.sys_account = resp;
      console.log("array",this.sys_account);
    }, err => {console.log(err);}
    )
  }


  addUsers() {
    this.modaluser=true;
    //this.router.navigate(["../users"], { relativeTo: this.route });
  }
  manage(id:any){
    this.router.navigate(["../manageteam/"+ id],{relativeTo: this.route});
  }
  goToResetPassword() {
    this.router.navigate(["../passwordreset"], { relativeTo: this.route });
  }

  roles: string[]
  role: string;
  getUserRoles() {
    const role = this.userInfoService.getRoles();
    console.log('roles = ', role);

    // if(role !== null) {
    //   this.roles = role.split(',');
    // }
    this.role = role;
    console.log(this.role);
  }

  isAdmin() :boolean {
    const role: string = this.userInfoService.getRoles();
    if(role.includes('ADMIN')) {
      return true;
    }
    return false;
  }
  addteam(){
    this.modalteam=true;
  }
  addguest(){
    this.modalguest=true;
  }
  getalluser(){
    this.mywork.getalluser().subscribe((data)=>{
      this.alluser=data;
      console.log(this.alluser);
    })
  }
  getallguest(){
    this.mywork.getallguest().subscribe((data)=>{
this.allguest=data;
console.log(this.allguest);
    })
  }
  getallmyworkspace(){
    this.mywork.getall().subscribe((data)=>{
      this.allworkspacedata=data;
      console.log(this.allworkspacedata);
    })
  }

  onCreateuser(){
    let email = this.userForm.value.email;
    console.log(email);
this.mywork.adduser(this.userForm.value.email).subscribe((data)=>{
  this.mywork.storeEmail(email);
  console.log(data);
  if (data) {
    this.toastr.success('User Added successfully');
        }
},(error) => {
  console.log('Error in adding data...',+error);
  if(error.status=200){
    this.toastr.success('User Added');
  }
  // if(error=400){
  //   this.toastr.error('User Already Exist');
  // }
  else{
    this.toastr.error('Not added  Getting Some Error');
  }
});
this.modaluser=false;
  }

  onCreateguest(){
    this.mywork.addguest(this.guestForm.value.email,this.guestForm.value.access_duration).subscribe((data)=>{
      console.log(data);
      if (data) {
        this.toastr.success('Guest Added successfully');
            }
    },(error) => {
      console.log('Error in adding data...',+error);
      if(error.status=200){
        this.toastr.success('Guest Added');
      }
      else{
        this.toastr.error('Not added  Getting Some Error');
      }
    })
    this.modalguest=false;
  }
  oncreateteam(){
    this.mywork.addteam(this.teamForm.value).subscribe((data)=>{
      console.log(data);
      if (data) {
        this.toastr.success('Guest Added successfully');
            }
    },(error) => {
      console.log('Error in adding data...',+error);
      if(error){
        this.toastr.error('Not added  Getting Some Error');
      }
 })
  }
  allworksapce(){
    this.allworkmodal=true;
    this.mywork.getallworkspace().subscribe((data)=>{
      this.allwdata=data;
      console.log(data);
    })
  }
}
