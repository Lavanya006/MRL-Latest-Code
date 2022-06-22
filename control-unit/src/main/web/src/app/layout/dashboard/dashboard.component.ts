import { Component, OnInit, Inject } from '@angular/core';
import { routerTransition } from '../../router.animations';
import {User} from 'src/app/model/user';
import { RestcontrollerService } from 'src/app/restcontroller.service';


@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    animations: [ routerTransition()]
})
export class DashboardComponent implements OnInit {
    restcontroller: RestcontrollerService;
    constructor(rcs: RestcontrollerService) {
        this.sliders.push(
            {
                imagePath: 'assets/images/slider1.jpg',
                label: 'First slide label',
                text:
                    'Nulla vitae elit libero, a pharetra augue mollis interdum.'
            },
            {
                imagePath: 'assets/images/slider2.jpg',
                label: 'Second slide label',
                text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
            },
            {
                imagePath: 'assets/images/slider3.jpg',
                label: 'Third slide label',
                text:
                    'Praesent commodo cursus magna, vel scelerisque nisl consectetur.'
            }
        );

        this.alerts.push(
            {
                id: 1,
                type: 'success',
                message: `Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Voluptates est animi quibusdam praesentium quam, et perspiciatis,
                consectetur velit culpa molestias dignissimos
                voluptatum veritatis quod aliquam! Rerum placeat necessitatibus, vitae dolorum`
            },
            {
                id: 2,
                type: 'warning',
                message: `Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Voluptates est animi quibusdam praesentium quam, et perspiciatis,
                consectetur velit culpa molestias dignissimos
                voluptatum veritatis quod aliquam! Rerum placeat necessitatibus, vitae dolorum`
            }
        );
        this.restcontroller = rcs;
        

    }
    public alerts: Array<any> = [];
    public sliders: Array<any> = [];
    public userliste: any[];

    //public entries: any = [{'username': 'Xenia', 'admin': true, 'data': false, 'analyst': false}, {'username': 'Michael', 'admin': false, 'data': true, 'analyst': true}, {'username': 'Marina', 'admin': false, 'data': false, 'analyst': false}];

    ngOnInit() {
        this.userliste = this.restcontroller.getAllUser();
        console.log(this.userliste);

    }

    public closeAlert(alert: any) {
        const index: number = this.alerts.indexOf(alert);
        this.alerts.splice(index, 1);
    }
    public changeAdmin(event) {
        const target = event.target;
        const id = target.attributes.id;
        // tslint:disable-next-line:forin
        for (const i in this.userliste) {
            if (this.userliste[i].username.localeCompare(id.value) === 0) {
                if (this.userliste[i].administrator === true) {
                    this.userliste[i].administrator = false;
                } else {
                    this.userliste[i].administrator = true;
                }
            }
        }
    }
    public changeData(event) {
        const target = event.target;
        const id = target.attributes.id;
        // tslint:disable-next-line:forin
        for (const i in this.userliste) {
            if (this.userliste[i].username.localeCompare(id.value) === 0) {
                if (this.userliste[i].datamanager === true) {
                    this.userliste[i].datamanager = false;
                } else {
                    this.userliste[i].datamanager = true;
                }
            }
        }

    }
    public changeAnalyst(event) {
        const target = event.target;
        const id = target.attributes.id;
        for (const i in this.userliste) {
            if (this.userliste[i].username.localeCompare(id.value) === 0) {
                if (this.userliste[i].analyst === true) {
                    this.userliste[i].analyst = false;
                } else {
                    this.userliste[i].analyst = true;
                }
            }
        }
    }

    public saveUser(event) {
        const target = event.target;
        const id = target.attributes.id;
        const i = this.getUserfromList(id.value);
        const usertosave = new User(this.userliste[i].id, this.userliste[i].username, this.userliste[i].password, this.userliste[i].administrator, this.userliste[i].datamanager, this.userliste[i].analyst);
        this.restcontroller.updateUser(usertosave);
        window.location.reload();

    }
    public cancelUser(event): void {
        const target = event.target;
        const id = target.attributes.id;
        const i = this.getUserfromList(id.value);
        const usertodelete = new User(this.userliste[i].id, this.userliste[i].username, this.userliste[i].password, this.userliste[i].administrator, this.userliste[i].datamanager, this.userliste[i].analyst);
        this.restcontroller.deleteUser(usertodelete);
        window.location.reload();
    }

    public getUserfromList(name: string) {
        for (const i in this.userliste) {
            if (this.userliste[i].username.localeCompare(name) === 0) {
                return i;
            }
        }
    }

    }
    /*@Component({
        selector: 'dialog-overview-example-dialog',
        templateUrl: 'dialog-overview-example-dialog.html',
      })
      export class DialogOverviewExampleDialog {

        constructor(
          public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
          @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

        onNoClick(): void {
          this.dialogRef.close();
        }

      }*/

