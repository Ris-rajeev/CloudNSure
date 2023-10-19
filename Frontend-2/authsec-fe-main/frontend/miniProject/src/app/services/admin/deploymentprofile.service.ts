import { Injectable } from '@angular/core';
import { ApiRequestService } from '../api/api-request.service';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeploymentprofileService {

  private getAllURL = 'deployment/deplomentprofile';
  addDataURl = 'deployment/deplomentprofile';
  private baseURL = "deployment/deplomentprofile";
  private getallline="deployment/deplomentprofile_line";
  constructor(
    private _http: HttpClient,
    private apiRequest: ApiRequestService
  ) { }

  getAll(page?: number, size?: number): Observable<any> {
    // create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    return this.apiRequest.get(this.getAllURL, params);
    }
getalllines(page?: number, size?: number){
  let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
  return this.apiRequest.get(this.getallline,params);
}
    create(data:any): Observable<any> {
    return this.apiRequest.post(this.addDataURl, data);
    }

    delete(id: number): Observable<any> {
      const _http = this.baseURL + "/" + id;
      return this.apiRequest.delete(_http);
    }

    update(id: number, data: any): Observable<any> {
      const _http = this.baseURL + "/" + id;
      return this.apiRequest.put(_http, data);
    }
}
