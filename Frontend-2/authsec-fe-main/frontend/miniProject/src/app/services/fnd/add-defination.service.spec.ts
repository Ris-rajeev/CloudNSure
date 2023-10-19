import { TestBed } from '@angular/core/testing';

import { AddDefinationService } from './add-defination.service';

describe('AddDefinationService', () => {
  let service: AddDefinationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddDefinationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
