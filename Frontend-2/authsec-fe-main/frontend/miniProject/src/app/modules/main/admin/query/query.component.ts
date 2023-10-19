import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ReportBuilderService } from 'src/app/services/api/report-builder.service';
import { TableList } from '../../../../models/fnd/table-setup';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.scss']
})
export class QueryComponent implements OnInit {
  searchtable:any;
  searchcol:any;
  columns: any[];
  table:boolean=false;
  database:boolean=false;
  query:boolean=false;
  public entryForm: FormGroup;
  public addForm: FormGroup;
  addmodal:boolean =false;
  query2:string;
  rows:any[];
  loading = false;
  tableList: TableList[];
  databaselist;
  collist;
  search:any;
  selectedtable;
  selectedcol;
  selectedcol1;
  selectedquery;
  searchquery;
  col:boolean=false;
  col1:boolean=false;
  searchdatabase;
  selecteddatabase;
  msg;
  querydata;
  errorco;
  errortb;
  errorcl;
  loadingIndicator = true;  reorderable = true;
  tabs = ['Tab', 'Tab',];
  selected = new FormControl(0);
  constructor( private _fb: FormBuilder,
    private reportBuilderService: ReportBuilderService,private toastr:ToastrService,) { }

  ngOnInit(): void {
    this.entryForm = this._fb.group({
      sql_query: [null],
    });
    this.addForm =this._fb.group({
      conn_string:[null],
      username:[null],
      password:[null],
      driver_class_name:[null],
    })
    //this.listofTables();
  }
listoddatabase(){
  this.reportBuilderService.getdatabse().subscribe((data)=>{
    this.databaselist=data;
    console.log(this.databaselist)
  },(error) => {
    console.log(error);
    if(error){
     this.errorco="No data Available";
   }
  });

}
  listofTables() {
    this.reportBuilderService.getTableListn(this.name[1]).subscribe(data => {
      //console.log("table list "+data);
      this.tableList = data;
      console.log(this.tableList);
    },(error) => {
      console.log(error);
      if(error){
       this.errortb="No data Available";
     }
    })
  }
  listofcol(){
    this.reportBuilderService.getcolListn(this.name[1],this.selectedtable).subscribe((data)=>{
      this.collist=data;
      if(this.selectedtable==null){
        this.msg='Plz First Select Table'
      }

      //console.log(this.collist);
    },(error) => {
      console.log(error);
      if(error){
       this.errorcl="No data Available";
     }
    })
  }
  listofquery(){
    this.reportBuilderService.getall().subscribe((data)=>{
      this.querydata=data;
      console.log(this.querydata)
    })
  }
  rowdata;
  onSubmit(){

    this.query2=this.entryForm.value.sql_query;
    console.log(this.query2);
    this.reportBuilderService.getMasterData(this.query2).subscribe((data) => {
      this.rows = data;
      console.log(this.rows);
this.rowdata= [this.rows];
      console.log(typeof this.rows);
      if(data){
        this.toastr.success("Run Successfully")
      }
       var j;
       var cart = [];

     for(var i = 0; i < data.length; i++)
     {
       var columnsIn = data[i];
       if(i==1)
       {
           for(var key in columnsIn)
          {
           j={prop:key , name: key};
           cart.push(j)

          }
       }
     }
     this.columns = cart;

  });
}
getHeaders() {
  let headers: string[] = [];
  if(this.rows) {
    this.rows.forEach((value) => {
      Object.keys(value).forEach((key) => {
        if(!headers.find((header) => header == key)){
          headers.push(key)
        }

      })

    })
  }
  return headers;
}
savequery(){
  //this.query2=this.entryForm.value.sql_query;
  console.log(this.entryForm.value);
  this.reportBuilderService.saveq(this.entryForm.value).subscribe((data)=>{
    console.log(data);
  })

}

//tab
addTab(selectAfterAdding: boolean) {
  this.tabs.push('Tab');

  if (selectAfterAdding) {
    this.selected.setValue(this.tabs.length - 1);
  }
}

removeTab(index: number) {
  this.tabs.splice(index, 1);
}
opendatabsemo(){
  this.database=true;
  this.listoddatabase();
}
name;
databasename(val){
  console.log(val);
  this.selecteddatabase=val.conn_string;
  console.log(this.selecteddatabase);
  // this.selecteddatabase.substring(0,this.selecteddatabase.indexOf(':3306/'))
  // console.log(this.selecteddatabase);
  this.name=this.selecteddatabase.split(":3306/");
  console.log(this.name[1]);
  this.database=false;
}
opentablemod(){
this.table=true;
this.listofTables();
}
tablename(value){
  console.log(value);
  this.selectedtable=value;
  this.table=false;
}
opentcolmod(){
  this.col=true;
  this.listofcol()
}
opentcolmod1(){
  this.col1=true;
  this.listofcol()
}
colname(col){
  console.log(col);
  this.selectedcol=col;
  this.col=false;
}
colname1(col){
  console.log(col);
  this.selectedcol1=col;
  this.col1=false;
}
openquerymod(){
  this.query=true;
  this.listofquery();
}
selectquery(val){
console.log(val);
this.selectedquery=val;
this.query=false;
}
opencopym(){

  this.addmodal=true;
}
onCreate(){
  console.log(this.addForm.value);
this.reportBuilderService.createdb(this.addForm.value).subscribe((data)=>{
  console.log(data);
})
}
}
