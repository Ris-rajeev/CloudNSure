import { TestBed } from '@angular/core/testing';

import { StdParamSetupService } from './std-param-setup.service';

describe('StdParamSetupService', () => {
  let service: StdParamSetupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StdParamSetupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
