import {Injectable} from "@angular/core";
declare var ics: any;

@Injectable()
export class CalenderService {
  static days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  createCalander(lunchName: string, restaurantName: string, website: string = "", start: Date) {
    var cal = ics();
    let end = this.getEndDateTime(start);
    cal.addEvent('Lunch at ' + restaurantName, "Lunch-mator lunch", website, start, end);
    cal.download("lunch-mator");
  }

  format(date: Date) {
    let mm = date.getMonth() + 1; // getMonth() is zero-based
    let dd = date.getDate();
    let HH = date.getHours();
    let MM = date.getMinutes();
    let mmdd = [dd, mm].join('.');
    let hhmm = [HH, MM].join(':');
    return mmdd + " (" + CalenderService.days[date.getDay()] + ") " + hhmm;
  }

  private getEndDateTime(start: Date): Date {
    let endDate = new Date(start.getTime());
    let endHour = endDate.getHours() + 1;
    endDate.setHours(endHour);
    return endDate;
  }
}
