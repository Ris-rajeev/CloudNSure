import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { ApiRequestService } from './api-request.service';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class QueryRunnerService {
  private baseURL = "api/report-data";
  constructor(private apiRequest: ApiRequestService) { }
  getById(report_id:number, page?: number, size?: number): Observable<any> {
    //Create Request URL params
    let params: HttpParams = new HttpParams();
    params = params.append("report_id", report_id.toString());
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    //const _http = this.baseURL + '/all';
    return this.apiRequest.get(this.baseURL, params);
  }
}
