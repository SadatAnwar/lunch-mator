import {Component, Input, Output, EventEmitter} from '@angular/core';
import {LunchDto} from 'app/lunch/dto/types';
import {LunchListComponent} from '../list/lunch-list.component';

@Component({
  selector: 'lunch-item',
  templateUrl: 'assets/javascripts/app/lunch/components/item/lunch-item.component.html'
})
export class LunchItemComponent {
  static days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  @Input()
  lunch: LunchDto;

  startTime: string;

  constructor(private lunchComponent: LunchListComponent) {
  }

  ngAfterContentInit() {
    this.startTime = this.format(new Date(this.lunch.startTime));
    console.log(this.startTime);
  }

  join(lunch: LunchDto) {
    this.lunchComponent.join(lunch);
  }

  format(date: Date) {
    let mm = date.getMonth() + 1; // getMonth() is zero-based
    let dd = date.getDate();
    let HH = date.getHours();
    let MM = date.getMinutes();
    let mmdd = [dd, mm].join('.');
    let hhmm = [HH, MM].join(':');
    return mmdd + " (" + LunchItemComponent.days[date.getDay()] + ") " + hhmm;
  }

}
