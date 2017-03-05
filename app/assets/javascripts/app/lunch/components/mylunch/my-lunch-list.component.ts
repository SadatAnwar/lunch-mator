import {Component, OnInit, Injectable} from '@angular/core';
import {LunchDto} from '../../../dto/types';
import {LunchService} from '../../../services/lunch.service';
import {AlertDisplay} from '../../../services/AlertDisplay';
import {AlertLevel} from '../../../common/types/Alert';

@Component({
  selector: 'my-lunch-list',
  templateUrl: 'assets/javascripts/app/lunch/components/mylunch/my-lunch-list.component.html'
})

@Injectable()
export class MyLunchListComponent extends AlertDisplay implements OnInit {
  lunchs: LunchDto[];

  constructor(private lunchService: LunchService) {
    super();
  }

  ngOnInit(): void {
    this.getMyLunchList();
  }

  private getMyLunchList() {
    this.lunchService.getMyLunchList()
      .subscribe((response: LunchDto[]) => {
        this.lunchs = response;
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, `Error:  ${error.text()}`, 3);
      });
  }

  requestLeave(lunch: LunchDto) {
    this.lunchService.requestLeave(lunch, (response: any) => {
      this.displayAlert(AlertLevel.SUCCESS, "Left lunch at " + lunch.restaurant.name, 3);
      this.ngOnInit();
    }, (error: any) => {
      this.displayAlert(AlertLevel.ERROR, `Error:  ${error}`, 3);
    });
  }
}
