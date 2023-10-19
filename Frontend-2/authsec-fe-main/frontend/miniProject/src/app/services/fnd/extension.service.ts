import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
import { HttpParams} from "@angular/common/http";
import { ExtensionField } from 'src/app/models/fnd/ExtensionField';
@Injectable({
  providedIn: 'root'
})
export class ExtensionService {
  private extensionAPI = 'api/extension';
  private extensionBuildAPI = 'api/extension_build';
  private lookupAPI = 'api/lookup_values';
  private dataTypeAPI = 'api/datatypes';
  constructor(private apiRequest: ApiRequestService) { }

  getAll(page?:number, size?:number): Observable<any> {
    //Create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
    params = params.append('page', typeof page === "number"? page.toString():"0");
    params = params.append('size', typeof size === "number"? size.toString():"1000");
    return this.apiRequest.get(this.extensionAPI, params);

}

getById(id: number) :Observable<ExtensionField> {
    return this.apiRequest.get(this.extensionAPI + '/' + id);

}

create(extensionField: Object) :Observable<ExtensionField> {
    //`${this.baseURL}`
    return this.apiRequest.post(this.extensionAPI, extensionField);

}

update(id: number, extensionField: ExtensionField) :Observable<ExtensionField> {
    return this.apiRequest.put(this.extensionAPI + "/" + id, extensionField);
}

// http://localhost:9119/api/extension_build?account_id=admin&form_code=teacher_form
// build extension
buildExtension(account_id?:any, form_code?:string): Observable<string> {
    let params: HttpParams = new HttpParams();
    params = params.append('account_id', account_id.toString());
    params = params.append('form_code', form_code.toString());
    return this.apiRequest.get(this.extensionBuildAPI, params);
}

// look up code (attribute, flex)
getLookupValues() :Observable<any> {
    return this.apiRequest.get(this.lookupAPI);
}

// datatypes code (string, varchar, int etc)
getDataTypeValues() :Observable<any> {
    return this.apiRequest.get(this.dataTypeAPI);
}
delete(id:any){
  return this.apiRequest.delete(`api/extension/${id}`);
}
}
