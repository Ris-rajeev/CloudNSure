import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FileData } from 'src/app/models/builder/FileData';
import { environment } from 'src/environments/environment';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class SureopsService {
  private createFileURl = 'sureops/createfile';
  private getfileurl='sureops/getfile/get_allfile';
  private getbyid='sureops/getfile/getfile';
  private updateurl='sureops/fileupdate/updatefile';

  baseurl = environment.cretedevlopmenturl;
  appurl=environment.appbuilderurl;
  constructor(private apiRequest: ApiRequestService,
    private _http: HttpClient,) { }
  public createFile(id: any,profile_id:any) {
    // return this.apiRequest.post(this.createFileURl, id);
    let _http = this.createFileURl + "/" + id +"/"+profile_id;
         return this.apiRequest.get(_http);
   }
   public create(id: any,profile_id:any){
    return this._http.get(`${this.baseurl}/sureops/createfile/${id}/${profile_id}`);
  }
  public delete(profile_id:any,id:any){
    return this._http.delete(`${this.baseurl}/sureops/getfile/delete/${profile_id}/${id}`);
  }
  private scripturl='sureops/script_initilizer'
  public createscript(id: any,profile_id:any,copy_from:any,deployemntProfile:any,commit_msg:any,Sure_filepath:any){
    console.log(id,profile_id,commit_msg,copy_from,deployemntProfile,Sure_filepath)
    let params: HttpParams = new HttpParams();
    params =params.append("copy_from",copy_from);
    params =params.append("Deployment_profile",deployemntProfile);
    params =params.append("commit_msg",commit_msg);
    params =params.append("Sure_filepath",Sure_filepath);
    const _http = this.scripturl + "/" + id +"/"+ profile_id;
    //return this.apiRequest.get(_http,params);
    return this._http.get(`${this.baseurl}/sureops/script_initilizer/${id}/${profile_id}`,{ params: params });
  }
public getallfiles(id:any){
  let _http = this.getfileurl +"/" + id;
  return this.apiRequest.get(_http);
}
public getallfile(id:any,pid:any){
  return this._http.get(`${this.baseurl}/sureops/getfile/get_allfile/${id}/${pid}`);
}
public getbysurepipeproid(pid:any){
  return this._http.get(`${this.baseurl}/sureops/get/${pid}`);
}
public getbypipeid(id:any){
  return this._http.get(`${this.baseurl}/sureops/getby/${id}`);
}
public getbyfileid(id:any){
  let _http = this.getbyid + "/" + id;
         return this.apiRequest.get(_http);
}
public getbyfileeid(id:any){
  return this._http.get(`${this.baseurl}/sureops/getfile/getfile/${id}`);
}
updatefile(id:number,data:any){
  let _http = this.updateurl + "/" + id;
  return this.apiRequest.put(_http,data);
}
public updatebyid(id:any,data:any){
  return this._http.put(`${this.baseurl}/sureops/fileupdate/updatefile/${id}`,data);
}
public build(Id: any){
  //return this.apiRequest.get(`entityBuilder/BuildByProject/${Id}`);
  return this._http.get(`${this.appurl}/entityBuilder/BuildByProject/${Id}`);
}
public deploy(id: any){
  return this._http.get(`${this.baseurl}/sureops/deployapp/${id}`);
}
}
