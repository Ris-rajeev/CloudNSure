import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class IncomingwebhookService {

  constructor(private apiRequest: ApiRequestService) { }
  incomegetall(){
    let _http = `api/incoming/getall`;
    return this.apiRequest.get(_http);
  }
 incomepost(data:any){
 let _http=`api/incoming/save`;
 return this.apiRequest.post(_http,data);
  }
  getById(id: number) {
    const _http =`api/incoming/getbyid/${id}`;
    return this.apiRequest.get(_http);
  }
  update(id: number, data:any): Observable<any> {
    const _http =`api/incoming/update/${id}`;
    return this.apiRequest.put(_http,data);
  }
  indelete(id:any){
    let _http=`api/incoming/delete/${id}`;
    return this.apiRequest.delete(_http,);
  }
  generateuserkey(){
    let _http=`api/incoming/genrateuserkey`;
    return this.apiRequest.get(_http);
  }
  generateapikey(){
    let _http=`api/incoming/genrateapikey`;
    return this.apiRequest.get(_http);
  }
  generatetokenkey(){
    let _http=`api/incoming/genratetokenkey`;
    return this.apiRequest.get(_http);
  }
  generateurlkey(){
    let _http=`api/incoming/generateurl`;
    return this.apiRequest.get(_http);
  }
}
