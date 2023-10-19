import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from '../../services/api/helper';

@Injectable({
  providedIn: 'root'
})
export class UserListService {

  constructor(
    private _http: HttpClient
  ) { }

  public add(user: any){
    return this._http.post(`${baseUrl}/user_list/create`, user);
  }

  public update(user: any){
    return this._http.put(`${baseUrl}/user_list/update`, user);
  }

  public getOne(user: any){
    return this._http.get(`${baseUrl}/user_list/get-one/${user}`);
  }

  public getAll(){
    return this._http.get(`${baseUrl}/user_list/get-all`);
  }

  public deleteById(id: any){
    return this._http.delete(`${baseUrl}/user_list/delete/${id}`);
  }
}
