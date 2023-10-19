import { TestBed } from '@angular/core/testing';

import { BiDashSetupService } from './bi-dash-setup.service';

describe('BiDashSetupService', () => {
  let service: BiDashSetupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BiDashSetupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
