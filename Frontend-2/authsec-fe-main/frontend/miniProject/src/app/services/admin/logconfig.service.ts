import { Injectable } from '@angular/core';
import baseUrl from 'src/app/services/api/helper';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class LogconfigService {

  constructor(private _http: HttpClient,) { }
  public getAll() {
    return this._http.get(`${baseUrl}/log/getAll`);
  }
  public getbyid(Id: any){
    return this._http.get(`${baseUrl}/log/getOne/${Id}`);
  }
  public create(username:any,mode:any){
    return this._http.get(`${baseUrl}/log/startLogging/${username}/${mode}`);
  }
   public delete(username:any){
    return this._http.get(`${baseUrl}/log/stopLogging/${username}`);
  }
  public downloadfile(filename:any){
    return this._http.get(`${baseUrl}/log/downloadLog/${filename}`);
  }
  public readfile(id:any){
    return this._http.get(`${baseUrl}/log2/fileread/${id}`);
  }
}
