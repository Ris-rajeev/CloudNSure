import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from 'src/app/services/api/helper';
@Injectable({
  providedIn: 'root'
})
export class SessionloggerService {

  constructor(private _http: HttpClient) { }
  public getAll() {
    return this._http.get(`${baseUrl}/user1/session/getAll`);
  }

   public delete(id:any){
    return this._http.delete(`${baseUrl}/user1/session/delete/${id}`);
  }
}
