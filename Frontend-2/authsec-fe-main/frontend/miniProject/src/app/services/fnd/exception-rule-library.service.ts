import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bcf_Exception_Rule_Library } from 'src/app/models/fnd/Bcf_Exception_Rule_Library ';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class ExceptionRuleLibraryService {
  private exceptionRuleLibraryBaseURL = 'api/exception-rule-library';
  constructor(private apiRequest: ApiRequestService) { }
  getAll(page?: number, size?: number): Observable<any> {
    //Create Request URL params
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    return this.apiRequest.get(this.exceptionRuleLibraryBaseURL, params);
  }

  getById(id: number): Observable<Bcf_Exception_Rule_Library> {
    const _http = this.exceptionRuleLibraryBaseURL + "/" + id;
    return this.apiRequest.get(_http);
  }

  save(exception_rule: Bcf_Exception_Rule_Library): Observable<Bcf_Exception_Rule_Library> {
    return this.apiRequest.post(this.exceptionRuleLibraryBaseURL, exception_rule);
  }

  update(id: number, exception_rule: Bcf_Exception_Rule_Library): Observable<Bcf_Exception_Rule_Library> {
    const _http = this.exceptionRuleLibraryBaseURL + "/" + id;
    return this.apiRequest.put(_http, exception_rule);
  }
}
