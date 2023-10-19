import { TestBed } from '@angular/core/testing';

import { WebpageBuilderService } from './webpage-builder.service';

describe('WebpageBuilderService', () => {
  let service: WebpageBuilderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WebpageBuilderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
