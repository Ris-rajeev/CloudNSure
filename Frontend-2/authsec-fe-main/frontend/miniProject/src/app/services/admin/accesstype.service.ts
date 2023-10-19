import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
import baseUrl from 'src/app/services/api/helper';
@Injectable({
  providedIn: 'root'
})
export class AccesstypeService {
  private AllURL = 'access_type/Accesstype';
private moduleurl='_back/Modules_t'
  constructor( private apiRequest: ApiRequestService,private _http: HttpClient,) { }
  getAll(page?: number, size?: number): Observable<any> {
    // create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    return this.apiRequest.get(this.AllURL, params);
    }

    create(data:any): Observable<any> {
    return this.apiRequest.post(this.AllURL, data);
    }

    delete(id: number): Observable<any> {
      const _http = this.AllURL + "/" + id;
      return this.apiRequest.delete(_http);
    }

    update(id: number, data: any): Observable<any> {
      const _http = this.AllURL + "/" + id;
      return this.apiRequest.put(_http, data);
    }
    getAll1(page?: number, size?: number): Observable<any> {
      // create Request URL params
      let me = this;
      let params: HttpParams = new HttpParams();
      params = params.append("page", typeof page === "number" ? page.toString() : "0");
      params = params.append("size", typeof size === "number" ? size.toString() : "1000");
      return this.apiRequest.get(this.moduleurl, params);
      }

      create1(data:any): Observable<any> {
      return this.apiRequest.post(this.moduleurl, data);
      }

      delete1(id: number): Observable<any> {
        const _http = this.moduleurl + "/" + id;
        return this.apiRequest.delete(_http);
      }

      update1(id: number, data: any): Observable<any> {
        const _http = this.moduleurl + "/" + id;
        return this.apiRequest.put(_http, data);
      }
      public getById(id: any){
        return this._http.get(`${baseUrl}/access_type/accessmenu/Accessmenu/${id}`);
      }
      public addById(id:any,data:any){
        return this._http.post(`${baseUrl}/access_type/accessmenu/Accessmenu/${id}`,data);

      }
}
