import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-exception',
  templateUrl: './exception.component.html',
  styleUrls: ['./exception.component.scss']
})
export class ExceptionComponent implements OnInit {
  loading = false;
  public entryForm: FormGroup;
  givendata;
  modalAdd= false;
  modaldelete=false;
  rowSelected :any= {};
  error;
  constructor() { }

  ngOnInit(): void {
  }

}
