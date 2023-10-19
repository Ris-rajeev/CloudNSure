import { Injectable } from '@angular/core';
import baseUrl from 'src/app/services/api/helper';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class ConnectorMappingService {
  private getAllData = 'connector/Connectorjson';
  private baseURL = 'Connectorjson';

  constructor(private _http: HttpClient,) { }
  public getAll() {
    return this._http.get(`${baseUrl}/connector/Connectorjson`);
  }
  public create(data: any){
    return this._http.post(`${baseUrl}/connector/Connectorjson`, data);
  }
  public getById(id: any){
    return this._http.get(`${baseUrl}/connector/Connectorjson/${id}`);
  }
  public delete(id:any){
    return this._http.delete(`${baseUrl}/connector/Connectorjson/${id}`);
  }
  public getkeys(data:any) {
    return this._http.post(`${baseUrl}/token/connector/mapping/mapping`,data);
  }
  public getValues(data:any) {
    return this._http.post(`${baseUrl}/token/connector/mapping/mapping1`,data);
  }
  public getValues1(data:any) {
    return this._http.post(`${baseUrl}/token/connector/mapping/mapping2`,data);
  }
  public update(id:any,data:any){
    return this._http.put(`${baseUrl}/connector/Connectorjson/${id}`, data);
  }
}
