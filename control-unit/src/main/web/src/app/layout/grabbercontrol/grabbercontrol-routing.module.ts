import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {GrabbercontrolComponent} from 'src/app/layout/grabbercontrol/grabbercontrol.component';

const routes: Routes = [
  {
      path: '/grabbercontrol', component: GrabbercontrolComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GrabbercontrolRoutingModule { }
