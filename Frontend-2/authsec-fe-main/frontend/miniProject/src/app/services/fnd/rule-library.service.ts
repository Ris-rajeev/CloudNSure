import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Bcf_Rule_Library } from 'src/app/models/fnd/Bcf_Rule_Library';
import { RuleCopy, Rule } from 'src/app/models/fnd/RuleCopy';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class RuleLibraryService {
  private ruleLibraryBaseURL = 'code_extractor/rule_keyword/Rule_library_keyword';
  private copyRuleURL = 'api/rule-copy';
  private keywordurl='codeextractor/rulelib_new/rule_keyword1';
  private filedurl='codeextractor/rulelib_new/rule_keyword';
  private copyURL = 'code_extractor/rule_keyword/copyrule'
  constructor(private apiRequest: ApiRequestService) { }

  copy(data: RuleCopy) {
    return this.apiRequest.post(this.copyRuleURL, data);
  }

  getAll(page?: number, size?: number): Observable<any> {
    //Create Request URL params
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    return this.apiRequest.get(this.ruleLibraryBaseURL, params);
  }

  getById(id: number): Observable<Bcf_Rule_Library> {
    const _http = this.ruleLibraryBaseURL + "/" + id;
    return this.apiRequest.get(_http);
  }

  save(rule: Bcf_Rule_Library): Observable<Bcf_Rule_Library> {
    return this.apiRequest.post(this.ruleLibraryBaseURL, rule);
  }

  update(id: number, rule: Bcf_Rule_Library): Observable<Bcf_Rule_Library> {
    const _http = this.ruleLibraryBaseURL + "/" + id;
    return this.apiRequest.put(_http, rule);
  }
  delete(id: number): Observable<any> {
    const _http = this.ruleLibraryBaseURL + "/" + id;
    return this.apiRequest.delete(_http);
  }
  keyword(id: number,keyword: any): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append("id", id.toString());
    params= params.append("keyword",keyword);
    return this.apiRequest.get(this.keywordurl, params);
  }

  insertfile(id:any,data:any,keyword:any,){
    let params: HttpParams = new HttpParams();
    params = params.append("id", id.toString());
    params= params.append("selectedarea",data);
    params= params.append("keyword",keyword);
    console.log(params);
    return this.apiRequest.get(this.filedurl, params);
  }

  copyRule(technologyStack: string, objectType: string, subObjectType: string, service: string, newTechStackName: string, rule: Rule){
    let params: HttpParams = new HttpParams();
      params =params.append("technology_stack",technologyStack);
      params =params.append("object_type",objectType);
      params =params.append("sub_object_type",subObjectType);
      params =params.append("service",service),
      params=params.append("new_tech_stack_name",newTechStackName)
      return this.apiRequest.post(this.copyURL,rule,params);
      //return this.apiRequest.post(this.copydeployurl,params);
    //return this.http.get(`${baseUrl}/api/getmodulename`,params);
  
  }
}
