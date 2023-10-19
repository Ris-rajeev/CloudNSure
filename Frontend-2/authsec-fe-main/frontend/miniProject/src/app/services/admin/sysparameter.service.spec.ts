import { TestBed } from '@angular/core/testing';

import { SysparameterService } from './sysparameter.service';

describe('SysparameterService', () => {
  let service: SysparameterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SysparameterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
