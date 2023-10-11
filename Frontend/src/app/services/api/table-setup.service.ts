import { Injectable } from '@angular/core';
import { RbTables } from '../../models/builder/RbTables';
import { Observable } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { HttpParams } from '@angular/common/http';
import { TableList } from '../../models/fnd/TableList';

@Injectable({
  providedIn: 'root'
})
export class TableSetupService {
  private baseURL = 'api/add-tables';
  private baseURL2 = 'api/add-alltables';
  private tableListUrl ='api/table-list';
  private tablelisturl='api/Table_list'
  constructor(private apiRequest: ApiRequestService) { }
  create(rb_tables: RbTables,reportId: number) :Observable<RbTables> {
    let params: HttpParams = new HttpParams();
    params = params.append("reportId", reportId.toString());
    return this.apiRequest.post(this.baseURL2, rb_tables,params);
}

getTableList(): Observable<TableList[]> {
  return this.apiRequest.get(this.tableListUrl);
}
getTableListn(): Observable<TableList[]> {
  return this.apiRequest.get(this.tablelisturl);
}
}
