import { Injectable } from '@angular/core';
import { ApiRequestService } from '../api/api-request.service';
import { Observable } from 'rxjs';
import { TableList } from 'src/app/models/fnd/TableList';
import { RbColumns } from 'src/app/models/builder/RbColumns';
import { HttpParams } from '@angular/common/http';
import { DropDown } from '../builder/dropdown.service';
@Injectable({
  providedIn: 'root'
})
export class ColumnSetupService {
  private tableListUrl ='api/table-list';
  private baseURL2 ='api/add-allcolumn';
  private columnAPI='api/column_alias_list/'
  constructor(private apiRequest: ApiRequestService) { }
  gettableAlias(id: number): Observable<TableList[]> {
    const _http = this.tableListUrl + "/" + id;
    return this.apiRequest.get(_http);
  }

  create(rb_columns: RbColumns,reportId: number) :Observable<RbColumns> {
    let params: HttpParams = new HttpParams();
    params = params.append("reportId", reportId.toString());
    return this.apiRequest.post(this.baseURL2, rb_columns,params);
}

getColumnList(tableAlias: string): Observable<DropDown[]> {
  const _http = this.columnAPI + tableAlias;
  return this.apiRequest.get(_http);
}
}
