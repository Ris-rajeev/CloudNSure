import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from '../../services/api/helper';

@Injectable({
  providedIn: 'root'
})
export class MenuRegisterService {

  constructor(
    private _http: HttpClient
  ) { }

  public add(r: any){
    return this._http.post(`${baseUrl}/api/menu-register`, r);
  }

  public getd(){
    return this._http.get(`${baseUrl}/api/menu-register`);
  }

  public getById(id: any){
    return this._http.get(`${baseUrl}/api/menu-register/${id}`);
  }

  public update(id:any, reg:any){
    return this._http.put(`${baseUrl}/api/menu-register/${id}`, reg);
  }

  public deleteById(id: any){
    return this._http.delete(`${baseUrl}/api/menu-register/${id}`);
  }
}
