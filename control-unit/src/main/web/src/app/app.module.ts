import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LanguageTranslationModule } from './shared/modules/language-translation/language-translation.module';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {LimitconfigurationComponent} from 'src/app/layout/limitconfiguration/limitconfiguration.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthGuard } from './shared';
import { PipeModuleModule } from 'src/pipe-module/pipe-module.module';
import {RestcontrollerService} from 'src/app/restcontroller.service';

import {TranslateHttpLoader} from '@ngx-translate/http-loader';

// AoT requires an exported function for factories
export function HttpLoaderFactory(httpClient: HttpClient) {
    return new TranslateHttpLoader(httpClient);
  }

@NgModule({
    imports: [
      BrowserModule,
      BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        CommonModule,
        HttpClientModule,
        LanguageTranslationModule,
        AppRoutingModule,
        TranslateModule.forRoot({
            loader: {
              provide: TranslateLoader,
              useFactory: HttpLoaderFactory,
              deps: [HttpClient]
            }
          })

          // MaterialModule.forRoot(),
    ],
    exports: [TranslateModule, PipeModuleModule],
    declarations: [AppComponent],
    providers: [AuthGuard, PipeModuleModule, RestcontrollerService],
    bootstrap: [AppComponent]
})
export class AppModule {}
