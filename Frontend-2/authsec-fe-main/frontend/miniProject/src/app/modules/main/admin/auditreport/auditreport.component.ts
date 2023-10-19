import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuditreportService } from 'src/app/services/admin/auditreport.service';
import { UsermaintanceService } from 'src/app/services/admin/usermaintance.service';
import { OutgoingwebhookService } from 'src/app/services/fnd/outgoingwebhook.service';

@Component({
  selector: 'app-auditreport',
  templateUrl: './auditreport.component.html',
  styleUrls: ['./auditreport.component.scss']
})
export class AuditreportComponent implements OnInit {
  loading = false;
  public entryForm: FormGroup;
  givendata;
  modalAdd= false;
  modaldelete=false;
  rowSelected :any= {};
  error;
  myForm:FormGroup;
  date;
  userdata;
  entitydata;
  selectedfrom;
  selectedto;
  selecteduser;
  selectedentity;
  constructor(private auditservice:AuditreportService,private router: Router,
    private route: ActivatedRoute, private mainservice:UsermaintanceService,
    private toastr: ToastrService,private webhookservice:OutgoingwebhookService,) { }

  ngOnInit(): void {
    this.getall();
this.getalluser();
this.getonentity();
    this.myForm = new FormGroup({
      'presentDate': new FormControl((new Date()).toISOString().substring(0,10)),
    });
    this.date=(new Date()).toISOString().substring(0,10);
    console.log(this.date);

  }
  getall(){
    this.auditservice.getAll().subscribe((data)=>{
      console.log(data);
      this.givendata=data;
    });
  }
  getalluser(){
    this.mainservice.getAll().subscribe((data)=>{
      console.log(data);
this.userdata=data;
    })
  }
  getonentity(){
    this.webhookservice.getallentity().subscribe((data)=>{
      console.log(data);
      this.entitydata=data;
    })

  }
  history(id:any){
    this.router.navigate(["../audithistory/" + id], { relativeTo: this.route });

  }
  modo(val){
    console.log(val);
    this.selecteduser=val;
    this.auditservice.getbyuser(val).subscribe((data)=>{
        console.log(data);
    })
  }
  modo1(val){
    console.log(val);
    this.selectedentity=val;
    this.auditservice.getbyentity(val).subscribe((data)=>{
        console.log(data);
    })
  }
  modo2(val){
    console.log(val);
    this.selectedfrom=val;

  }
  modo3(val){
    console.log(val);
this.selectedto=val;
this.auditservice.onlydate(this.selectedfrom,this.selectedto).subscribe((data)=>{
  console.log(data);
})
  }
  apply(){
   this.auditservice.apply(this.selectedfrom,this.selectedto,this.selecteduser,this.selectedentity).subscribe((data)=>{
    console.log(data);
    this.givendata=data;
   })
  }
  goToAdd(){

  }
  onExport(){

  }
  onSubmit(){

  }
}
