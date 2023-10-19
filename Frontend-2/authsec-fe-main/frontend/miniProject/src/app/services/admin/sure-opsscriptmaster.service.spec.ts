import { TestBed } from '@angular/core/testing';

import { SureOpsscriptmasterService } from './sure-opsscriptmaster.service';

describe('SureOpsscriptmasterService', () => {
  let service: SureOpsscriptmasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SureOpsscriptmasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
