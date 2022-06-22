import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportconfigurationComponent } from './importconfiguration.component';

describe('ImportconfigurationComponent', () => {
  let component: ImportconfigurationComponent;
  let fixture: ComponentFixture<ImportconfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImportconfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportconfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
