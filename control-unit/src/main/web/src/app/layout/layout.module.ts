import { NgModule, Pipe } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserModule } from '@angular/platform-browser';

import { LayoutRoutingModule } from './layout-routing.module';
import { LayoutComponent } from './layout.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HeaderComponent } from './components/header/header.component';
import {PipeModuleModule} from 'src/pipe-module/pipe-module.module';
import {MrlfilterPipe} from 'src/app/layout/limitconfiguration/mrlfilter.pipe';
import { MrllistfilterPipe } from 'src/app/mrllistfilter.pipe';
import { Http } from '@angular/http';
import { RestcontrollerService } from '../restcontroller.service';


// import { NameconfigurationComponent } from './nameconfiguration/nameconfiguration.component';
// import { LimitconfigurationComponent } from './limitconfiguration/limitconfiguration.component';
// import { ImportconfigurationComponent } from './importconfiguration/importconfiguration.component';
const COMPONENTS: any = [MrlfilterPipe];

@NgModule({
    imports: [
        PipeModuleModule.forRoot(),
        CommonModule,
        LayoutRoutingModule,
        TranslateModule,
        NgbDropdownModule,
        TranslateModule,
        FormsModule,
        ReactiveFormsModule

    ],
    exports: [PipeModuleModule],
    declarations: [LayoutComponent, SidebarComponent, HeaderComponent],
    providers: [PipeModuleModule, RestcontrollerService]
})
export class LayoutModule {}
