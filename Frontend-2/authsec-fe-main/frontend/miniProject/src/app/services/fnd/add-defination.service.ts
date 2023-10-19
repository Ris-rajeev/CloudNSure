import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
import { BiDashLine } from 'src/app/models/fnd/BiDashLine';
@Injectable({
  providedIn: 'root'
})
export class AddDefinationService {
  private baseURL ='api/widget-information';
  private baseupdateURL='api/widget-update';
  constructor(private apiRequest: ApiRequestService) { }
  getById(id: number) :Observable<BiDashLine> {
    const _http = this.baseURL + '/' + id;
    return this.apiRequest.get(_http);
}


update(id: number, biDash: BiDashLine) :Observable<BiDashLine> {
  const _http = this.baseupdateURL + '/' + id;
  return this.apiRequest.put(_http, biDash);
}
}
