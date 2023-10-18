"import { Injectable } from '@angular/core';" + "\r\n" + 
"import { HttpParams } from \"@angular/common/http\";" + "\r\n" + 
"import { Observable } from \"rxjs\";" + "\r\n" + 
"import { ApiRequestService } from \"../../../../services/api/api-request.service\";" + "\r\n" + 
"@Injectable({" + "\r\n" + 
"  providedIn: 'root'" + "\r\n" + 
"})" + "\r\n" + 
"export class Gnyandipta_tservice{" + "\r\n" + 
"  private baseURL = \"Gnyandipta/Gnyandipta\" ;  constructor(" + "\r\n" + 
"    private apiRequest: ApiRequestService," + "\r\n" + 
"  ) { }" + "\r\n" + 
"  getAll(page?: number, size?: number): Observable<any> {" + "\r\n" + 
"    return this.apiRequest.get(this.baseURL);" + "\r\n" + 
"  }" + "\r\n" + 
"  getById(id: number): Observable<any> {" + "\r\n" + 
"    const _http = this.baseURL + \"/\" + id;" + "\r\n" + 
"    return this.apiRequest.get(_http);" + "\r\n" + 
"  }" + "\r\n" + 
"  create(data: any): Observable<any> {" + "\r\n" + 
"    return this.apiRequest.post(this.baseURL, data);" + "\r\n" + 
"  }" + "\r\n" + 
"  update(id: number, data: any): Observable<any> {" + "\r\n" + 
"    const _http = this.baseURL + \"/\" + id;" + "\r\n" + 
"    return this.apiRequest.put(_http, data);" + "\r\n" + 
"  }" + "\r\n" + 
"  delete(id: number): Observable<any> {" + "\r\n" + 
"    const _http = this.baseURL + \"/\" + id;" + "\r\n" + 
"    return this.apiRequest.delete(_http);" + "\r\n" + 
"  }" + "\r\n" + 
"}" 