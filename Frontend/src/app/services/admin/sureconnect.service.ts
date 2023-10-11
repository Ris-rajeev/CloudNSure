import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import baseUrl from 'src/app/services/api/helper';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class SureconnectService {

  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService,) { }
    public create(data: any){
      return this._http.post(`${baseUrl}/Sure_Connect`, data);
    }

    // create card
    public update(data: any,id:any){
      return this._http.put(`${baseUrl}/Sure_Connect/${id}`, data);
    }

    // get all cards
    public getAll(){
      return this._http.get(`${baseUrl}/Sure_Connect`);
    }

    // get one card
    public getOne(id: any){
      return this._http.get(`${baseUrl}/Sure_Connect/${id}`);
    }

    // delete card
    public delete(id: any){
      return this._http.delete(`${baseUrl}/Sure_Connect/${id}`);
    }
}
