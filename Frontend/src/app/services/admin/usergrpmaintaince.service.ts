import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import baseUrl from '../api/helper';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
import { Usergrpmain } from '../../models/admin/usergrpma';
@Injectable({
  providedIn: 'root'
})
export class UsergrpmaintainceService {
  private baseURL = "api/updateOneUsrGrp";
  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService,) { }
  public getAll() {
    return this._http.get(`${baseUrl}/api/getAllUsrGrp`);
  }
  public getbyid(Id: any){
    return this._http.get(`${baseUrl}/api/getOneAppUser/${Id}`);
  }
  public getbyusergrpid(id:any){
    return this._http.get(`${baseUrl}/api1/getusracces1/${id}`);
  }
  public create(data: any){
    return this._http.post(`${baseUrl}/api/addOneUsrGrp`, data);
  }
// update
public update(data: any){
  return this._http.post(`${baseUrl}/api/updateOneUsrGrp`, data);
}
updatei(id: number, projectSetup: Usergrpmain): Observable<any> {
  const _http = this.baseURL + "/" + id;
  return this.apiRequest.put(_http, projectSetup);
}
public getall(){
  return this._http.get(`${baseUrl}/api1/getAllData`);
}
public deleteusr(id:any){
  return this._http.delete(`${baseUrl}/api/delete_usrgrp/${id}`);
}
public delete(id:any,usrgrp:any){
return this._http.delete(`${baseUrl}/api1/deleteGrpMenuAcces/${id}/${usrgrp}`);
}
public deletemain(id:any,usrgrp:any){
  return this._http.delete(`${baseUrl}/fndMenu/DelMenu_WithSub1/${id}/${usrgrp}`);
  }
}
