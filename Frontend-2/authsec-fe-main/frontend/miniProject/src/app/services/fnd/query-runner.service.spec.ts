import { TestBed } from '@angular/core/testing';

import { QueryRunnerService } from './query-runner.service';

describe('QueryRunnerService', () => {
  let service: QueryRunnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QueryRunnerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
