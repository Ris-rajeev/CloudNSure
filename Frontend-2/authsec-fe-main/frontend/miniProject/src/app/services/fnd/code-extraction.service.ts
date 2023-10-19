import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';


import { Bcf_Extractor } from 'src/app/models/fnd/Bcf_Extractor';
import { Bcf_Extractor_Params } from 'src/app/models/fnd/Bcf_Extractor_Params';
import { FileData } from 'src/app/models/builder/FileData';
import { Observable } from 'rxjs';
import { ApiRequestService } from '../api/api-request.service';
@Injectable({
  providedIn: 'root'
})
export class CodeExtractionService {
  private codeExtractionURL = 'api/bcf-extractor';
  private codeExtractionParamsURL = 'api/bcf-extractor-params';
  private staticCodeExtractionURL = 'api/static_code_extraction';
  private dynamicCodeExtractionURL = 'api/dynamic_code_extraction';
  private buildMasterBuilderURL = 'api/build_master_builder';
  private extractionStatusChangeURL = 'api/bcf-extractor-params/extraction-status-change';
  private creationStatusChangeURL = 'api/bcf-extractor-params/creation-status-change';

  constructor(private apiRequest: ApiRequestService) { }
  getAll(page?: number, size?: number): Observable<any> {
    //Create Request URL params
    let me = this;
    let params: HttpParams = new HttpParams();
    params = params.append("page", typeof page === "number" ? page.toString() : "0");
    params = params.append("size", typeof size === "number" ? size.toString() : "1000");
    // get all
    // return this.apiRequest.get('api/instructors');
    // paginated data
    return this.apiRequest.get(this.codeExtractionURL, params);
  }

  getById(id: number): Observable<Bcf_Extractor> {
    const _http = this.codeExtractionURL + "/" + id;
    return this.apiRequest.get(_http);
  }

  create(bcf_extractor: Bcf_Extractor): Observable<Bcf_Extractor> {
    //`${this.baseURL}`
    return this.apiRequest.post(this.codeExtractionURL, bcf_extractor);
  }

  // UPLOAD ZIP FILE DATA
  saveFormAndUploadFile(formData: FormData) :Observable<any> {
    //return this.http.post(this.appConfig.baseApiPath + this.codeExtractionURL, formData, { reportProgress:true, observe: 'events' });
    return this.apiRequest.postFormData(this.codeExtractionURL, formData);
  }
  delete1(id: number): Observable<any> {
    const _http = this.codeExtractionURL + "/" + id;
    return this.apiRequest.delete(_http);
  }
  update(id: number, bcf_extractor: Bcf_Extractor): Observable<Bcf_Extractor> {
    const _http = this.codeExtractionURL + "/" + id;
    return this.apiRequest.put(_http, bcf_extractor);
  }

  // =========== CODE EXTRACTION PARAMS ===============

  getCodeExtractionParamById(id: number): Observable<Bcf_Extractor_Params> {
    const _http = this.codeExtractionParamsURL + "/" + id;
    return this.apiRequest.get(_http);
  }
  getCodeExtractionParams(header_id: number): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append('header_id',  header_id.toString());
    return this.apiRequest.get(this.codeExtractionParamsURL, params);
  }
  delete(id: number): Observable<any> {
    const _http = this.codeExtractionParamsURL + "/" + id;
    return this.apiRequest.delete(_http);
  }
  createExtractionParams(header_id: number, bcf_extractor_params: Bcf_Extractor_Params): Observable<Bcf_Extractor_Params> {
    let params: HttpParams = new HttpParams();
    params = params.append('header_id',  header_id.toString());
    return this.apiRequest.post(this.codeExtractionParamsURL, bcf_extractor_params, params);
  }

  updateExtractionParams(id: number, header_id: number, bcf_extractor_params: Bcf_Extractor_Params): Observable<Bcf_Extractor_Params> {
    let params: HttpParams = new HttpParams();
    params = params.append('header_id',  header_id.toString());
    const _http = this.codeExtractionParamsURL + "/" + id;
    return this.apiRequest.put(_http, bcf_extractor_params, params);
  }
updatecodeparam(id:any,bcf_extractor_params: Bcf_Extractor_Params):Observable<Bcf_Extractor_Params>{
  const _http = this.codeExtractionParamsURL + "/" + id;
  return this.apiRequest.put(_http, bcf_extractor_params, );
}
  // CREATION STATUS CHANGE

  creationStatusChange(id: number): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append("id", id.toString());
    return this.apiRequest.get(this.creationStatusChangeURL, params);
  }
  // EXTRACTION STATUS CHANGE
  extractionStatusChange(id: number): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append("id", id.toString());
    return this.apiRequest.get(this.extractionStatusChangeURL, params);
  }
  // STATIC CODE EXTRACTOPN AND DYNAMIC CODE EXTRACTION
  staticCodeExtraction(id:number): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append('header_id',  id.toString());
    return this.apiRequest.get(this.staticCodeExtractionURL, params);
  }
  dynamicCodeExtraction(id:number): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append('header_id',  id.toString());
    return this.apiRequest.get(this.dynamicCodeExtractionURL, params);
  }

  // BUILD MASTER BUILDER
  buildMasterBuilder(id: number): Observable<any> {
    let params: HttpParams = new HttpParams();
    params = params.append('id',  id.toString());
    return this.apiRequest.get(this.buildMasterBuilderURL, params);
  }

  // ========== code editor service =======

  private codeExtractorFileListURL = 'api/bcf-extractor-file-list';
  private fileReadURL = 'api/file-code-read';
  private fileCodeSaveURL = 'api/file-code-save';

  getFileList(id: number): Observable<any> {
    const _http = this.codeExtractorFileListURL + "/" + id;
    return this.apiRequest.get(_http);

  }

  readFile(id: number): Observable<FileData> {
    const _http = this.fileReadURL + "/" + id;
    return this.apiRequest.get(_http);
  }
  saveCodeIntoFile(fileData: FileData): Observable<any> {
    return this.apiRequest.post(this.fileCodeSaveURL, fileData);
  }

  // ======= STATIC CODE READ AND SAVE ========== //
  private staticFileReadURL = 'api/static-file-code-read';
  private SstaticFileCodeSaveURL = 'api/static-file-code-save';
  readStaticFile(id: number): Observable<FileData> {
    const _http = this.staticFileReadURL + "/" + id;
    return this.apiRequest.get(_http);
  }

  saveCodeIntoStaticFile(fileData: FileData): Observable<any> {
    return this.apiRequest.post(this.SstaticFileCodeSaveURL, fileData);
  }
}
