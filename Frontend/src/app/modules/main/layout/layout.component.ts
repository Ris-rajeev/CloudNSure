
import { Component,ViewEncapsulation, OnInit, Input } from '@angular/core';
import { Router,ActivatedRoute } from '@angular/router';
import { LoginService   } from '../../../services/api/login.service';
import { UserInfoService} from '../../../services/user-info.service';
import { RealnetMenuService } from '../../../services/api/realnet-menu.service';
import { Rn_Main_Menu } from '../../../models/builder/Rn_Main_Menu';
import { MenuGroupService } from '../../../services/admin/menu-group.service';
// import { GuidedTour, Orientation} from '../ngx-guided-tour/src/lib/guided-tour.constants';
// import { GuidedTourService } from '../ngx-guided-tour/src/lib/guided-tour.service';
// import { TourService } from '../ngx-guided-tour/src/lib/tour.service';
import { ToastrService } from 'ngx-toastr';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {
  baseUrl= environment.chaturl;
  @Input() data:any;
  collapsed = true;
  public showAppAlert:boolean = false;
modalteam=false;
  public userName: string="";

  private formCode: string ='teacher_form';
  public key:string="formCode";
  public storage:Storage = sessionStorage;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userInfoService:UserInfoService,
    private realnetMenuService: RealnetMenuService,
    private menuGroupService: MenuGroupService,
    // private guidedTourService: GuidedTourService,
    // private tourservice: TourService,
    private toastr: ToastrService,
  ) { this.userName = this.userInfoService.getUserName();
  }
  user_name : any;
  userrole:any;
  menus: Rn_Main_Menu[];
  menu:any;
  tourdata;
  steps;
  error;
  ngOnInit() {
    this.storage.setItem(this.key, this.formCode);
    this.user_name = this.userInfoService.getUserName();
    console.log('user name: ' + this.user_name);
    this.userrole=this.userInfoService.getRoles();
    console.log('user_role',this.userrole);
    this.udata=this.userInfoService.getUserId();
    console.log('user id',this.udata);
    this.loadMenuByAccountId();
    this.loadMenuByMenuGroup();

//     this.tourservice.getall().subscribe((data)=>{
//       this.tourdata=data[1];
//       //console.log("tour data",data);
//       console.log(this.tourdata);
//       //console.log(this.tourdata.tourId);
//       //console.log(this.tourdata.useOrb);
// //this.steps=JSON.stringify(this.tourdata.steps);
//      // console.log(this.tourdata.steps);
//       //this.dashboardTour.tourId=this.tourdata.tourId;
//       //console.log(this.dashboardTour.tourId);
//       //this.dashboardTour.useOrb=this.tourdata.useOrb;
//    // this.dashboardTour.steps=this.tourdata.steps;
//     });
}
// side nav menu-sub_menu
loadMenuByAccountId() {
  this.realnetMenuService.getByAccountId().subscribe(resp => {
          this.menus = resp;
          console.log('menu: ', this.menus);
  });
}

loadMenuByMenuGroup() {
  this.menuGroupService.getByCurrentUserMenuGroupId2().subscribe(resp => {
      this.menus = resp;
      console.log('menus: ', this.menus);
  },(error) => {
    console.log(error);
    if(error){
     this.error="No data Available OR server Error";
   }
   if (error.status === 401) {
    // auto logout if 401 response returned from api
    // this.authenticationService.logout();
    this.toastr.error("Your Token Is Expire Plz login Again")
    //location.reload(true);
}
  })
}


/*  menuGroup: Rn_Menu_Group_Header[];
menu_id: number;
loadMenuGroupData() {
  this.menuGroupService.getAll().subscribe(resp => {
      this.menuGroup = resp;
      this.menu_id = this.menuGroup
  });
} */





navbarSelectionChange(val){
  // console.log(val);
}

closeAppAlert(){
  this.showAppAlert=false;
}

isDisabled(input: string): boolean{
  if(input === null) {
      return true;
  } else false;
}


  onLogout() {
    this.userInfoService.logout().subscribe((data)=>{
      console.log(data);
    });
    sessionStorage.clear();
    this.router.navigate(['login']);
  }
  menuFlag = true;
  menuFlag1 = false;
  udata;
  uid;
  message(){
    //this.menuFlag = false;
   // this.menuFlag1 = true;
// this.menuGroupService.getuser(this.udata).subscribe((data)=>{
//   console.log(data);
// })
window.open(`${this.baseUrl}`)
   //window.location.href = `${this.baseUrl}`;
   //this.udata=this.userInfoService.getUserInfo();
   //console.log(this.udata);

    this.menuGroupService.save(this.udata);

   this.uid=this.udata.userid;
   localStorage.setItem("id", JSON.stringify(this.udata));
   //localStorage.setItem('id', this.uid);
   //window.location.href = (`${this.baseUrl}/`  + this.udata), true
   //this.router.navigate([`${this.baseUrl}`], { relativeTo: this.route ,queryParams: { id: this.udata }});

   //this.router.navigate(["../sureboard"],{relativeTo: this.route, queryParams: { userid: this.udata }});
    //this.router.navigate([`${this.baseUrl}`],{relativeTo: this.route, queryParams: { userid: this.udata }});
    //this.udata = this.userInfoService.getUserInfo();
    //this.userInfoService.storeUserInfo(JSON.stringify(this.udata.user));
  }//

  modaladd(){
    //this.modalteam=true;
    this.router.navigate(['/cns-portal/myworkspace']);
  }
  mc;
  me;
  md;
  mv;
  send(val){
    console.log(val);
    this.menuGroupService.storeaddeditvalues(val);
this.mc=val.mcreate;
this.me=val.medit;
this.md=val.mdelete;
this.mv=val.mvisible;
if(this.mv == 'false'){
  this.router.navigate(['/**'])
}
    this.router.navigate(['./'+ val.main_menu_action_name] , { relativeTo: this.route});
  }
  //skipLocationChange: true, value pass params->,queryParams:{mc:this.mc,me:this.me,md:this.md}
}
