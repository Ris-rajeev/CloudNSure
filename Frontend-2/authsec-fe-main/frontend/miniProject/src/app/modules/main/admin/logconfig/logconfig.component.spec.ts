import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogconfigComponent } from './logconfig.component';

describe('LogconfigComponent', () => {
  let component: LogconfigComponent;
  let fixture: ComponentFixture<LogconfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogconfigComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LogconfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
