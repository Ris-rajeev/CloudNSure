import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { ApiRequestService } from "../api/api-request.service";
import { HttpParams } from "@angular/common/http";
import { HttpClient} from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class SysparameterService {
  private addsysurl='api/addSysParams ';
  private updatesysurl='api/updateSysParams';
  private getbyidurl='api/getSysParams';
  constructor(private apiRequest: ApiRequestService,) { }


  create(data: any): Observable<any> {
    return this.apiRequest.post(this.addsysurl, data);
  }

  update(id: number,data:any,file:any): Observable<any> {
    const s =JSON.stringify(data);
    const formData:FormData=new FormData();
    formData.append("o1",s);
    formData.append(file.name,file);

    const _http = this.updatesysurl + "/" + id;
    return this.apiRequest.put(_http, data);
  }
  getById(id: number): Observable<any> {
    const _http = this.getbyidurl + "/" + id;
    return this.apiRequest.get(_http);
  }
}
