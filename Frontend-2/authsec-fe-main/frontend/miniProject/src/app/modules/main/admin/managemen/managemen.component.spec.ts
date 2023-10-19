import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagemenComponent } from './managemen.component';

describe('ManagemenComponent', () => {
  let component: ManagemenComponent;
  let fixture: ComponentFixture<ManagemenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagemenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagemenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
