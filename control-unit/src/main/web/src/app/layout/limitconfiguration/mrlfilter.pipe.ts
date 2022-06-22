import { Pipe, PipeTransform } from '@angular/core';
import {CommonModule} from '@angular/common';
import { Injectable} from '@angular/core';

@Pipe({
  name: 'filter',
  pure: false
})
@Injectable()
export class MrlfilterPipe implements PipeTransform {

  transform(mrllist: any, term: any[]): any {
    console.log(term);
    if (term === undefined) { return mrllist; }
    return mrllist.filter( mrlItem => mrlItem.crop.includes(term));
  }

}
