import { TestBed } from '@angular/core/testing';

import { IncomingwebhookService } from './incomingwebhook.service';

describe('IncomingwebhookService', () => {
  let service: IncomingwebhookService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IncomingwebhookService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
