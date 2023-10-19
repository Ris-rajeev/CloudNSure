import { TestBed } from '@angular/core/testing';

import { WhereParamSetupService } from './where-param-setup.service';

describe('WhereParamSetupService', () => {
  let service: WhereParamSetupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WhereParamSetupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
