import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import baseUrl from 'src/app/services/api/helper';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class DatastoreService {
  private addDataURl = 'api/suredata';
  private addDataURl1='api/suredatasource';
  constructor(private _http: HttpClient,private apiRequest: ApiRequestService) { }
  public getAll() {
    return this._http.get(`${baseUrl}/api/suredata`);
  }
  public create(data: any){
    return this._http.post(`${baseUrl}/api/suredata`, data);
  }
  create1(data:any,files:any): Observable<any> {
    const s = JSON.stringify(data);
    const formData:FormData = new FormData();
    formData.append("data",s);
    formData.append("ssh_file_key",files);
    // for(let i=0;i<files.length;i++){
    //   console.log("here");
    //   formData.append(files[i].name,files[i]);
    // }
    //console.log(formData.get("attachmentFile0"))
    return this.apiRequest.postFormData(this.addDataURl,formData);

   // return this.apiRequest.post(this.addDataURl, data);
    }
  public getById(id: any){
    return this._http.get(`${baseUrl}/api/suredata/${id}`);
  }
  public delete(id:any){
    return this._http.delete(`${baseUrl}/api/suredata/${id}`);
  }

  public update(id:any,data:any){
    return this._http.put(`${baseUrl}/api/suredata/${id}`, data);
  }
  update1(id: number, data:any,files:any): Observable<any> {
    const s =JSON.stringify(data);
    const formData:FormData=new FormData();
    formData.append("data",s);
    formData.append("ssh_file_key",files);

    return this.apiRequest.postFormData(`${this.addDataURl}/${id}`,formData);


  }
  public getAll2() {
    return this._http.get(`${baseUrl}/api/suredatasource`);
  }

  create2(data:any,files:any): Observable<any> {
    const s = JSON.stringify(data);
    const formData:FormData = new FormData();
    formData.append("data",s);
    formData.append("ssh_file_key",files);
    // for(let i=0;i<files.length;i++){
    //   console.log("here");
    //   formData.append(files[i].name,files[i]);
    // }
    //console.log(formData.get("attachmentFile0"))
    return this.apiRequest.postFormData(this.addDataURl1,formData);

   // return this.apiRequest.post(this.addDataURl, data);
    }
  public getById2(id: any){
    return this._http.get(`${baseUrl}/api/suredatasource/${id}`);
  }
  public delete2(id:any){
    return this._http.delete(`${baseUrl}/api/suredatasource/${id}`);
  }


  update2(id: number, data:any,files:any): Observable<any> {
    const s =JSON.stringify(data);
    const formData:FormData=new FormData();
    formData.append("data",s);
    formData.append("ssh_file_key",files);

    return this.apiRequest.postFormData(`${this.addDataURl1}/${id}`,formData);


  }
}
