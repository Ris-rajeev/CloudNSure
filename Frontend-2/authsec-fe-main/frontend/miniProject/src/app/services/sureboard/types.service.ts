import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import baseUrl from 'src/app/services/api/helper';
import { Observable } from "rxjs";
import { ApiRequestService } from "src/app/services/api/api-request.service";
@Injectable({
  providedIn: 'root'
})
export class TypesService {
  private baseURL = "sec_type";
  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService,) { }
  // create
  public create(data: any){
    return this._http.post(`${baseUrl}/Sec_type`, data);
  }

  // update
  public update(id:number,data:any): Observable<any>{
    //const _http = this.baseURL + "/" + id;
    //return this.apiRequest.put(_http, data);
    return this._http.put(`${baseUrl}/Sec_type/${id}`, data);
  }
  // get all
  public getAll(){
    return this._http.get(`${baseUrl}/Sec_type`);
  }

  // get one
  public getOne(Id: any){
    return this._http.get(`${baseUrl}/Sec_type/${Id}`);
  }

  // delete
  public delete(id: any){
    return this._http.delete(`${baseUrl}/Sec_type/${id}`);
  }
}
