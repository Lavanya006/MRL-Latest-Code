import { Component, OnInit } from '@angular/core';
import {RestcontrollerService} from 'src/app/restcontroller.service';

@Component({
    selector: 'app-layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

    collapedSideBar: boolean;


    constructor() {
        
    }

    ngOnInit() {}

    receiveCollapsed($event) {
        this.collapedSideBar = $event;
    }

}
