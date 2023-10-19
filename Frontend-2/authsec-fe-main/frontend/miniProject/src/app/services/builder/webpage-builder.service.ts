import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from 'rxjs';
import { ApiRequestService } from "src/app/services/api/api-request.service";
import baseUrl from 'src/app/services/api/helper';
@Injectable({
  providedIn: 'root'
})
export class WebpageBuilderService {

  private getAllURL = 'get_by_module_id';
  private updaterowdata = 'Report_builder';
	private addDataURl = 'Report_builder';
	 private deleteFieldURL = 'Report_builder';
	 private getbyidURL = 'Report_builder';
	// private editURL = 'update_Dashbord1_Line';
   private updateURL = 'Report_line';

  constructor(private _http: HttpClient,
    private apiRequest: ApiRequestService) { }
    getAll(module_id: number,page?: number, size?: number): Observable<any> {
      // create Request URL params
      let me = this;
      let params: HttpParams = new HttpParams();
      params = params.append("page", typeof page === "number" ? page.toString() : "0");
      params = params.append("size", typeof size === "number" ? size.toString() : "1000");
      params = params.append("module_id", module_id.toString());
      // get all
      return this.apiRequest.get(this.getAllURL, params);
      }

      create(data:any): Observable<any> {
      return this.apiRequest.post(this.addDataURl, data);
      }

      deleteField(id:number){
        let _http = this.deleteFieldURL + "/" + id;
          return this.apiRequest.delete(_http);
      }

      getById(id:number)
      {
        let _http = this.getbyidURL + "/" + id;
        return this.apiRequest.get(_http);
      }

      // addToDB(line:any):Observable<any>
      // {
      //  return this.apiRequest.put(this.editURL,line);
      // }
      UpdateLineData(id:number, line:any)
      {
      let _http = this.updateURL + "/" + id;
       return this.apiRequest.put(_http,line);
      }
      UpdateRowData(id:number, line:any)
      {
      let _http = this.updaterowdata + "/" + id;
       return this.apiRequest.put(_http,line);
      }
      uploadImg(image:File): Observable<any>
      {
      const formData = new FormData();
       formData.append('image', image,image.name);
      // return this.apiRequest.post(this.imgUpload, formData);
       return this._http.post(`${baseUrl}/image/upload/image_upload`, formData);
      }
}
