import {Component, OnInit, Injectable} from "@angular/core";
import {LunchDto} from "app/lunch/dto/types";
import {LunchService} from "../../service/lunch.service";
import {AlertLevel} from "../../../common/types/Alert";
import {ErrorMapper} from "../../../mappers/ErrorMapper";
import {AlertDisplay} from "../../../common/services/AlertDisplay";
import {CalenderService} from "../../service/calander.service";

@Component({
  selector: 'lunch-list',
  templateUrl: 'assets/javascripts/app/lunch/components/list/lunch-list.component.html'
})

@Injectable()
export class LunchListComponent extends AlertDisplay implements OnInit {
  lunchList: LunchDto[];
  time: string;

  constructor(private lunchService: LunchService, private calenderService: CalenderService) {
    super();
  }

  ngOnInit(): void {
    this.getLunchList();
  }

  private getLunchList() {
    let lunch = this.lunchService.getLunchList()
      .subscribe((response: LunchDto[]) => {
        console.log(response);
        this.lunchList = response;
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error).message)
      });
    console.log(lunch);
  }

  join(lunch: LunchDto) {
    console.log(lunch);
    this.lunchService.join(lunch.id)
      .subscribe((response: any) => {
        console.log(response);
        this.displayAlert(AlertLevel.SUCCESS, "Joined lunch at " + lunch.restaurant.name, 3);
        this.calenderService.createCalander(lunch.lunchName, lunch.restaurant.name, lunch.restaurant.website, new Date(lunch.startTime));
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, "Error while joining lunch, make sure you are not already joined", 3)
      });
  }
}
