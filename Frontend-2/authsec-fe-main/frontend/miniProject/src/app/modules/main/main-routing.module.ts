


import { Component, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainPageComponent } from '../main/fnd/main-page/main-page.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AboutComponent } from '../main/admin/about/about.component';
import { LayoutComponent } from './layout/layout.component';
import { UserComponent } from '../main/admin/user/user.component';

import { PasswordResetComponent } from '../main/admin/password-reset/password-reset.component';
import { DashboardComponent } from '../main/fnd/dashboard/dashboard.component';

import { AllMenuGroupComponent } from '../main/admin/menu-group/all/all-menu-group.component';

import { EditMenuGroupComponent } from '../main/admin/menu-group/edit/edit-menu-group.component';
import { MenuGroupComponent } from '../main/admin/menu-group/menu-group.component';
import { ReadOnlyMenuGroupComponent } from '../main/admin/menu-group/read-only/readonly-menu-group.component';
import { MenuRegisterComponent } from '../main/admin/menu-register/menu-register.component';
import { AllMenurComponent } from '../main/admin/menu-register/all-menur/all-menur.component';
import { AddMenurComponent } from '../main/admin/menu-register/add-menur/add-menur.component';
import { EditMenurComponent } from '../main/admin/menu-register/edit-menur/edit-menur.component';
import { ReadonlyMenurComponent } from '../main/admin/menu-register/readonly-menur/readonly-menur.component';
import { ProfileSettingComponent } from '../main/admin/profile-setting/profile-setting.component';
import { UsermaintanceComponent } from '../main/admin/usermaintance/usermaintance.component';
import { UsermaintanceaddComponent } from '../main/admin/usermaintanceadd/usermaintanceadd.component';
import { UsermaintanceeditComponent } from '../main/admin/usermaintanceedit/usermaintanceedit.component';
import { UsergrpmaintenanceComponent } from '../main/admin/usergrpmaintenance/usergrpmaintenance.component';
import { MenuaccesscontrolComponent } from '../main/admin/menuaccesscontrol/menuaccesscontrol.component';
import { LogconfigComponent } from '../main/admin/logconfig/logconfig.component';
import { AuthGuard } from '../../services/auth_guard.service';

import { SystemparametersComponent } from '../main/admin/systemparameters/systemparameters.component';
import { MenumaintanceComponent } from '../main/admin/menumaintance/menumaintance.component';
// import { DocumentmasterComponent } from '../main/admin/documentmaster/documentmaster.component';
// import { DocumentreferenceComponent } from '../main/admin/documentreference/documentreference.component';
// import { DocumentstructureComponent } from '../main/admin/documentstructure/documentstructure.component';

import { MyworkspaceComponent } from '../main/admin/myworkspace/myworkspace.component';

import { OauthComponent } from './admin/oauth/oauth.component';

import { QueryComponent } from './admin/query/query.component';

import { SubmenuComponent } from './admin/submenu/submenu.component';

import { Role } from '../../models/admin/role';
import { AccesstypeComponent } from './admin/accesstype/accesstype.component';
import { ModulesComponent } from './admin/modules/modules.component';

import { SessionloggerComponent } from './admin/sessionlogger/sessionlogger.component';
import { LogreaderComponent } from './admin/logreader/logreader.component';
import { ExceptionComponent } from './admin/exception/exception.component';
import { AuditreportComponent } from './admin/auditreport/auditreport.component';

import { AudithistoryComponent } from './admin/audithistory/audithistory.component';




const routes: Routes = [
   //Important: The sequence of path is important as the router go over then in sequential manner
   { path: '', redirectTo: '/cns-portal/dashboard', pathMatch: 'full' },
  {
    path: 'cns-portal',
    component: LayoutComponent,
     canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'user', pathMatch: 'full' },
      { path: 'main', component: MainPageComponent },
      { path: 'user', component: UserComponent},

      {path: 'usermaintance',component:UsermaintanceComponent},
      {path: 'usermaintanceadd',component:UsermaintanceaddComponent},
      {path: 'usermaintancedit/:id',component:UsermaintanceeditComponent},
      {path: 'usergrpmaintance',component:UsergrpmaintenanceComponent},
      {path: 'menuaccess',component:MenuaccesscontrolComponent},
      {path: 'systemparameters', component: SystemparametersComponent},
      {path: 'query',component:QueryComponent,canActivate: [AuthGuard],data: { roles: [Role.Admin] } },
      {path: 'menumaintance', component:MenumaintanceComponent},
      {path: 'submenu/:id',component:SubmenuComponent},
 
      {path:'log',component:LogconfigComponent},
     { path: 'passwordreset', component: PasswordResetComponent },
     { path: 'profile-settings', component: ProfileSettingComponent },
      { path: 'about', component: AboutComponent },
   
      {path: 'myworkspace', component:MyworkspaceComponent},

      {path: 'oauth', component:OauthComponent},

      {path: 'logreader',component:LogreaderComponent},
 
      {path: 'accesstype',component:AccesstypeComponent}, //////////////////////////////////
      {path: 'acmodules',component:ModulesComponent},
      {path: 'sessionlogger',component:SessionloggerComponent},
      {path: 'exception',component:ExceptionComponent},
      {path: 'auditreport',component:AuditreportComponent},
      {path: 'audithistory/:id',component:AudithistoryComponent},
      {path: 'dashboard', component:DashboardComponent,

    },

    {
      path: 'menu-group', component: MenuGroupComponent,
      children: [
        { path: '', redirectTo: 'all-menu' ,pathMatch: 'full' },
        { path: 'all-menu', component: AllMenuGroupComponent },
        { path: 'edit-menu', component: EditMenuGroupComponent },
        { path: 'read-only', component: ReadOnlyMenuGroupComponent }
      ],
    },

    {
      path: 'menu-r', component: MenuRegisterComponent,
      children: [
        { path: '', redirectTo: 'all-r', pathMatch: 'full' },
        { path: 'all-r', component: AllMenurComponent },
        { path: 'add-r', component: AddMenurComponent },
        { path: 'edit-r', component: EditMenurComponent },
        { path: 'readonly-r', component: ReadonlyMenurComponent }
      ],
    },

            { path: '**', component: PageNotFoundComponent },
      ]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }