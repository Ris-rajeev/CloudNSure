import { TestBed } from '@angular/core/testing';

import { RuleLibraryService } from './rule-library.service';

describe('RuleLibraryService', () => {
  let service: RuleLibraryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RuleLibraryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
