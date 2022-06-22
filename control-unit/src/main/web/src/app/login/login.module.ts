import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MrlfilterPipe} from 'src/app/layout/limitconfiguration/mrlfilter.pipe';


import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import { AccessDeniedModule} from 'src/app/access-denied/access-denied.module';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule,
        LoginRoutingModule, AccessDeniedModule],
    declarations: [LoginComponent]
})
export class LoginModule {}
