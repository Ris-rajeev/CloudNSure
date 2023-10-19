import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { ApiRequestService } from "../api/api-request.service";
@Injectable({
  providedIn: 'root'
})
export class IconService {
  private addURL = "api/addFavById";
  private removeURL= "api/removeFavById";
  private pinurl ="api/addPinById";
  private pinremove ="api/removePinById";
  private starurl ="api/addStarById";
  private starremove ="api/removeStarById";
  private watchurl ="api/addWatchlistById";
  private watchremove = "api/removeWatchlistById";
  private futureurl = "api/addFuturisticById";
  private futureremove = "api/removeFuturisticById";
  private archurl ="api/addArchiveById";
  constructor(private apiRequest: ApiRequestService,) { }
  create(data:any): Observable<any> {
    return this.apiRequest.post(this.addURL, data);
  }

  delete(id: number): Observable<any> {
    const _http = this.removeURL + "/" + id;
    return this.apiRequest.delete(_http);
  }
  createpin(data:any): Observable<any> {
    return this.apiRequest.post(this.pinurl, data);
  }

  deletepin(id: number): Observable<any> {
    const _http = this.pinremove + "/" + id;
    return this.apiRequest.delete(_http);
  }
  createstar(data:any): Observable<any> {
    return this.apiRequest.post(this. starurl, data);
  }

  deletestar(id: number): Observable<any> {
    const _http = this.starremove + "/" + id;
    return this.apiRequest.delete(_http);
  }
  createwatch(data:any): Observable<any> {
    return this.apiRequest.post(this. watchurl, data);
  }

  deletewatch(id: number): Observable<any> {
    const _http = this.watchremove + "/" + id;
    return this.apiRequest.delete(_http);
  }
  createfuture(data:any): Observable<any> {
    return this.apiRequest.post(this.futureurl, data);
  }

  deletefuture(id: number): Observable<any> {
    const _http = this.futureremove + "/" + id;
    return this.apiRequest.delete(_http);
  }
  createarch(data:any): Observable<any> {
    return this.apiRequest.post(this.archurl, data);
  }
}
