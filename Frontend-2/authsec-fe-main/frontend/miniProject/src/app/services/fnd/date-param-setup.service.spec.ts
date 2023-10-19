import { TestBed } from '@angular/core/testing';

import { DateParamSetupService } from './date-param-setup.service';

describe('DateParamSetupService', () => {
  let service: DateParamSetupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DateParamSetupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
