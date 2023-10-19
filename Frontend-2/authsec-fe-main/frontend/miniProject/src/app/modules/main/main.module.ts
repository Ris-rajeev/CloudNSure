
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



import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
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
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { LogconfigComponent } from '../main/admin/logconfig/logconfig.component';
import { SystemparametersComponent } from '../main/admin/systemparameters/systemparameters.component';
import { MenumaintanceComponent } from '../main/admin/menumaintance/menumaintance.component';
// import { DocumentmasterComponent } from '../main/admin/documentmaster/documentmaster.component';
// import { DocumentreferenceComponent } from '../main/admin/documentreference/documentreference.component';
// import { DocumentstructureComponent } from '../main/admin/documentstructure/documentstructure.component';

import { DndModule } from 'ngx-drag-drop';
import { DragDropModule } from '@angular/cdk/drag-drop';

import { HttpClientModule } from '@angular/common/http';

import { MyworkspaceComponent } from '../main/admin/myworkspace/myworkspace.component';
import { UserRegistrationComponent } from '../main/admin/user-registration/user-registration.component';

import { OauthComponent } from './admin/oauth/oauth.component';

import { GridsterModule } from 'angular-gridster2';
import { ChartsModule } from 'ng2-charts';

import { ImageCropperModule } from 'ngx-image-cropper';

import { QueryComponent } from './admin/query/query.component';
import { HighchartsChartModule } from "highcharts-angular";
import { SubmenuComponent } from './admin/submenu/submenu.component';
import {GuidedTourModule} from '../main/ngx-guided-tour/src/lib/guided-tour/guided-tour.module';
import { CKEditorModule } from 'ng2-ckeditor';
import { TagInputModule } from 'ngx-chips';
import { CodemirrorModule } from "@ctrl/ngx-codemirror";
import { AccesstypeComponent } from './admin/accesstype/accesstype.component';
import { ModulesComponent } from './admin/modules/modules.component';
import { CookieService } from 'ngx-cookie-service';
import { SessionloggerComponent } from './admin/sessionlogger/sessionlogger.component';
import { LogreaderComponent } from './admin/logreader/logreader.component';
import { ExceptionComponent } from './admin/exception/exception.component';
import { AuditreportComponent } from './admin/auditreport/auditreport.component';
import { AudithistoryComponent } from './admin/audithistory/audithistory.component';
@NgModule({
  declarations: [
    MainPageComponent, PageNotFoundComponent,AboutComponent, LayoutComponent, UserComponent,PasswordResetComponent,
     DashboardComponent,MenuGroupComponent, AllMenuGroupComponent, EditMenuGroupComponent, ReadOnlyMenuGroupComponent,UserRegistrationComponent,
   MenuRegisterComponent, AddMenurComponent, EditMenurComponent, AllMenurComponent, ReadonlyMenurComponent,ProfileSettingComponent,
    UsermaintanceComponent, UsermaintanceaddComponent, UsermaintanceeditComponent, UsergrpmaintenanceComponent, MenuaccesscontrolComponent,
    LogconfigComponent, SystemparametersComponent,MyworkspaceComponent,
   MenumaintanceComponent, OauthComponent,  QueryComponent, SubmenuComponent, AccesstypeComponent, ModulesComponent, SessionloggerComponent, LogreaderComponent, ExceptionComponent, AuditreportComponent, AudithistoryComponent

 
 
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
    DndModule,
    HttpClientModule,
    ImageCropperModule,
    HighchartsChartModule,
    TagInputModule,
   // QueryBuilderModule,
// Thirdparty Module
//WebdatarocksPivotModule,
CodemirrorModule,
NgxDatatableModule,
NgxChartsModule,
Ng2SearchPipeModule,
// DynamicModule.withComponents([DoughnutChartComponent, RadarChartComponent, LineChartComponent,BarChartComponent,PieChartComponent,
//   PolarChartComponent,BubbleChartComponent,ScatterChartComponent,DynamicChartComponent,FinancialChartComponent,ToDoChartComponent,
//   TextAreaComponent,ImgFieldComponent,LineFieldComponent,QrCodeComponent,TableFieldComponent]),
GridsterModule,
ChartsModule,
CKEditorModule,
GuidedTourModule.forRoot()
  ],
  providers: [
    CookieService,

  ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class MainModule { }