import { TestBed } from '@angular/core/testing';

import { ColumnSetupService } from './column-setup.service';

describe('ColumnSetupService', () => {
  let service: ColumnSetupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ColumnSetupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
