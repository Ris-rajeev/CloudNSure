import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';

@Injectable({
  providedIn: 'root'
})
export class OutgoingwebhookService {

  constructor(private apiRequest: ApiRequestService) { }
  getall(){
    let _http = `api/outgoing/getall`;
    return this.apiRequest.get(_http);
  }
  getallentity(){
    let _http = `api/outgoing/getentity`;
    return this.apiRequest.get(_http);
  }
  wall(){
    let _http = `api/webhookdata/getall`;
    return this.apiRequest.get(_http);
  }
 post(data:any){
 let _http=`api/outgoing/save`;
 return this.apiRequest.post(_http,data);
  }
  getById(id: number) {
    const _http =`api/outgoing/getById/${id}`;
    return this.apiRequest.get(_http);
  }
  update(id: number, data:any): Observable<any> {
    const _http =`api/outgoing/update/${id}`;
    return this.apiRequest.put(_http,data);
  }
  update1(id: number, data:any): Observable<any> {
    const _http =`api/outgoing_lines/update/${id}`;
    return this.apiRequest.put(_http,data);
  }
  delete(id:any){
    let _http=`api/outgoing/delete/${id}`;
    return this.apiRequest.delete(_http,);
  }
  getallworkflow(){
    let _http = `token/webhook/Workflow/getall`;
    return this.apiRequest.get(_http);
  }
  getbyidworkflow(id:any){
    let _http = `token/webhook/Workflow/get/${id}`;
    return this.apiRequest.get(_http);
  }
}
