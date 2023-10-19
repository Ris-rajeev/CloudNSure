import { TestBed } from '@angular/core/testing';

import { SureapiService } from './sureapi.service';

describe('SureapiService', () => {
  let service: SureapiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SureapiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
