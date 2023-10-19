import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuditreportService } from 'src/app/services/admin/auditreport.service';

@Component({
  selector: 'app-audithistory',
  templateUrl: './audithistory.component.html',
  styleUrls: ['./audithistory.component.scss']
})
export class AudithistoryComponent implements OnInit {
id:any;
data:any;
data1;
olddata;
newdata;
  constructor( private route: ActivatedRoute,private auditservice:AuditreportService,) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params["id"];
    console.log("update with id = ", this.id);
    this.getById(this.id);
  }
  getById(id:any){
    this.auditservice.getbyid(id).subscribe((data)=>{
      console.log(data);
      this.data=data;
      this.data1 = JSON.parse(this.data?.json) ;
      console.log(this.data1);
      //this.olddata = this.olddata+ this.data1[0] + " ";
      console.log(this.olddata)
      this.olddata= JSON.stringify(this.data1[0]);
      this.newdata=JSON.stringify(this.data1[1]);
    })
  }
}
