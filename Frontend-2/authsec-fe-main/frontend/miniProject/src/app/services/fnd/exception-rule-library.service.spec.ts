import { TestBed } from '@angular/core/testing';

import { ExceptionRuleLibraryService } from './exception-rule-library.service';

describe('ExceptionRuleLibraryService', () => {
  let service: ExceptionRuleLibraryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExceptionRuleLibraryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
