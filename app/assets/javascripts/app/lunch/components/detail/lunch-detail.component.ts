import {Component, OnInit} from '@angular/core';
import {LunchDetailDto, ParticipantDto} from '../../../dto/types';
import {ActivatedRoute, Params} from '@angular/router';
import {LunchService} from '../../../services/lunch.service';
import {AlertLevel} from '../../../common/types/Alert';
import {AlertDisplay} from '../../../services/AlertDisplay';
import {CalenderService} from '../../../services/calander.service';
import {Response} from '@angular/http';

@Component({
  selector: 'lunch-detail',
  templateUrl: 'assets/javascripts/app/lunch/components/detail/lunch-detail.component.html'
})
export class LunchDetailComponent extends AlertDisplay implements OnInit {

  private lunch: LunchDetailDto;
  private startTime: string;
  private title: string;
  private hasMap = false;

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
