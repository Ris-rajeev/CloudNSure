import { TestBed } from '@angular/core/testing';

import { AdhocParamSetupService } from './adhoc-param-setup.service';

describe('AdhocParamSetupService', () => {
  let service: AdhocParamSetupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdhocParamSetupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
