import { Injectable } from '@angular/core';
import baseUrl from './helper';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class ItemdemoService {

  constructor(private _http: HttpClient) { }
  public getAll() {
    return this._http.get(`${baseUrl}/ncso_i/get-all`);
  }
  public getbyid(Id: any){
    return this._http.get(`${baseUrl}/ncso_i/get-one/${Id}`);
  }
}
