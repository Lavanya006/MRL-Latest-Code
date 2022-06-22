import { Component, OnInit, Input } from '@angular/core';
import {RestcontrollerService} from 'src/app/restcontroller.service';


@Component({
  selector: 'app-nameconfiguration',
  templateUrl: './nameconfiguration.component.html',
  styleUrls: ['./nameconfiguration.component.scss'],
  providers: [RestcontrollerService]
})

export class NameconfigurationComponent implements OnInit {
  croplist: any = [];
  cropGrouplist: any = [];
  focuesdCropGroup: any;
  focusedCrop: any;
  @Input() alternativnamesInput: string;
  restcontroller: RestcontrollerService;

  constructor(rcs: RestcontrollerService) {
    this.restcontroller = rcs;
  }

  ngOnInit() {
    this.croplist = this.restcontroller.getAllCrop();
    this.cropGrouplist = this.restcontroller.getAllCropGroups();
  }
  selectCrop(value: any) {
    this.croplist.id = value;
    const selectedCrop = this.getCropfromList(value);
    this.focusedCrop = selectedCrop;
    const selectedInputForAlternativNames = document.getElementsByName('existingAlternativ')[0];
    selectedInputForAlternativNames.setAttribute('value', selectedCrop.otherNames);

  }

  selectGroupCrop(value: any) {
    this.croplist.id = value;
    const selectedCropGroup = this.getCropGroupfromList(value);
    this.focuesdCropGroup = selectedCropGroup;
  }

  public getCropfromList(name: string) {
  // tslint:disable-next-line:forin
    for (const i in this.croplist) {
        // tslint:disable-next-line:no-construct
        if (new String(this.croplist[i].id).valueOf() == new String(name).valueOf()) {
            return this.croplist[i];
        }
    }
  }

  public getCropGroupfromList(name: any) {
    // tslint:disable-next-line:forin
    for (const i in this.cropGrouplist) {
        if (new String(this.cropGrouplist[i].id).valueOf() == new String(name).valueOf()) {
            return this.cropGrouplist[i];
        }
    }
  }

  public saveAlternativNames() {
    const selectedInputForAlternativNames = (<HTMLInputElement>document.getElementById('existingAlternativ')).value;
    const selectedInputForNewAlternativNames = (<HTMLInputElement>document.getElementById('AddingAlternativ')).value;
    selectedInputForNewAlternativNames.trim();

    console.log(selectedInputForNewAlternativNames);
    this.getCropfromList(this.focusedCrop.id).otherNames = selectedInputForAlternativNames + ', ' + selectedInputForNewAlternativNames;
    this.restcontroller.updateCrop(this.focusedCrop);
    

  }

  /*public saveDependencies() {
    const selectedInputForCrops = (<HTMLInputElement>document.getElementById('existingDependencies')).value;
    const selectedInputForNewCrops = (<HTMLInputElement>document.getElementById('AddingDependencies')).value;
    selectedInputForNewCrops.trim();

    this.restcontroller.updateCropGroup(this.getCropGroupfromList(this.focuesdCropGroup.id).otherNames = selectedInputForCrops + ', ' + selectedInputForNewCrops);
  }*/

}
