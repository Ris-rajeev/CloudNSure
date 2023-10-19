import { TestBed } from '@angular/core/testing';

import { OutgoingwebhookService } from './outgoingwebhook.service';

describe('OutgoingwebhookService', () => {
  let service: OutgoingwebhookService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OutgoingwebhookService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
