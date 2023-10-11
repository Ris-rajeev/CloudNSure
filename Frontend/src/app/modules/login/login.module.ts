import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';

import { LoginRoutingModule } from './login-routing.module';
import { LoginPageComponent } from './login-page/login-page.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { ForgotresetpasswordComponent } from './forgotresetpassword/forgotresetpassword.component';
import { Forgotresetpassword1Component } from './forgotresetpassword1/forgotresetpassword1.component';
import { AddguestComponent } from './addguest/addguest.component';

@NgModule({
  declarations: [LoginPageComponent, ForgotpasswordComponent, ForgotresetpasswordComponent, Forgotresetpassword1Component, AddguestComponent],
  imports: [
    CommonModule,
    FormsModule,
    ClarityModule,
    LoginRoutingModule,
  ReactiveFormsModule,
  ]
})
export class LoginModule { }
