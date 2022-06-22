import { TestBed } from '@angular/core/testing';

import { RestcontrollerService } from './restcontroller.service';

describe('RestcontrollerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RestcontrollerService = TestBed.get(RestcontrollerService);
    expect(service).toBeTruthy();
  });
});
