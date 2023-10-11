import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import baseUrl from '../../services/api/helper';
import { Observable } from "rxjs";
import { Rn_Main_Menu } from "../../models/builder/Rn_Main_Menu";
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class MenumaintanceService {

  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService,) { }
  // create
  public create(data: any){
    return this._http.post(`${baseUrl}/Menu_maintain`, data);
  }

  // update
  public update(id:number,data:any): Observable<any>{
    //const _http = this.baseURL + "/" + id;
    //return this.apiRequest.put(_http, data);
    return this._http.put(`${baseUrl}/Menu_maintain/${id}`, data);
  }
  // get all
  public getAll(){
    return this._http.get(`${baseUrl}/Menu_maintain`);
  }
  getByCurrentUserMenuGroupId1(): Observable<Rn_Main_Menu[]> {
    const _http1 = "api1/submenu1"
    return this.apiRequest.get(_http1);
  }
  // add submenudet table
  // create
  public create1(data: any){
    return this._http.post(`${baseUrl}/api1/Sec_menuDet`, data);
  }
  public create2(data:any){
    return this._http.post(`${baseUrl}/api1/addgrpwithsubmenu`, data)
  }
  // sink
  public sink(id:any,data:any){
    return this._http.put(`${baseUrl}/fndMenu/sink/${id}`,data);
  }
  //get by menuid->submenu
  public getbyid(id:any){
    return this._http.get(`${baseUrl}/api1/submenu1/${id}`);
  }
  //update update by menu_item_id
  public update1(id:number,data:any): Observable<any>{
       return this._http.put(`${baseUrl}/api1/submenu1/${id}`, data);
  }
  public update2(id:number,usrgrp:any,data:any): Observable<any>{
    return this._http.put(`${baseUrl}/api1/update/${id}/${usrgrp}`, data);
}
  //delete
  public delete1(id: any){
    return this._http.delete(`${baseUrl}/api1/menu/${id}`);
  }
  // get one
  public getOne(Id: any){
    return this._http.get(`${baseUrl}/Menu_maintain/${Id}`);
  }

  // delete
  public delete(id: any){
    return this._http.delete(`${baseUrl}/Menu_maintain/${id}`);
  }
}
