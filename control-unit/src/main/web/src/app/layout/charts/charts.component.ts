import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';
import {RestcontrollerService} from 'src/app/restcontroller.service';

@Component({
    selector: 'app-charts',
    templateUrl: './charts.component.html',
    styleUrls: ['./charts.component.scss'],
    animations: [routerTransition()]
})
export class ChartsComponent implements OnInit {
    grabberlist: any = [];
    restcontroller: RestcontrollerService;
    forcedGrabber: any;

    constructor(rcs: RestcontrollerService) {
        this.restcontroller = rcs;
    }

    ngOnInit() {
        this.grabberlist = this.restcontroller.getAllGrabber();
    }

    selectGrabber(value: any){
        this.grabberlist.id = value;
        const selectedGrabber = this.getGrabberfromList(value);
        const lastExecutionResponse = this.restcontroller.getlastExecutionOfGrabber(value);
        this.forcedGrabber = selectedGrabber;
        (<HTMLInputElement>document.getElementById('lastExecution')).innerHTML = 'Last Execution on: ' +lastExecutionResponse.execution.dateTime;
    }

    public getGrabberfromList(name: string) {
        for (const i in this.grabberlist) {
            if (new String(this.grabberlist[i].id).valueOf() == new String(name).valueOf()) {
                return this.grabberlist[i];
            }
        }
    }

    public startGrabber(){
        if(this.forcedGrabber === null || this.forcedGrabber === undefined){
            return null;
        }else{
            this.restcontroller.startGrabber(this.forcedGrabber);
        }
    }
}
