import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from '../../services/api/helper';
import { ApiRequestService } from '../api/api-request.service';
import { Rn_Main_Menu } from '../../models/builder/Rn_Main_Menu';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
@Injectable({
  providedIn: 'root'
})
export class MenuGroupService {
  public key: string ='key-for-data-in-cookies';
  private storage: Storage = sessionStorage;
  constructor(
    private _http: HttpClient,private readonly _cookieService: CookieService,
    private apiRequest: ApiRequestService,
  ) { }

  public getAll(){
    return this._http.get(`${baseUrl}/api/menu-group`);
  }

  public addToDb(header: any){
    return this._http.post(`${baseUrl}/api/menu-group`, header);
  }

  public addLineToDb(line: any){
    return this._http.post(`${baseUrl}/lines_m/create`, line);
  }

  public getOneById(id: any){
    return this._http.get(`${baseUrl}/api/menu-group/${id}`);
  }

  public updateGroupHeader(id: any, data: any){
    return this._http.put(`${baseUrl}/api/menu-group/${id}`, data);
  }

  public updateLineById(id: any){
    return this._http.put(`${baseUrl}/lines_m/update`, id);
  }

  public deleteById(id: any){
    return this._http.delete(`${baseUrl}/api/menu-group/${id}`);
  }
  getByCurrentUserMenuGroupId(): Observable<Rn_Main_Menu[]> {
    const _http1 = "api1/getByUserId"
    return this.apiRequest.get(_http1);
}
getByCurrentUserMenuGroupId1(): Observable<Rn_Main_Menu[]> {
  const _http1 = "api1/submenu1"
  return this.apiRequest.get(_http1);
}
getByCurrentUserMenuGroupId2(): Observable<Rn_Main_Menu[]> {
  const _http1 = "fndMenu/menuloadbyuser"
  return this.apiRequest.get(_http1);
}
private data: string = "data";
public storeaddeditvalues(data:any,) {
   this.storage.setItem(this.data,JSON.stringify (data));
   console.log(this.data);
}
getdata(): any {
  //console.log(this.data);
  var data = JSON.parse(this.storage.getItem(this.data));
  //let data = this.storage.getItem(this.data);
    return data;
}
getuser(id:any){
  const http="chat/getuser";
  return this.apiRequest.post(http,id);
}
public save(text:string):void{
  return this._cookieService.set(this.key,text);
}
}
