import {Injectable} from '@angular/core';
declare var ics: any;

@Injectable()
export class CalenderService {

  createCalander(lunchName: string, restaurantName: string, website: string = "", start: Date) {
    var cal = ics();
    let end = this.getEndDateTime(start);
    cal.addEvent('Lunch at ' + restaurantName, "Lunch-mator lunch", website, start, end);
    cal.download("lunch-mator");
  }

  private getEndDateTime(start: Date): Date {
    let endDate = new Date(start.getTime());
    let endHour = endDate.getHours() + 1;
    endDate.setHours(endHour);
    return endDate;
  }
}
