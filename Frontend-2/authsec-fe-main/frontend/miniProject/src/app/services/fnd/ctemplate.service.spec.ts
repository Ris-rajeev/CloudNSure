import { TestBed } from '@angular/core/testing';

import { CtemplateService } from './ctemplate.service';

describe('CtemplateService', () => {
  let service: CtemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CtemplateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
