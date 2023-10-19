import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class SuredokrService {
  private baseURL = "http://localhost:3000/dockerapi/images/json";
  constructor( private http:HttpClient) { }
  getAll():Observable<any>{
    return this.http.get(this.baseURL);
  }
}
