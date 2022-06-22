import { NgModule, ModuleWithProviders } from '@angular/core';

import { MrllistfilterPipe } from 'src/app/mrllistfilter.pipe';
import { MrlfilterPipe } from 'src/app/layout/limitconfiguration/mrlfilter.pipe';

@NgModule({
  exports: [MrllistfilterPipe, MrlfilterPipe],
  declarations: [MrllistfilterPipe, MrlfilterPipe]
})

export class PipeModuleModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: PipeModuleModule
    };
  }
}
