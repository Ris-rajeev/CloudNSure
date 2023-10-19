import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from '../api/helper';
import{environment} from 'src/environments/environment';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class SchedulerService {
  //baseurl='http://localhost:8085';
  baseurl = environment.jobUrl;
  registrationurl = environment.registrationurl;
  sureopsurl = environment.sureops;
  communicationUrl = environment.communicationurl;
  constructor(private _http: HttpClient,private apiRequest: ApiRequestService) { }
  public getAllinfo() {
    return this._http.get(`${this.baseurl}/jobpro/schedule`);
  }
  public getAllsceduler() {
    return this._http.get(`${this.baseurl}/jobpro/getalljob`);
  }
  public createsceduler(data: any){
    return this._http.post(`${this.baseurl}/jobpro/schedule`, data);
  }
  public update(data: any){
    return this._http.post(`${this.baseurl}/jobpro/schedule`, data);
  }
  public pause(data:any){
    return this._http.post(`${this.baseurl}/jobpro/pauseschedule`, data);
  }
  public runonce(data:any){
    return this._http.post(`${this.baseurl}/jobpro/runschedule`, data);
  }
  public resume(data:any){
    return this._http.post(`${this.baseurl}/jobpro/resumeschedule`, data);
  }
  public delete(val:any){
    return this._http.delete(`${this.baseurl}/jobpro/deleteschedule/${val}`,);
  }


  public getAll() {
    return this._http.get(`${this.baseurl}/jobpro/pipiline-completed`);
  }
  public getAllpipe() {
    return this._http.get(`${this.baseurl}/jobpro/getAllPipeline`);
  }

  public createpipe(data: any){
    return this._http.post(`${this.baseurl}/jobpro/addPipeline`, data);
  }
  public updatepipe(data: any){
    return this._http.post(`${this.baseurl}/jobpro/addPipeline`, data);
  }
  public create(data: any){
    return this._http.post(`${this.baseurl}/jobpro/schedule`,data);
  }
  public requ(id:any,data:any){
    return this._http.post(`${this.baseurl}/jobpro/requeue/${id}`,data);
  }
  public getalllogfile() {
    return this._http.get(`${this.baseurl}/jobpro/get_allfile`);
  }
  public createfile(filename:any){
    return this._http.get(`${this.baseurl}/jobpro/createfile/${filename}`);
  }
  public readlogfile(id:any){
    return this._http.get(`${this.baseurl}/jobpro/readfile/${id}`)
  }
  public deletefile(id:any){
    return this._http.delete(`${this.baseurl}/jobpro/deletefile/${id}`);
  }
  public getAllRegistrations(){
    return this._http.get(`${this.registrationurl}/token/getall`);
  }

  public getAllSureOps(): Observable<any>{
    return this._http.get(`${this.sureopsurl}/sureops/get`)
  }

  public getAllCommunication(){
    return this._http.get(`${this.communicationUrl}/Surecommunication/communication/jobtable/Com_jobTable`)
  }

  public getAllwebhook(){
    return this.apiRequest.get('token/webhook/Workflow/web_flagN')
  }
}
