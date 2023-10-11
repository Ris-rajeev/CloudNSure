import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThaiDateAbbrPipe } from './thai-date-abbr.pipe';
import { ThaiDateFullPipe } from './thai-date-full.pipe';
import { ThaiDatePipe } from './thai-date.pipe';
import {TimePipePipe} from './time-pipe.pipe';
import { SearchFilterPipe } from './search-filter.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    ThaiDatePipe,
    ThaiDateAbbrPipe,
    ThaiDateFullPipe,
    TimePipePipe,
    SearchFilterPipe,
  ],
  exports: [
    ThaiDatePipe,
    ThaiDateAbbrPipe,
    ThaiDateFullPipe,
    TimePipePipe,
    SearchFilterPipe,
  ]
})
export class HelperModule { }
