import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class BuilddetailsService {
  private baseURL = "entityBuilder/project_builder";
  private allpkgurl="token/frontendtable/getall";
  constructor(private apiRequest: ApiRequestService,) { }
  create(data: any): Observable<any> {
    return this.apiRequest.post(this.baseURL, data);
  }
  getall(): Observable<any> {
   return this.apiRequest.get(this.allpkgurl);
  }
}
