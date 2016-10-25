import {Component} from '@angular/core';
import {LunchDetailDto} from 'app/lunch/dto/types';
import {LunchItemComponent} from '../item/lunch-item.component';
import {LunchService} from '../../service/lunch.service';

@Component({
  selector: 'lunch-item',
  templateUrl: 'assets/javascripts/app/lunch/components/details/lunch-detail.component.html'
})
export class LunchDetailComponent {

  lunch: LunchDetailDto;
  lunchId: number;

  constructor(private lunchService: LunchService) {
  }

  ngAfterContentInit() {
    this.lunchService.getLunchDetails(this.lunchId);
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
