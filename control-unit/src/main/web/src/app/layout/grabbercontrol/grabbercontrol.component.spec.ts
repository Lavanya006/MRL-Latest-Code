import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrabbercontrolComponent } from './grabbercontrol.component';

describe('GrabbercontrolComponent', () => {
  let component: GrabbercontrolComponent;
  let fixture: ComponentFixture<GrabbercontrolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrabbercontrolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrabbercontrolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
