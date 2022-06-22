import { Pipe, PipeTransform, NgModule, Injectable } from '@angular/core';

@Pipe({
  name: 'mrllistfilter'
})
@Injectable({
  providedIn: 'root',
})

export class MrllistfilterPipe implements PipeTransform {
  private originalmrlfilterPipe: MrllistfilterPipe;
  constructor() {
    // this.originalmrlfilterPipe = new MrllistfilterPipe();
  }

  /*transform(mrllist: any[]): any {
    const term = null;
    if (term === '' || term === null) { return mrllist; }
    return mrllist.filter(function(crop) {
      return crop === term.crop;
    });
  }*/

  transform(mrllist: any[], term: any[]): any[] {
    let filteredlist: any = null;
    if (!mrllist) {
      return [];
    }
    if (!term[0]) {
      return mrllist;
    }
    if (!term[1] || term[1] === undefined) {
      return mrllist = mrllist.filter( mrlItem => mrlItem.crop.name.includes(term[0].crop.name));
    }
    if (mrllist.length > 0) {
      mrllist = mrllist.filter( mrlItem => mrlItem.crop.name.includes(term[0].crop.name));
      filteredlist = mrllist.filter( mrlItem => mrlItem.originCountry.name.includes(term[1].originCountry.name));
      }
      if (term[2] === undefined) { return filteredlist; } else {
          filteredlist = mrllist.filter( mrlItem => mrlItem.substance.name.includes(term[2].substance.name));
      }
      
      return filteredlist;
    }
    /*if (term[0] === '' || term[0] === null || term[0] === undefined) { return mrllist; }
    return mrllist.filter(function(crop) {
      if (crop.crop === term) {return term; }
    });*/
  }

