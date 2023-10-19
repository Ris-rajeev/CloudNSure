import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
import { DynamicForm } from 'src/app/models/fnd/DynamicForm';
@Injectable({
  providedIn: 'root'
})
export class DynamicTransactionService {
  private baseURL = 'api/dynamic_transaction';
  constructor(private apiRequest: ApiRequestService,) { }
  getAll(page?:number, size?:number): Observable<any> {
    //Create Request URL params
    let params: HttpParams = new HttpParams();
    params = params.append('page', typeof page === "number"? page.toString():"0");
    params = params.append('size', typeof size === "number"? size.toString():"1000");
    const _http = this.baseURL + '/all';
    return this.apiRequest.get(_http, params);
}

// get listof data (GRID)
getByFormId(form_id:number): Observable<any> {
    //Create Request URL params
    let params: HttpParams = new HttpParams();
    params = params.append('form_id',  form_id.toString());
    return this.apiRequest.get(this.baseURL, params);
}

/*   getById(id: number) :Observable<DynamicForm> {
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.get(_http);
} */
//get one data (read-only, update form)
getByIdAndFormId(id: number, form_id:number) :Observable<DynamicForm> {
    let params: HttpParams = new HttpParams();
    params = params.append('form_id',  form_id.toString());
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.get(_http, params);
}




create(dynamicForm: DynamicForm) :Observable<DynamicForm> {
    return this.apiRequest.post(this.baseURL, dynamicForm);
}

/* update(id: number, dynamicForm: DynamicForm) :Observable<DynamicForm> {
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.put(_http, dynamicForm);
} */

updateByIdAndFormId(id: number, form_id: number, dynamicForm: DynamicForm) :Observable<DynamicForm> {
    let params: HttpParams = new HttpParams();
    params = params.append('form_id',  form_id.toString());
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.put(_http, dynamicForm, params);
}
delete(id: number): Observable<any> {
  const _http = this.baseURL + "/" + id;
  return this.apiRequest.delete(_http);
}
}
