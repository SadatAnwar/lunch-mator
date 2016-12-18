import {Component, OnInit, Injectable} from '@angular/core';
import {LunchDto} from 'app/lunch/dto/types';
import {LunchService} from '../../service/lunch.service';
import {AlertLevel} from '../../../common/types/Alert';
import {AlertDisplay} from '../../../common/services/AlertDisplay';
import {CalenderService} from '../../service/calander.service';

@Component({
  selector: 'lunch-list',
  templateUrl: 'assets/javascripts/app/lunch/components/list/lunch-list.component.html'
})

@Injectable()
export class LunchListComponent extends AlertDisplay implements OnInit {
  lunchList: LunchDto[];
  time: string;
  waiting: boolean;

  constructor(private lunchService: LunchService, private calenderService: CalenderService) {
    super();
  }

  ngOnInit(): void {
    this.getLunchList();
  }

  private getLunchList() {
    this.waiting = true;
    this.lunchService.getLunchList()
      .subscribe((response: LunchDto[]) => {
        this.lunchList = response;
        this.waiting = false;
      }, (error: any) => {
        this.waiting = false;
        this.displayAlert(AlertLevel.ERROR, `Error retrieving available lunch: [${error}]`, 5)
      });
  }

  join(lunch: LunchDto) {
    this.lunchService.requestJoin(lunch,
      (response: any) => {
        this.displayAlert(AlertLevel.SUCCESS, `Joined lunch at ${lunch.restaurant.name}`, 3);
        this.calenderService.createCalander(lunch.lunchName, lunch.restaurant.name, lunch.restaurant.website, new Date(lunch.startTime));
        this.ngOnInit();
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, `Error: [${error}], make sure you are not already joined`, 3)
      });
  }

  leave(lunch: LunchDto) {
    this.lunchService.requestLeave(lunch, (response: any) => {
      this.displayAlert(AlertLevel.SUCCESS, "Left lunch at " + lunch.restaurant.name, 3);
      this.ngOnInit();
    }, (error: any) => {
      this.displayAlert(AlertLevel.ERROR, `Error:  ${error}`, 3);
    });
  }
}
