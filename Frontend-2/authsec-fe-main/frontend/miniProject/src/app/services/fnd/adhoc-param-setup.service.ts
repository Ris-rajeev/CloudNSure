import { Injectable } from '@angular/core';
import { AdhocParam } from 'src/app/models/builder/AdhocParam';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class AdhocParamSetupService {
  private baseURL ='api/add-adhoc-param';
  constructor(private apiRequest: ApiRequestService) { }
  create(adhocParam: AdhocParam,reportId: number) :Observable<AdhocParam> {
    let params: HttpParams = new HttpParams();
    params = params.append("reportId", reportId.toString());
    return this.apiRequest.post(this.baseURL, adhocParam,params);
}
}
