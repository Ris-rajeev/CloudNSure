"import { Component, OnInit } from '@angular/core';" + "\r\n" + 
"import { ToastrService } from 'ngx-toastr';" + "\r\n" + 
"import { AlertService } from 'src/app/services/alert.service';" + "\r\n" + 
"import { Gnyandipta_tservice} from './Gnyandipta_t.service';" + "\r\n" + 
"import { FormArray, FormBuilder, FormGroup } from '@angular/forms';" + "\r\n" + 
"@Component({" + "\r\n" + 
"  selector: 'app-Gnyandipta_t'," + "\r\n" + 
"  templateUrl: './Gnyandipta_t.component.html'," + "\r\n" + 
"  styleUrls: ['./Gnyandipta_t.component.scss']" + "\r\n" + 
"})" + "\r\n" + 
"export class Gnyandipta_tComponent implements OnInit {" + "\r\n" + 
"  rowSelected :any= {};" + "\r\n" + 
"  modaldelete=false;" + "\r\n" + 
"  modalEdit=false;" + "\r\n" + 
"  modalAdd= false;" + "\r\n" + 
"  public entryForm: FormGroup;" + "\r\n" + 
"  loading = false;" + "\r\n" + 
"  product;" + "\r\n" + 
"  modalOpenedforNewLine = false;" + "\r\n" + 
"  newLine:any;" + "\r\n" + 
" selected: any[] = []; constructor(" + "\r\n" + 
"    private mainService:Gnyandipta_tservice," + "\r\n" + 
"    private alertService: AlertService," + "\r\n" + 
"    private toastr: ToastrService," + "\r\n" + 
"    private _fb: FormBuilder," + "\r\n" + 
"  ) { }" + "\r\n" + 
"  ngOnInit(): void {" + "\r\n" + 
"    this.getData();" + "\r\n" + 
"    this.entryForm = this._fb.group({" + "\r\n" + 
"  firstname: [null]," + "\r\n" + 
"  lastname: [null]," + "\r\n" + 
"    });" + "\r\n" + 
"  }" + "\r\n" + 
"  getData() {" + "\r\n" + 
"    this.mainService.getAll().subscribe((data) => {" + "\r\n" + 
"      console.log(data);" + "\r\n" + 
"      this.product = data;" + "\r\n" + 
"     " + "\r\n" + 
"    });" + "\r\n" + 
"  }" + "\r\n" + 
"  onEdit(row) {" + "\r\n" + 
"    this.rowSelected = row;" + "\r\n" + 
"    this.modalEdit = true;" + "\r\n" + 
"  }" + "\r\n" + 
"   onDelete(row) {" + "\r\n" + 
"    this.rowSelected = row;" + "\r\n" + 
"     this.modaldelete=true;" + "\r\n" + 
"  }" + "\r\n" + 
"  delete(id)" + "\r\n" + 
"  {" + "\r\n" + 
"    this.modaldelete = false;" + "\r\n" + 
"    console.log(\"in delete  \"+id);" + "\r\n" + 
"    this.mainService.delete(id).subscribe(" + "\r\n" + 
"      (data) => {" + "\r\n" + 
"        console.log(data);" + "\r\n" + 
"        this.ngOnInit();" + "\r\n" + 
"  if (data) {				      this.toastr.success('Deleted successfully');      }" + "\r\n" + 
"    });" + "\r\n" + 
"  }" + "\r\n" + 
"    onUpdate(id) {" + "\r\n" + 
"      this.modalEdit = false;" + "\r\n" + 
"         //console.log(\"in update\");" + "\r\n" + 
"      console.log(\"id  \"+id);" + "\r\n" + 
"      console.log( this.rowSelected );" + "\r\n" + 
"      //console.log(\"out update\");" + "\r\n" + 
"      this.mainService.update(id,this.rowSelected).subscribe(" + "\r\n" + 
"        (data) => {" + "\r\n" + 
"          console.log(data);" + "\r\n" + 
"        }," + "\r\n" + 
"      );" + "\r\n" + 
"      if (id) {" + "\r\n" + 
"        this.toastr.success('Updated successfully');" + "\r\n" + 
"              }" + "\r\n" + 
"  }" + "\r\n" + 
"  goToAdd(row) {" + "\r\n" + 
"this.modalAdd = true;" + "\r\n" + 
"  }" + "\r\n" + 
"onSubmit() {" + "\r\n" + 
"  console.log(this.entryForm.value);" + "\r\n" + 
"  if (this.entryForm.invalid) {" + "\r\n" + 
"    return;" + "\r\n" + 
"  }" + "\r\n" + 
"  this.onCreate();" + "\r\n" + 
"}" + "\r\n" + 
"onCreate() {" + "\r\n" + 
"     this.modalAdd=false;" + "\r\n" + 
"  this.mainService.create(this.entryForm.value).subscribe(" + "\r\n" + 
"    (data) => {" + "\r\n" + 
"      console.log(data);" + "\r\n" + 
"      this.ngOnInit();" + "\r\n" + 
"    }," + "\r\n" + 
"  );" + "\r\n" + 
"  if (this.entryForm.value) {" + "\r\n" + 
"    this.toastr.success('Added successfully');" + "\r\n" + 
"  }" + "\r\n" + 
"}" + "\r\n" + 
"}" 