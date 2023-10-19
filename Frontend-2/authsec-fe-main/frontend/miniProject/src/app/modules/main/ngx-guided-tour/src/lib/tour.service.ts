import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ApiRequestService } from 'src/app/services/api/api-request.service';
import { Observable } from 'rxjs';
import baseUrl from 'src/app/services/api/helper';
@Injectable({
  providedIn: 'root'
})
export class TourService {

  constructor(private _http: HttpClient,) { }

  getall(): Observable<any>{
    return this._http.get(`${baseUrl}/Tour/tour`);
  }
  getroadmapall():Observable<any>{
    return this._http.get(`${baseUrl}/tourRoad/roadmap`);
  }
}
