import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import{environment} from 'src/environments/environment';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class JobmanagementService {
  baseurl = environment.jobmgurl;
  private updateWorkflowURL = 'updateByid';
  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService) { }
  public getAll() {
    return this._http.get(`${this.baseurl}/sureconnect/getalljob`);
  }

  public create(data: any){
    return this._http.post(`${this.baseurl}/sureconnect/createschedule`, data);
  }
  public update(data: any){
    return this._http.post(`${this.baseurl}/sureconnect/updateschedule`, data);
  }
  public pause(data:any){
    return this._http.post(`${this.baseurl}/sureconnect/pauseschedule`, data);
  }
  public runonce(data:any){
    return this._http.post(`${this.baseurl}/sureconnect/runschedule`, data);
  }
  public resume(data:any){
    return this._http.post(`${this.baseurl}/sureconnect/resumeschedule`, data);
  }
  public delete(val:any){
    return this._http.delete(`${this.baseurl}/sureconnect/deleteschedule/${val}`,);
  }
  updateLineById(id:number,data:any){
    let _http = this.updateWorkflowURL + "/"+id;
    return this.apiRequest.put(_http,data);
  }
}
