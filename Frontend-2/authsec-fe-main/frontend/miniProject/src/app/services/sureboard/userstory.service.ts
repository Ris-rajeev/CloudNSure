import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from 'rxjs';
import baseUrl from 'src/app/services/api/helper';
import { ApiRequestService } from "src/app/services/api/api-request.service";
@Injectable({
  providedIn: 'root'
})
export class UserstoryService {
  private addDataURl = 'pmUser';

  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService) { }
    getall(){
      return this._http.get(`${baseUrl}/pmUser`);
    }
    getById(id: number): Observable<any> {
      const _http = this.addDataURl + "/" + id;
      return this.apiRequest.get(_http);
    }
    create(data:any,files:any): Observable<any> {
      const s = JSON.stringify(data);
      const formData:FormData = new FormData();
      formData.append("o1",s);
      for(let i=0;i<files.length;i++){
        console.log("here");
        formData.append(files[i].name,files[i]);
      }
      //console.log(formData.get("attachmentFile0"))
      return this.apiRequest.postFormData(this.addDataURl,formData);

     // return this.apiRequest.post(this.addDataURl, data);
      }
      update(id: number, data:any,files:any): Observable<any> {
        const s =JSON.stringify(data);
        const formData:FormData=new FormData();
        formData.append("o1",s);
        for(let i=0;i<files.length;i++){
          console.log("here");
          formData.append(files[i].name,files[i]);
        }
        return this.apiRequest.postFormData(this.addDataURl,formData);

        //const _http = this.updateurl + "/" + id;
        //return this.apiRequest.put(_http, data);
      }
      delete(id: number): Observable<any> {
        const _http = this.addDataURl + "/" + id;
        return this.apiRequest.delete(_http);
      }
}
