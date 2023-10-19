import { Injectable } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from '../../services/api/api-request.service';
import { HttpParams} from "@angular/common/http";
import { Rn_Forms_Setup } from 'src/app/models/fnd/Rn_Forms_Setup';
@Injectable({
  providedIn: 'root'
})
export class DynamicformService {
  private baseURL = 'api/form_setup';
  private buildDynamicFormURL = 'api/dynamic_form_build';
  constructor( private apiRequest: ApiRequestService,) { }
  getAll(page?:number, size?:number): Observable<any> {
    //Create Request URL params
    let params: HttpParams = new HttpParams();
    params = params.append('page', typeof page === "number"? page.toString():"0");
    params = params.append('size', typeof size === "number"? size.toString():"1000");
    //const _http = this.baseURL + '/all';
    return this.apiRequest.get(this.baseURL, params);

}

getById(id: number) :Observable<Rn_Forms_Setup> {
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.get(_http);
}

create(rn_forms_setup: Rn_Forms_Setup) :Observable<Rn_Forms_Setup> {
    return this.apiRequest.post(this.baseURL, rn_forms_setup);
}

update(id: number, rn_forms_setup: Rn_Forms_Setup) :Observable<Rn_Forms_Setup> {
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.put(_http, rn_forms_setup);
}

buildDynamicForm(form_id?:number): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append('form_id', form_id.toString());
    return this.apiRequest.get(this.buildDynamicFormURL, params);

}
delete(id: number): Observable<any> {
  const _http = this.baseURL + "/" + id;
  return this.apiRequest.delete(_http);
}
}
