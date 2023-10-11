import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseUrl from '../api/helper';
@Injectable({
  providedIn: 'root'
})
export class ForgotpassService {
  private url = "api/forgot";
  public localStorage: Storage = localStorage;
  constructor(private http: HttpClient,) { }

  checkMailExists(email: string) {
    return this.http.post(this.url+ "check-email", {"email": email});
  }
  storeEmail(userInfoString: string) {
    this.localStorage.setItem("registeredEmail", userInfoString);
  }
  //Store userinfo from session storage

  //Get email from session storage ( WILL REMOVE AFTER REGISTER)
  getStoredEmail(): string | null {
    try {
      let userInfoString: string = this.localStorage.getItem(
       "registeredEmail"
      );
      if (userInfoString) {
        return userInfoString;
      } else {
        return null;
      }
    } catch (e) {
      return null;
    }
  }
  sendemail(email:string){
    const params = new HttpParams()
    .set('email',email)
  console.log(email);
   return this.http.post(`${baseUrl}/api/resources/forgotpassword`,params)
  }
  resetpass(data:any,id:any){
    return this.http.post(`${baseUrl}/api/resources/savePassword/${id}`,data);
  }
}
