import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../services/api/login.service';
import { ActivatedRoute} from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import{environment} from 'src/environments/environment';
//import { UserRegistrationService } from 'src/app/services/api/user-registration.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  baseUrl= environment.regiurl;
  email = '';
  password = '';
  isError = false;

  model: any = {};
  errMsg: string = '';
  constructor(
    private router: Router,
    private route:ActivatedRoute,
    private loginService: LoginService,
    private toastr: ToastrService,
  ) { }

  ngOnInit() {
    this.loginService.logout(false);
   // this.userRegistrationService.removeSignedUpUserInfo();
    //this.userRegistrationService.removeStoredEmail();
  }

  onLogin() {
    // tslint:disable-next-line:max-line-length
    this.loginService.getToken(this.model.email, this.model.password)
      .subscribe(resp => {
        if (resp.user === undefined || resp.user.token === undefined || resp.user.token === "INVALID") {
          this.errMsg = 'Checking Email or password';
          return;
        }
        this.router.navigate([resp.landingPage]);// add , {skipLocationChange: true}
      },
        (errResponse: HttpErrorResponse) => {
          switch (errResponse.status) {
            case 401:
              this.errMsg = 'Email or password is incorrect';
              break;
            case 404:
              this.errMsg = 'Service not found';
            case 408:
              this.errMsg = 'Request Timedout';
            case 500:
              this.errMsg = 'Internal Server Error';
            default:
              this.errMsg = 'Server Error';
          }
        }
      );

  }
  goaccount(){
    window.location.href = `${this.baseUrl}`;
  }
  goforgotpass(){
    this.router.navigate(["../forgotpass"], { relativeTo: this.route });
  }
}
