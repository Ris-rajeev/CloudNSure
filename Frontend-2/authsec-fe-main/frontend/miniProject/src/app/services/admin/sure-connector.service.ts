import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
//import baseUrl from 'src/app/services/api/helper';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class SureConnectorService {
  baseurl = environment.jobmgurl;
  constructor(private _http: HttpClient,) { }
  public getAll() {
    return this._http.get(`${this.baseurl}/jobpro/get_rn_sureconnetor`);
  }
  public create(data: any){
    return this._http.post(`${this.baseurl}/jobpro/Savern_sureconnetor`, data);
  }

  public delete(id:any){
    return this._http.delete(`${this.baseurl}/jobpro/delete_by_rn_sureconnetor_id/${id}`);
  }

  public update(id:any,data:any){
    return this._http.put(`${this.baseurl}/jobpro/update_rn_sureconnetor/${id}`, data);
  }
}
