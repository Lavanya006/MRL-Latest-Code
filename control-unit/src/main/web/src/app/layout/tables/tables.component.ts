import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';

@Component({
    selector: 'app-tables',
    templateUrl: './tables.component.html',
    styleUrls: ['./tables.component.scss'],
    animations: [routerTransition()]
})
export class TablesComponent implements OnInit {
    nameconfig = true;
    limitconfig = false;
    importconfig = false;
    nameElement: any;
    limitElement: any;
    importElement: any;
    constructor() {}

    ngOnInit() {
        this.nameElement = document.getElementsByName('nameconfig');
        this.limitElement = document.getElementsByName('limitconfig');
        //this.importElement = document.getElementsByName('importconfig');
        this.nameElement[0].setAttribute('class', 'nav-item nav-link active');
        this.limitElement[0].setAttribute('class', 'nav-item nav-link');
        //this.importElement[0].setAttribute('class', 'nav-item nav-link');
    }

    nameconfigAnzeigen() {
      this.nameconfig = true;
      this.limitconfig = false;
      this.importconfig = false;
      this.nameElement[0].setAttribute('class', 'nav-item nav-link active');
      this.limitElement[0].setAttribute('class', 'nav-item nav-link');
      //this.importElement[0].setAttribute('class', 'nav-item nav-link');
      return this.nameconfig;

    }
    limitconfigAnzeigen() {
      this.nameconfig = false;
      this.limitconfig = true;
      this.importconfig = false;
      this.nameElement[0].setAttribute('class', 'nav-item nav-link');
      this.limitElement[0].setAttribute('class', 'nav-item nav-link active');
      //this.importElement[0].setAttribute('class', 'nav-item nav-link');
      return this.limitconfig;

    }
    importconfigAnzeigen() {
      this.nameconfig = false;
      this.limitconfig = false;
      this.importconfig = true;
      this.nameElement[0].setAttribute('class', 'nav-item nav-link');
      this.limitElement[0].setAttribute('class', 'nav-item nav-link');
      //this.importElement[0].setAttribute('class', 'nav-item nav-link active');
      return this.importconfig;

    }

}
