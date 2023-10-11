
import { NgModule, CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';

import { MainRoutingModule } from './main-routing.module';
import { MainPageComponent } from '../main/fnd/main-page/main-page.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AboutComponent } from '../main/admin/about/about.component';
import { LayoutComponent } from './layout/layout.component';
import { UserComponent } from '../main/admin/user/user.component';
import { HelperModule } from '../../pipes/helpers.module';
import { PasswordResetComponent } from '../main/admin/password-reset/password-reset.component';

import { DashboardComponent } from '../main/fnd/dashboard/dashboard.component';

import { MenuGroupComponent } from '../main/admin/menu-group/menu-group.component';
import { AllMenuGroupComponent } from '../main/admin/menu-group/all/all-menu-group.component';
import { EditMenuGroupComponent } from '../main/admin/menu-group/edit/edit-menu-group.component';
import { ReadOnlyMenuGroupComponent } from '../main/admin/menu-group/read-only/readonly-menu-group.component';
import { MenuRegisterComponent } from '../main/admin/menu-register/menu-register.component';
import { AddMenurComponent } from '../main/admin/menu-register/add-menur/add-menur.component';
import { EditMenurComponent } from '../main/admin/menu-register/edit-menur/edit-menur.component';
import { AllMenurComponent } from '../main/admin/menu-register/all-menur/all-menur.component';
import { ReadonlyMenurComponent } from '../main/admin/menu-register/readonly-menur/readonly-menur.component';
import { ProfileSettingComponent } from '../main/admin/profile-setting/profile-setting.component';
import { UsermaintanceComponent } from '../main/admin/usermaintance/usermaintance.component';
import { UsermaintanceaddComponent } from '../main/admin/usermaintanceadd/usermaintanceadd.component';
import { UsermaintanceeditComponent } from '../main/admin/usermaintanceedit/usermaintanceedit.component';
import { UsergrpmaintenanceComponent } from '../main/admin/usergrpmaintenance/usergrpmaintenance.component';
import { MenuaccesscontrolComponent } from '../main/admin/menuaccesscontrol/menuaccesscontrol.component';
import { SystemparametersComponent } from '../main/admin/systemparameters/systemparameters.component';
import { MenumaintanceComponent } from '../main/admin/menumaintance/menumaintance.component';

import { DragDropModule } from '@angular/cdk/drag-drop';

import { HttpClientModule } from '@angular/common/http';

import { UserRegistrationComponent } from '../main/admin/user-registration/user-registration.component';


import { ImageCropperModule } from 'ngx-image-cropper';
import { SubmenuComponent } from './admin/submenu/submenu.component';
import { TagInputModule } from 'ngx-chips';
import { AccesstypeComponent } from './admin/accesstype/accesstype.component';
import { ModulesComponent } from './admin/modules/modules.component';
import { CookieService } from 'ngx-cookie-service';
import { SessionloggerComponent } from './admin/sessionlogger/sessionlogger.component';

@NgModule({
  declarations: [
    MainPageComponent, PageNotFoundComponent,AboutComponent, LayoutComponent, UserComponent,PasswordResetComponent,
     DashboardComponent,MenuGroupComponent, AllMenuGroupComponent, EditMenuGroupComponent, ReadOnlyMenuGroupComponent,UserRegistrationComponent,
   MenuRegisterComponent, AddMenurComponent, EditMenurComponent, AllMenurComponent, ReadonlyMenurComponent,ProfileSettingComponent,
    UsermaintanceComponent, UsermaintanceaddComponent, UsermaintanceeditComponent, UsergrpmaintenanceComponent, MenuaccesscontrolComponent, SystemparametersComponent,
   MenumaintanceComponent, SubmenuComponent, AccesstypeComponent, ModulesComponent, SessionloggerComponent,

 
 
  ],
  // entryComponents: [DoughnutChartComponent, LineChartComponent, RadarChartComponent, BarChartComponent, BubbleChartComponent, DynamicChartComponent, ScatterChartComponent, PolarChartComponent, PieChartComponent, FinancialChartComponent, ToDoChartComponent,
  //   TextAreaComponent,ImgFieldComponent,LineFieldComponent,QrCodeComponent,TableFieldComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ClarityModule,
    HelperModule,
    MainRoutingModule,
    DragDropModule,
    HttpClientModule,
    ImageCropperModule,
    TagInputModule,
   // QueryBuilderModule,
// Thirdparty Module
//WebdatarocksPivotModule,
// DynamicModule.withComponents([DoughnutChartComponent, RadarChartComponent, LineChartComponent,BarChartComponent,PieChartComponent,
//   PolarChartComponent,BubbleChartComponent,ScatterChartComponent,DynamicChartComponent,FinancialChartComponent,ToDoChartComponent,
//   TextAreaComponent,ImgFieldComponent,LineFieldComponent,QrCodeComponent,TableFieldComponent]),
  ],
  providers: [
    CookieService,

  ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class MainModule { }