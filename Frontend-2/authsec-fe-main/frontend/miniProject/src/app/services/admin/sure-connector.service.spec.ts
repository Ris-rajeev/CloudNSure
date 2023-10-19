import { TestBed } from '@angular/core/testing';

import { SureConnectorService } from './sure-connector.service';

describe('SureConnectorService', () => {
  let service: SureConnectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SureConnectorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
