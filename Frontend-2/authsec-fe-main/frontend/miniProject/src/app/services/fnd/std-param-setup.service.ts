import { Injectable } from '@angular/core';
import { StdParam } from 'src/app/models/builder/StdParam';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class StdParamSetupService {
  private baseURL ='api/add-std-param';
  constructor(private apiRequest: ApiRequestService) { }
  create(stdParam: StdParam,reportId: number) :Observable<StdParam> {
    let params: HttpParams = new HttpParams();
    params = params.append("reportId", reportId.toString());
    return this.apiRequest.post(this.baseURL, stdParam,params);
}
}
