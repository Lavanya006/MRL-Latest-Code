import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { routerTransition } from '../router.animations';
import {RestcontrollerService} from 'src/app/restcontroller.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    animations: [routerTransition()]
})
export class LoginComponent implements OnInit {
    restcontroller: RestcontrollerService;
    router: Router;
    constructor(rcs: RestcontrollerService, private r:Router) {
        this.restcontroller = rcs;
        this.router = r;
    }

    ngOnInit() {}

    onLoggedin(username: string, password: string) {
        const response = this.restcontroller.getLogin(username, password);
        if(response.username === username){
            this.router.navigate(['/layout/dashboard']);
            localStorage.setItem('isLoggedin', 'isLoggedin');

        }else{
            this.router.navigate(['/access-denied']);
        }
    }
}
