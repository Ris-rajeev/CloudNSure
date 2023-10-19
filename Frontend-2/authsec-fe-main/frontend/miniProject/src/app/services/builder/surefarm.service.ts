import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class SurefarmService {
  private baseURL = "http://localhost:3000/dockerapi/containers/json?all=1";
  constructor(private http:HttpClient) { }
  getAll():Observable<any>{
    return this.http.get(this.baseURL);
  }
}
