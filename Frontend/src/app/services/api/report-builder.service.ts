import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { ApiRequestService } from './api-request.service';
import { Observable } from 'rxjs';
import { ReportBuilder } from '../../models/builder/ReportBuilder';
import { ReportBuilderQuery } from '../../models/builder/ReportBuilderQuery';
import { TableList } from '../../models/fnd/TableList';
@Injectable({
  providedIn: 'root'
})
export class ReportBuilderService {
  private reportBaseURL ='api/report-builder-by-id';
  private reportBaseURLSubmit ='api/report-builder';
  private masterQueryUrl ='api/add-master-query';
  private storage: Storage = sessionStorage;
  private moduleIdKey: string = "moduleId";
  private baseURLMasterQuery="api/master-query";
  private baseURL="api/edit-report";
  private reportbuild2url="api/add-report";
  private  servicereportsave="api/report-builder_service";
  private updatereportservice="api/updatereport";

  private databaselisturl='Table_list'
  private colurl='fndMenu/loadcolumn';
  private saveurl='FndQuery/query';
  constructor(private apiRequest: ApiRequestService) { }
  getById(id: number): Observable<ReportBuilder> {
    const _http = this.baseURL + "/" + id;
    return this.apiRequest.get(_http);
  }

  buildReport(id: number) {
    let params: HttpParams = new HttpParams();
    params = params.append("id", id.toString());
    return this.apiRequest.get("api/build_report", params);
  }

  getModuleId(): number {
    let modId = +this.storage.getItem(this.moduleIdKey);
    return modId;
  }

  getAll(moduleId: number, page?: number, size?: number): Observable<any> {
    // create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    params = params.append("moduleId", moduleId.toString());
    // get all
    return this.apiRequest.get(this.reportBaseURL, params);
  }

  //save report
  create(fbHeader: ReportBuilder, moduleId: number): Observable<ReportBuilder> {
    //`${this.baseURL}`
    let params: HttpParams = new HttpParams();
    params = params.append("moduleId", moduleId.toString());
   // params = params.append("formType", formType);
    return this.apiRequest.post(this.reportBaseURLSubmit, fbHeader, params);
  }

  //save report service
  createservicereport(fbHeader: ReportBuilder, moduleId: number): Observable<ReportBuilder> {
    //`${this.baseURL}`
    let params: HttpParams = new HttpParams();
    params = params.append("moduleId", moduleId.toString());
   // params = params.append("formType", formType);
    return this.apiRequest.post(this.servicereportsave, fbHeader, params);
  }

  //save report
  createQuery(reportId: number): Observable<any> {
    //`${this.baseURL}`
    let params: HttpParams = new HttpParams();
    params = params.append("reportId", reportId.toString());
   // params = params.append("formType", formType);
    return this.apiRequest.post(this.masterQueryUrl,params);
  }

  // update(reportId: number, functionRegister: ReportBuilder): Observable<ReportBuilder> {
  //   let params: HttpParams = new HttpParams();
  //   params = params.append("reportId", reportId.toString());
  //   return this.apiRequest.put(this.masterQueryUrl/reportId, functionRegister);
  // }

  update(id: number, functionRegister: ReportBuilder): Observable<ReportBuilder> {
    const _http = this.masterQueryUrl + "/" + id;
    return this.apiRequest.put(_http, functionRegister);
  }

  updateservicereport(id: number, functionRegister: ReportBuilder): Observable<ReportBuilder> {
    const _http = this.updatereportservice + "/" + id;
    return this.apiRequest.put(_http, functionRegister);
  }

  getMasterQuery(id: number): Observable<ReportBuilderQuery> {
    const _http = this.baseURLMasterQuery + "/" + id;
    return this.apiRequest.get(_http);
  }

  getMasterData(query:string): Observable<any> {
    //Create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
     params = params.append('sql_query', query);
    return this.apiRequest.get('api/master-query-data',params);
}


 report2(servicename):Observable<any>
{
  console.log("in service",servicename);

  // let params: HttpParams = new HttpParams();
  // params = params.append('service_name', servicename);

  // console.log(this.reportbuild2url,params);

  return this.apiRequest.post(this.reportbuild2url,servicename);
}
//savequery
saveq(data:any):Observable<any>{
  return this.apiRequest.post(this.saveurl,data);
}
//getall
getall(){
  return this.apiRequest.get(this.saveurl);
}
getreportdata(apiname):Observable<any>
{
 var url="api"+"/"+apiname;
 console.log(url)

 return this.apiRequest.get(url);

}
getdatabse(): Observable<any>{
  return this.apiRequest.get(`SqlworkbenchSqlcont/sql`);
  //return this.apiRequest.get(this.databaselisturl);
}
getTableListn(val:any): Observable<TableList[]> {
  return this.apiRequest.get(`Table_list/${val}`);
}
getcolListn(val:any,val1:any): Observable<TableList[]> {
  return this.apiRequest.get(`Table_list/${val}/${val1}`);
}
getcollist(table:any){
  const _http = this.colurl+ "/" + table;
  return this.apiRequest.get(_http);
  //return this.apiRequest.get(`this.colurl/${table}`);
}
createdb(data:any){
  return this.apiRequest.post(`SqlworkbenchSqlcont/sql`,data);
}
getallentity(){
  return this.apiRequest.get(this.reportBaseURLSubmit);
}
}
