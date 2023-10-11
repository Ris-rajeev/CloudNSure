import { ExcelService } from './services/excel.service';
import { BrowserModule } from '@angular/platform-browser';

import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClarityModule } from '@clr/angular';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MainModule } from './modules/main/main.module';
import { LoginModule } from './modules/login/login.module';
import { MainService } from './services/main.service';
import { AlertService } from './services/alert.service';
import { HelperModule } from './pipes/helpers.module';
import { LogoComponent } from './modules/logo/logo.component';
import { AppConfig } from './app-config';
import { JwtInterceptor } from './services/jwt.interceptor';
import { UserInfoService } from './services/user-info.service';
import { AuthGuard } from './services/auth_guard.service';
import { LoginService } from './services/api/login.service';
import { ApiRequestService } from './services/api/api-request.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";
import { TranslateService } from './services/api/translate.service';
import { RealnetMenuService } from './services/api/realnet-menu.service';
import { UserProfileService } from './services/admin/user-profile.service';
import { DragDropModule  } from '@angular/cdk/drag-drop';

import '@clr/icons';
import '@clr/icons/shapes/all-shapes';

@NgModule({
  declarations: [
    AppComponent,
    LogoComponent,


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HelperModule,
    MainModule,
    LoginModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,

  ],
  providers: [
    MainService,
    AlertService,
    ExcelService,
    UserInfoService,
    LoginService,
    ApiRequestService,
    TranslateService,
    RealnetMenuService,
    UserProfileService,
    // ProjectSetupService,
    // TechnologyStackService,
    // DropdownService,
    // WireframeService,
    // SuregitService,
    AuthGuard,
    AppConfig,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: LocationStrategy, useClass: HashLocationStrategy }  // HashLocationStrategy  to use # and remove # PathLocationStrategy
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA  ]
})
export class AppModule { }
