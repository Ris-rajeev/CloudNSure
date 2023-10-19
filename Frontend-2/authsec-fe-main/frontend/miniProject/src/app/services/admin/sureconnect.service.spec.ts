import { TestBed } from '@angular/core/testing';

import { SureconnectService } from './sureconnect.service';

describe('SureconnectService', () => {
  let service: SureconnectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SureconnectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
