import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AlertService } from 'src/app/services/alert.service';
import { Gnyandipta_tservice} from './Gnyandipta_t.service';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
@Component({
  selector: 'app-Gnyandipta_t',
  templateUrl: './Gnyandipta_t.component.html',
  styleUrls: ['./Gnyandipta_t.component.scss']
})
export class Gnyandipta_tComponent implements OnInit {
  rowSelected :any= {};
  modaldelete=false;
  modalEdit=false;
  modalAdd= false;
  public entryForm: FormGroup;
  loading = false;
  product;
  modalOpenedforNewLine = false;
  newLine:any;
 selected: any[] = []; constructor(
    private mainService:Gnyandipta_tservice,
    private alertService: AlertService,
    private toastr: ToastrService,
    private _fb: FormBuilder,
  ) { }
  ngOnInit(): void {
    this.getData();
    this.entryForm = this._fb.group({
  firstname: [null],
  lastname: [null],
    });
  }
  getData() {
    this.mainService.getAll().subscribe((data) => {
      console.log(data);
      this.product = data;
     
    });
  }
  onEdit(row) {
    this.rowSelected = row;
    this.modalEdit = true;
  }
   onDelete(row) {
    this.rowSelected = row;
     this.modaldelete=true;
  }
  delete(id)
  {
    this.modaldelete = false;
    console.log("in delete  "+id);
    this.mainService.delete(id).subscribe(
      (data) => {
        console.log(data);
        this.ngOnInit();
  if (data) {				      this.toastr.success('Deleted successfully');      }
    });
  }
    onUpdate(id) {
      this.modalEdit = false;
         //console.log("in update");
      console.log("id  "+id);
      console.log( this.rowSelected );
      //console.log("out update");
      this.mainService.update(id,this.rowSelected).subscribe(
        (data) => {
          console.log(data);
        },
      );
      if (id) {
        this.toastr.success('Updated successfully');
              }
  }
  goToAdd(row) {
this.modalAdd = true;
  }
onSubmit() {
  console.log(this.entryForm.value);
  if (this.entryForm.invalid) {
    return;
  }
  this.onCreate();
}
onCreate() {
     this.modalAdd=false;
  this.mainService.create(this.entryForm.value).subscribe(
    (data) => {
      console.log(data);
      this.ngOnInit();
    },
  );
  if (this.entryForm.value) {
    this.toastr.success('Added successfully');
  }
}
}