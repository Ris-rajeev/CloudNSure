import { HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Rn_Main_Menu } from "../../models/builder/Rn_Main_Menu";
import { Observable } from "rxjs";
import { UserInfoService } from "../user-info.service";
import { ApiRequestService } from "./api-request.service";

@Injectable()
export class RealnetMenuService {
  private baseURL = "api/realnet-menu";
  constructor(
    private apiRequest: ApiRequestService,
    private userInfoService: UserInfoService
  ) {}

  getAll(page?: number, size?: number): Observable<any> {
    //Create Request URL params
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    //const _http = this.baseURL + '/all';
    return this.apiRequest.get(this.baseURL, params);
  }

  getById(id: number): Observable<Rn_Main_Menu> {
    const _http = this.baseURL + "/" + id;
    return this.apiRequest.get(_http);
  }

  getByAccountId(): Observable<Rn_Main_Menu[]> {
      const _http = this.baseURL + "/user-menu";
      return this.apiRequest.get(_http);
  }

  create(rn_Main_Menu: Rn_Main_Menu): Observable<Rn_Main_Menu> {
    return this.apiRequest.post(this.baseURL, rn_Main_Menu);
  }

  update(id: number, rn_Main_Menu: Rn_Main_Menu): Observable<Rn_Main_Menu> {
    const _http = this.baseURL + "/" + id;
    return this.apiRequest.put(_http, rn_Main_Menu);
  }
}
