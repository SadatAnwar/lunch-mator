import {Component, OnInit} from '@angular/core';
import {Response} from '@angular/http';
import {ActivatedRoute, Params} from '@angular/router';
import {AlertService} from '../../services/alert.service';
import {CalenderService} from '../../services/calander.service';
import {LunchService} from '../../services/lunch.service';
import {LunchDetailDto, ParticipantDto} from '../../types';

@Component({
  selector: 'lunch-detail',
  templateUrl: './lunch-detail.component.html'
})
export class LunchDetailComponent implements OnInit {

  lunch: LunchDetailDto;
  startTime: string;
  title: string;
  hasMap = false;

  constructor(private route: ActivatedRoute,
              private alertService: AlertService,
              private calenderService: CalenderService,
              private service: LunchService) {
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
          this.alertService.error(`Error:  ${error.text()}`);
        });
    });
  }

  join(lunch: LunchDetailDto) {
    this.service.requestJoin(lunch,
      (response: any) => {
        this.alertService.success(`Joined lunch at ${lunch.restaurant.name}`);
        this.calenderService.createCalander(lunch.lunchName, lunch.restaurant.name, lunch.restaurant.website, new Date(lunch.startTime));
        this.ngOnInit();
      }, (error: any) => {
        this.alertService.error(`Error:  ${error.text()}`);
      });
  }

  leave(lunch: LunchDetailDto) {
    this.service.requestLeave(lunch, (response: any) => {
      this.alertService.success("Left lunch at " + lunch.restaurant.name);
      this.ngOnInit();
    }, (error: any) => {
      this.alertService.error(`Error:  ${error}`);
    });
  }
}
