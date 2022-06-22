import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LimitconfigurationComponent } from './limitconfiguration.component';

describe('LimitconfigurationComponent', () => {
  let component: LimitconfigurationComponent;
  let fixture: ComponentFixture<LimitconfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LimitconfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LimitconfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
