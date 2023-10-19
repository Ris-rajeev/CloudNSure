import { Injectable } from '@angular/core';
import { ColumnList } from 'src/app/models/builder/ColumnList';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
import { WhereParam } from 'src/app/models/builder/WhereParam';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WhereParamSetupService {
  private columnListUrl ='api/column-list';
  private baseURL2 ='api/add-where-param';
  constructor(private apiRequest: ApiRequestService) { }
  getColumns(id: number): Observable<ColumnList[]> {
    const _http = this.columnListUrl + "/" + id;
    return this.apiRequest.get(_http);
  }

  create(whereParam: WhereParam,reportId: number) :Observable<WhereParam> {
    let params: HttpParams = new HttpParams();
    params = params.append("reportId", reportId.toString());
    return this.apiRequest.post(this.baseURL2, whereParam,params);
}
}
