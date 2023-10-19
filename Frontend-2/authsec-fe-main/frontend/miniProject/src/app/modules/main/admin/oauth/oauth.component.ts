import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/api/login.service';
import {SureconnectService} from '../../../../services/admin/sureconnect.service'
@Component({
  selector: 'app-oauth',
  templateUrl: './oauth.component.html',
  styleUrls: ['./oauth.component.scss']
})
export class OauthComponent implements OnInit {
  public entryForm: FormGroup;
  model: any = {};
  errMsg: string = '';
  constructor(private sureconnectservice:SureconnectService,
    private _fb: FormBuilder,
    private route:ActivatedRoute,
    private router: Router,
    private loginService: LoginService,) { }

  ngOnInit(): void {
    this.entryForm = this._fb.group({
      connection_name:[null] ,
      description:[null] ,
      type:[null],
      access_token:[null],
      client_id:[null],
      username:[null],
      password:[null],

      });
  }
onSubmit(){
this.sureconnectservice.create(this.entryForm.value).subscribe((data)=>{
  console.log(data);
})
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
}
