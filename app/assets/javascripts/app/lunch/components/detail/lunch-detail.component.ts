import {Component} from '@angular/core';
import {LunchDetailDto} from 'app/lunch/dto/types';
import {Router, ActivatedRoute, Params} from '@angular/router';
import {LunchService} from '../../service/lunch.service';
import {AlertLevel} from '../../../common/types/Alert';
import {AlertDisplay} from '../../../common/services/AlertDisplay';

@Component({
  selector: 'lunch-detail',
  templateUrl: 'assets/javascripts/app/lunch/components/detail/lunch-detail.component.html'
})
export class LunchDetailComponent extends AlertDisplay {

  lunch: LunchDetailDto;
  lunchId: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: LunchService) {
    super();
    console.log("in constructor");
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.lunchId = +params['id']; // (+) converts string 'id' to a number
      console.log("id is: " + this.lunchId);
      this.service.getLunchDetails(this.lunchId)
        .subscribe((response: LunchDetailDto) => {
          console.log(response);
          this.lunch = response;
        }, (error: any) => {
          this.displayAlert(AlertLevel.ERROR, error)
        });
    });
  }

  join(lunch: LunchDetailDto) {
    console.log("joining lunchId:" + this.lunchId);
    this.service.join(this.lunchId)
      .subscribe((response: any) => {
        console.log(response);
        this.displayAlert(AlertLevel.SUCCESS, "Joined lunch at " + lunch.restaurant.name, 3);
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, "Error while joining lunch, make sure you are not already joined", 3)
      });
  }
}
