import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class SureOpsscriptmasterService {
  private URL = 'Sureops_script_api/Sureops_script_apis';
  private url='Sureops_script_api/Sureops_script_line'
  private masterurl='Sureops_script_master/sureops_scriptmaster1'
  constructor(private _http: HttpClient,private apiRequest: ApiRequestService) { }
  getAll(page?: number, size?: number): Observable<any> {
    // create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
   // params = params.append("module_id", module_id.toString());
    // get all
    return this.apiRequest.get(this.URL, params);
    }

    create(data:any): Observable<any>{
      return this.apiRequest.post(this.URL, data);
    }

    delete(id:number){
      let _http = this.URL + "/" + id;
        return this.apiRequest.delete(_http);
    }

    getById(id:number)
    {
       let _http = this.URL + "/"+id;
       return this.apiRequest.get(_http);
    }

    updateLineById(id:number,data:any)
    {
      let _http = this.url + "/"+id;
      return this.apiRequest.put(_http,data);
    }

    update(id:number,data:any)
    {
        let _http = this.URL + "/"+id;
        return this.apiRequest.put(_http,data);
    }

    getall(page?: number, size?: number): Observable<any> {
      // create Request URL params
      let me = this;
      let params: HttpParams = new HttpParams();
      params = params.append("page", typeof page === "number" ? page.toString() : "0");
      params = params.append("size", typeof size === "number" ? size.toString() : "1000");
     // params = params.append("module_id", module_id.toString());
      // get all
      return this.apiRequest.get(this.masterurl, params);
      }

      create1(data:any): Observable<any>{
        return this.apiRequest.post(this.masterurl, data);
      }

      delete1(id:number){
        let _http = this.masterurl + "/" + id;
          return this.apiRequest.delete(_http);
      }

      getById1(id:number)
      {
         let _http = this.masterurl + "/"+id;
         return this.apiRequest.get(_http);
      }



      update1(id:number,data:any)
      {
          let _http = this.masterurl + "/"+id;
          return this.apiRequest.put(_http,data);
      }

}
