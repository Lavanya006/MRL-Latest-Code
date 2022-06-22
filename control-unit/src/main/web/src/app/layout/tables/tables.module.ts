import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TablesRoutingModule } from './tables-routing.module';
import { TablesComponent } from './tables.component';
import { PageHeaderModule } from './../../shared';
import { NameconfigurationComponent } from 'src/app/layout/nameconfiguration/nameconfiguration.component';
import { LimitconfigurationComponent } from 'src/app/layout/limitconfiguration/limitconfiguration.component';
import { ImportconfigurationComponent } from 'src/app/layout/importconfiguration/importconfiguration.component';
import {PipeModuleModule} from 'src/pipe-module/pipe-module.module';

@NgModule({
    imports: [CommonModule, TablesRoutingModule, PageHeaderModule, FormsModule, ReactiveFormsModule, PipeModuleModule.forRoot()],
    exports: [PipeModuleModule],
    declarations: [TablesComponent, NameconfigurationComponent, LimitconfigurationComponent, ImportconfigurationComponent]
})
export class TablesModule {}
