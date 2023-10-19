import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from 'rxjs';
import { ApiRequestService } from "src/app/services/api/api-request.service";

@Injectable({
  providedIn: 'root'
})
export class WorkflowDataService {
  private getAllURL = 'work';
  private addDataURl = 'work';
  private editDataURL = 'work';
  private deleteURL ='work';
  private getByIdURL = 'work';
  private updateWorkflowURL = 'updateByid';
 private  fileurl ='/sureops/createfile';
 private callurl='workflow/workflow/callworkflow'
  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService) { }
    getAll(page?: number, size?: number): Observable<any> {
      // create Request URL params
      let me = this;
      let params: HttpParams = new HttpParams();
      params = params.append("page", typeof page === "number" ? page.toString() : "0");
      params = params.append("size", typeof size === "number" ? size.toString() : "1000");
     // params = params.append("module_id", module_id.toString());
      // get all
      return this.apiRequest.get(this.getAllURL, params);
      }
getcallall(){
  return this.apiRequest.get(this.callurl);
}
      create(data:any): Observable<any>
      {
        return this.apiRequest.post(this.addDataURl, data);
      }

      delete(id:number){
        let _http = this.deleteURL + "/" + id;
          return this.apiRequest.delete(_http);
      }

      getById(id:number)
      {
         let _http = this.getByIdURL + "/"+id;
         return this.apiRequest.get(_http);
      }

      updateLineById(id:number,data:any)
      {
        let _http = this.updateWorkflowURL + "/"+id;
        return this.apiRequest.put(_http,data);
      }

      update(id:number,data:any)
      {
          let _http = this.editDataURL + "/"+id;
          return this.apiRequest.put(_http,data);
      }

}
