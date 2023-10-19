import { TestBed } from '@angular/core/testing';

import { SureopsService } from './sureops.service';

describe('SureopsService', () => {
  let service: SureopsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SureopsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
