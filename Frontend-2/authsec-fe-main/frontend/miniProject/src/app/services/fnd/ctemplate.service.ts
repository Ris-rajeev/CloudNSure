import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class CtemplateService {
 baseurl = environment.communicationurl;
 addDataURl='Surecommunication/communication/jobtable/Com_jobTable';
  constructor(private _http: HttpClient,private apiRequest: ApiRequestService) { }
//template
   public getAll() {
    return this._http.get(`${this.baseurl}/Surecommunication/communication/template/template`);
  }
  public create(data: any){
    return this._http.post(`${this.baseurl}/Surecommunication/communication/template/template`, data);
  }

  public getById(id: any){
    return this._http.get(`${this.baseurl}/Surecommunication/communication/template/template/${id}`);
  }

  public delete(id:any){
    return this._http.delete(`${this.baseurl}/Surecommunication/communication/template/template/${id}`);
  }

  public update(id:any,data:any){
    return this._http.put(`${this.baseurl}/Surecommunication/communication/template/template/${id}`, data);
  }
//jobqueue
public getAll1() {
  return this._http.get(`${this.baseurl}/Surecommunication/communication/jobtable/Com_jobTable`);
}
create1(data:any,file:any): Observable<any> {
  const s = JSON.stringify(data);
  const formData:FormData = new FormData();
  formData.append("data",s);
  formData.append("file",file);
  return this._http.post(`${this.baseurl}/Surecommunication/communication/jobtable/Com_jobTable`,formData);

 // return this.apiRequest.post(this.addDataURl, data);
  }
  update1(id: number, data:any,file:any): Observable<any> {
    const s =JSON.stringify(data);
    const formData:FormData=new FormData();
    formData.append("data",s);
    formData.append("file",file);

    return this._http.put(`${this.baseurl}/Surecommunication/communication/jobtable/Com_jobTable/${id}`,formData);

    //const _http = this.updateurl + "/" + id;
    //return this.apiRequest.put(_http, data);
  }
public getById1(id: any){
  return this._http.get(`${this.baseurl}/Surecommunication/communication/jobtable/Com_jobTable/${id}`);
}

public delete1(id:any){
  return this._http.delete(`${this.baseurl}/Surecommunication/communication/jobtable/Com_jobTable/${id}`);
}


}
