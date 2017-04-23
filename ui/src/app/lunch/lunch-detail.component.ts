import {Component, OnInit} from '@angular/core';
import {Response} from '@angular/http';
import {LunchDetailDto, ParticipantDto} from '../types';
import {ActivatedRoute, Params} from '@angular/router';
import {LunchService} from '../services/lunch.service';
import {AlertLevel} from '../common/types/Alert';
import {AlertDisplay} from '../services/AlertDisplay';
import {CalenderService} from '../services/calander.service';

@Component({
  selector: 'lunch-detail',
  templateUrl: 'lunch-detail.component.html'
})
export class LunchDetailComponent extends AlertDisplay implements OnInit {

  lunch: LunchDetailDto;
  startTime: string;
  title: string;
  hasMap = false;

  constructor(private route: ActivatedRoute,
              private calenderService: CalenderService,
              private service: LunchService) {
    super();
  }

  ngOnInit() {
    this.route.params.map((params: Params) => {
      return params['id'];
    }).subscribe((lunchID: number) => {
      this.service.getLunchDetails(lunchID)
        .subscribe((lunchDetail: LunchDetailDto) => {
          this.lunch = lunchDetail;
          this.title = `${lunchDetail.lunchName ? lunchDetail.lunchName : "Lunch"} at ${lunchDetail.restaurant.name}`;
          this.startTime = this.calenderService.format(new Date(this.lunch.startTime));
          if (lunchDetail.restaurant.website && lunchDetail.restaurant.website.startsWith('http')) {
            this.hasMap = true;
          }
          this.service.getParticipants(lunchID).subscribe((participants: ParticipantDto[]) => {
            this.lunch.participants = participants;
          })
        }, (error: Response) => {
          this.displayAlert(AlertLevel.ERROR, `Error:  ${error.text()}`);
        });
    });
  }

  join(lunch: LunchDetailDto) {
    this.service.requestJoin(lunch,
      (response: any) => {
        this.displayAlert(AlertLevel.SUCCESS, `Joined lunch at ${lunch.restaurant.name}`, 3);
        this.calenderService.createCalander(lunch.lunchName, lunch.restaurant.name, lunch.restaurant.website, new Date(lunch.startTime));
        this.ngOnInit();
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, `Error:  ${error.text()}`, 3);
      });
  }

  leave(lunch: LunchDetailDto) {
    this.service.requestLeave(lunch, (response: any) => {
      this.displayAlert(AlertLevel.SUCCESS, "Left lunch at " + lunch.restaurant.name, 3);
      this.ngOnInit();
    }, (error: any) => {
      this.displayAlert(AlertLevel.ERROR, `Error:  ${error}`, 3);
    });
  }
}
