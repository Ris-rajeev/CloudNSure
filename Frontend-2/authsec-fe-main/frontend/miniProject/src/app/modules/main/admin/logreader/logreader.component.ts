import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CodemirrorComponent } from '@ctrl/ngx-codemirror';
import { ToastrService } from 'ngx-toastr';
import { LogconfigService } from 'src/app/services/admin/logconfig.service';

@Component({
  selector: 'app-logreader',
  templateUrl: './logreader.component.html',
  styleUrls: ['./logreader.component.scss']
})
export class LogreaderComponent implements OnInit {
  @ViewChild('codeEditor') codeEditor: CodemirrorComponent;
  codeMirrorOptions: any = {
    theme: "lint",
    mode:"text/x-java",
    lineNumbers: true,
    //noNewlines: true,
    lineSeparator: "\n",
    lineWrapping: true,
    foldGutter: true,
    gutters: [
      "CodeMirror-linenumbers",
      "CodeMirror-foldgutter",
      "CodeMirror-lint-markers",
    ],
    autoCloseBrackets: true,
    extraKeys: { "Ctrl-Space": "autocomplete" },
    matchBrackets: true,
    lint: true,
  };
  id:any;
  fileData:any={};
  constructor( private route:ActivatedRoute, private mainservice:LogconfigService,
    private toastr:ToastrService,) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.id = +params['id'];
      console.log(" ID ",this.id);
    });
    this.mainservice.readfile(this.id).subscribe((data)=>{
      console.log(data);
      this.fileData = data;

    })
  }
  saveTextIntoFile(){

  }
}
