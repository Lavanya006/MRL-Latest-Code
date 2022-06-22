import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login.component';
import {AccessDeniedComponent} from 'src/app/access-denied/access-denied.component';

const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path: '/access-denied',
        component: AccessDeniedComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoginRoutingModule {}
