import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from 'src/app/services/api/helper';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class AuditreportService {
private dateurl="audit/betweendate";
  constructor(private _http: HttpClient,private apiRequest: ApiRequestService,) { }
  public getAll() {
    return this._http.get(`${baseUrl}/audit/auditall`);
  }
  public getbyid(id:any) {
    return this._http.get(`${baseUrl}/audit/auditall/${id}`);
  }
  public getbyuser(val:any){
    return this._http.get(`${baseUrl}/audit/listusername/${val}`);
  }
  public getbyentity(val:any){
    return this._http.get(`${baseUrl}/audit/listentityname/${val}`);
  }
  public onlydate(from:any,to:any){
    let params: HttpParams = new HttpParams();
    params =params.append("startDate",from);
    params =params.append("endDate",to);
    return this.apiRequest.get(this.dateurl,params);
  }
  public apply(from:any,to:any,user:any,entity:any){
    let params: HttpParams = new HttpParams();
    params =params.append("startDate",from);
    params =params.append("endDate",to);
    params =params.append("user",user);
    params =params.append("entity_name",entity)
    return this.apiRequest.get(`audit/betweendate_byuser`,params);
    //return this._http.get(`${baseUrl}/audit/betweendate_byuser/${from}`);
  }
}
