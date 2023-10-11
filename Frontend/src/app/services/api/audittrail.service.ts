import { Injectable } from '@angular/core';
import baseUrl from './helper';
import { HttpClient } from '@angular/common/http';
import { ApiRequestService } from './api-request.service';
@Injectable({
  providedIn: 'root'
})
export class AudittrailService {
apiurl="master/AuditItemReport";
apiurl1="master/AuditItemReportCustom"
  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService,) { }
  public getAll() {
    return this._http.get(`${baseUrl}/master/AuditItemReport`);

  }
  public gettable(tname:any){
    const formData:FormData = new FormData();
    formData.set("table",tname);
    return this.apiRequest.postFormData(this.apiurl,formData);
  }
  public getdate(d1:any,d2:any,tname:any){
    const formData:FormData = new FormData();
    formData.set("id",null);
    formData.set("d1",d1);
    formData.set("d2",d2);
    formData.set("table",tname);
    return this.apiRequest.postFormData(this.apiurl1,formData);
  }
}
