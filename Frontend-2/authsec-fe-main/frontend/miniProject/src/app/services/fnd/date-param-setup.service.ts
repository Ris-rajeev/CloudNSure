import { Injectable } from '@angular/core';
import { DateParam } from 'src/app/models/builder/DateParam';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class DateParamSetupService {
  private baseURL2 ='api/add-date-param';
  constructor(private apiRequest: ApiRequestService) { }
  create(dateParam: DateParam,reportId: number) :Observable<DateParam> {
    let params: HttpParams = new HttpParams();
    params = params.append("reportId", reportId.toString());
    return this.apiRequest.post(this.baseURL2, dateParam,params);
}
}
