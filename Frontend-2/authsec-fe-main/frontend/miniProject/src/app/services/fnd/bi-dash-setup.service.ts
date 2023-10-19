import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { ApiRequestService } from '../api/api-request.service';
import { Observable } from 'rxjs';
import { BiDashHeader } from 'src/app/models/fnd/BiDashHeader';
@Injectable({
  providedIn: 'root'
})
export class BiDashSetupService {
  private storage: Storage = sessionStorage;
  private moduleIdKey: string = "moduleId";
  private dashBaseURL ='api/dashboard-details';
  private saveURL ='api/add-dashboard';
  private baseURL ='api/dashboard';
  constructor(private apiRequest: ApiRequestService) { }

  getModuleId(): number {
    let modId = +this.storage.getItem(this.moduleIdKey);
    return modId;
  }

  getById(id: number) :Observable<BiDashHeader> {
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.get(_http);
}




  getAll(moduleId: number, page?: number, size?: number): Observable<any> {
    // create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    params = params.append("moduleId", moduleId.toString());
    // get all
    return this.apiRequest.get(this.dashBaseURL, params);
  }

  create(biDash: BiDashHeader,moduleId:number) :Observable<BiDashHeader> {
    let params: HttpParams = new HttpParams();
    params = params.append("moduleId", moduleId.toString());
    return this.apiRequest.post(this.saveURL, biDash,params);
}



buildDash(id: number) {
  let params: HttpParams = new HttpParams();
  params = params.append("id", id.toString());
  return this.apiRequest.get("api/build_dashboard", params);
}
}
