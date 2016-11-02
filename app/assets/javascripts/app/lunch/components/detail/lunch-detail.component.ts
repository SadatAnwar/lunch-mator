import {Component} from "@angular/core";
import {LunchDetailDto} from "app/lunch/dto/types";
import {ActivatedRoute, Params} from "@angular/router";
import {LunchService} from "../../service/lunch.service";
import {AlertLevel} from "../../../common/types/Alert";
import {AlertDisplay} from "../../../common/services/AlertDisplay";
import {CalenderService} from "../../service/calander.service";

@Component({
  selector: 'lunch-detail',
  templateUrl: 'assets/javascripts/app/lunch/components/detail/lunch-detail.component.html'
})
export class LunchDetailComponent extends AlertDisplay {

  lunch: LunchDetailDto;
  startTime: string;
  lunchId: number;

  constructor(private route: ActivatedRoute,
              private calenderService: CalenderService,
              private service: LunchService) {
    super();
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.lunchId = +params['id']; // (+) converts string 'id' to a number
      this.service.getLunchDetails(this.lunchId)
        .subscribe((response: LunchDetailDto) => {
          this.lunch = response;
          this.startTime = this.calenderService.format(new Date(this.lunch.startTime));
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
        this.ngOnInit();
        this.displayAlert(AlertLevel.SUCCESS, "Joined lunch at " + lunch.restaurant.name, 3);
        this.calenderService.createCalander(lunch.lunchName, lunch.restaurant.name, lunch.restaurant.website, new Date(lunch.startTime));
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, "Error while joining lunch, make sure you are not already joined", 3)
      });
  }
}
