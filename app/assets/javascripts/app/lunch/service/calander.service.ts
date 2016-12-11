import {Injectable} from '@angular/core';
declare var ics: any;

@Injectable()
export class CalenderService {
  static DAYS = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  createCalander(lunchName: string, restaurantName: string, website: string = "", start: Date) {
    var cal = ics();
    let end = this.getEndDateTime(start);
    cal.addEvent('Lunch at ' + restaurantName, "Lunch-mator lunch", website, start, end);
    if (confirm("want to download an appointment for your calender?")) {
      cal.download("lunch-mator");
    }
  }

  format(date: Date) {
    let mm = this.format2Digit(date.getMonth() + 1); // getMonth() is zero-based
    let dd = this.format2Digit(date.getDate());
    let HH = this.format2Digit(date.getHours());
    let MM = this.format2Digit(date.getMinutes());
    let mmdd = [dd, mm].join('.');
    let hhmm = [HH, MM].join(':');
    return mmdd + " (" + CalenderService.DAYS[date.getDay()] + ") " + hhmm;
  }

  private getEndDateTime(start: Date): Date {
    let endDate = new Date(start.getTime());
    let endHour = endDate.getHours() + 1;
    endDate.setHours(endHour);
    return endDate;
  }

  private format2Digit(n: number): string {
    return ("0" + n).slice(-2);
  }
}
