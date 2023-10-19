
import { ErrorHandler, NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GuidedTourComponent } from './guided-tour.component';
import { WindowrefService } from '../windowref.service';
import { GuidedTourService } from '../guided-tour.service';



@NgModule({
  declarations: [GuidedTourComponent],
  imports: [CommonModule],
  providers: [WindowrefService],
  exports: [GuidedTourComponent],
  entryComponents: [GuidedTourComponent],
})
export class GuidedTourModule {
  public static forRoot(): ModuleWithProviders<GuidedTourModule> {
    return {
      ngModule: GuidedTourModule,
      providers: [ErrorHandler, GuidedTourService],
    };
  }
 }
