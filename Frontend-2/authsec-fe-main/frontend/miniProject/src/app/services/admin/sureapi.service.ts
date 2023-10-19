import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from 'src/app/services/api/helper';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class SureapiService {
url='sure_connect/sure_postman/call_api'
  constructor(private http:HttpClient,private apiRequest: ApiRequestService,) { }
  createpostman(api_url:any,json_body:any,method: any,token:any){
    console.log(api_url,json_body,method,token)
    let params: HttpParams = new HttpParams();
      params =params.append("api_url",api_url);
     // params =params.append("json_body",json_body);
      params =params.append("method",method);
      params =params.append("token",token)
      return this.http.post(`${baseUrl}/sure_connect/sure_postman/call_api`,json_body,{params:params});
      //return this.apiRequest.post(this.url,{ search:params });


  }
}
