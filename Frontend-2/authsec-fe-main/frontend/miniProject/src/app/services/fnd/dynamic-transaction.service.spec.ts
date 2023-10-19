import { TestBed } from '@angular/core/testing';

import { DynamicTransactionService } from './dynamic-transaction.service';

describe('DynamicTransactionService', () => {
  let service: DynamicTransactionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DynamicTransactionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
