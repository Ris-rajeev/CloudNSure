import { TestBed } from '@angular/core/testing';

import { JobmanagementService } from './jobmanagement.service';

describe('JobmanagementService', () => {
  let service: JobmanagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobmanagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
