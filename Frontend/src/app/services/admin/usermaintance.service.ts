import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from 'src/app/services/api/helper';
@Injectable({
  providedIn: 'root'
})
export class UsermaintanceService {

  constructor(private _http: HttpClient,) { }
  public getAll() {
        return this._http.get(`${baseUrl}/api/getAllAppUser`);
      }
      public getbyid(Id: any){
        return this._http.get(`${baseUrl}/api/getOneAppUser/${Id}`);
      }
      public create(data: any){
        return this._http.post(`${baseUrl}/api/addOneAppUser`, data);
      }
    // update
    public update(data: any){
      return this._http.put(`${baseUrl}/api/updateAppUser`, data);
    }
    //newupdate
    public updatenew(id:any,data: any){
      return this._http.put(`${baseUrl}/api/updateAppUserDto/${id}`, data);
    }
    public getallposition(){
      return this._http.get(`${baseUrl}/api/getAllPositions`);
    }
    public getbypositionid(Id:any){
      return this._http.get(`${baseUrl}/api/getPosition/${Id}`);
    }
    public getalldepartment(){
      return this._http.get(`${baseUrl}/api/getAllDepartments`);
    }
    public getbydepartmentid(Id:any){
      return this._http.get(`${baseUrl}/api/getDepartment/${Id}`);
    }
    public deleteusr(id:any){
      return this._http.delete(`${baseUrl}/api/delete_usr/${id}`);
    }
}
