import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NameconfigurationComponent } from './nameconfiguration.component';

describe('NameconfigurationComponent', () => {
  let component: NameconfigurationComponent;
  let fixture: ComponentFixture<NameconfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NameconfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NameconfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
