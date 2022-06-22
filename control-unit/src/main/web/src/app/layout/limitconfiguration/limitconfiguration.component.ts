import { Component, OnInit, NgModule } from '@angular/core';
import { MrllistfilterPipe } from 'src/app/mrllistfilter.pipe';
import {TranslateService} from '@ngx-translate/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {PipeModuleModule} from 'src/pipe-module/pipe-module.module';
import {RestcontrollerService} from 'src/app/restcontroller.service';


@Component({
  selector: 'app-limitconfiguration',
  templateUrl: './limitconfiguration.component.html',
  styleUrls: ['./limitconfiguration.component.scss']
})

@NgModule({
  imports: [FormsModule, CommonModule, ReactiveFormsModule, PipeModuleModule.forRoot()],
  providers: [PipeModuleModule]
})
export class LimitconfigurationComponent implements OnInit {
  // tslint:disable-next-line:max-line-length
  mrllist: any = [];
  // tslint:disable-next-line:max-line-length
  sourcemrllist: any = [];
  availableCrops: any;
  focusedCrop: any;
  focusedCountry: any;
  focusedSubstance: any;
  availableCountries: any[];
  availableSubstances: any;
  public term = null;
  mrllistfilter: MrllistfilterPipe;
  restcontroller: RestcontrollerService;

  constructor(ppm: PipeModuleModule, rcs: RestcontrollerService) {
    this.restcontroller = rcs;
  }


  ngOnInit() {

    this.mrllist = this.restcontroller.getAllMeasurements();
    this.sourcemrllist = this.restcontroller.getAllMeasurements();
  }
  selectCrop(value: any) {
    // this.mrllist.id = value;
    const selectedCrop = this.getCropfromList(value);
    this.focusedCrop = selectedCrop;
    //this.mrllist = new MrllistfilterPipe().transformCrop(this.mrllist, this.focusedCrop.crop);
  }

  public getCropfromList(name: string) {
    // tslint:disable-next-line:forin
    for (const i in this.mrllist) {
        if (this.mrllist[i].crop.id.toString().localeCompare(name) === 0) {
            return this.mrllist[i];
        }
    }
  }

  public getCrountryfromList(name: string) {
    // tslint:disable-next-line:forin
    // tslint:disable-next-line:forin
    for (const i in this.mrllist) {
        if (this.mrllist[i].originCountry.name.localeCompare(name) === 0) {
            return this.mrllist[i];
        }
      }
    }

  public getSubstancefromList(name: string) {
// tslint:disable-next-line:forin
    for (const i in this.mrllist) {
        if (this.mrllist[i].substance.name.localeCompare(name) === 0) {
            return this.mrllist[i];
        }
      }
    }

  selectCountry(value: any) {
    // this.mrllist.id = value;
    const selectedCrountry = this.getCrountryfromList(value);
    this.focusedCountry = selectedCrountry;
    // this.mrllist = this.mrlfilter.transform(this.mrllist, selectedCrountry.country);
  }

  selectSubstance(value: any) {
    const selectedSubstance = this.getSubstancefromList(value);
    this.focusedSubstance = selectedSubstance;
    // this.mrllist = this.mrlfilter.transform(this.mrllist, selectedSubstance.substance);
  }

}
