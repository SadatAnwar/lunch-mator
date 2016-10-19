import {Component} from "@angular/core";
import {CompleterService, CompleterData, CompleterItem} from "ng2-completer";
import {AlertDisplay} from "../../../common/services/AlertDisplay";
import {AlertLevel} from "../../../common/types/Alert";
import {RestaurantDto} from "../../dto/types";
import {ErrorMapper} from "../../../mappers/ErrorMapper";
import {LunchService} from "../../service/lunch.service";

@Component({
  selector: 'add-lunch',
  templateUrl: 'assets/javascripts/app/lunch/components/addlunch/add-lunch.component.html'
})

export class AddLunchComponent extends AlertDisplay {
  waiting: boolean = false;
  lunchName: string;
  restaurantName: string;
  startTime: number;
  maxSize: number;
  dataService: CompleterData;
  randomWord: string = "an awesome";
  selectedRestaurant: RestaurantDto;
  anonymous: boolean;

  constructor(private completerService: CompleterService, private lunchService: LunchService) {
    super();
    this.dataService = completerService.remote("/rest/restaurants/search/", "name", 'name');
  }

  createTable() {
    this.displayAlert(AlertLevel.SUCCESS, "Yippee!!", 5);
    let createLunchDto = {
      restaurantId: this.selectedRestaurant.id,
      lunchName: this.lunchName,
      startTime: this.startTime,
      anonymous: this.anonymous,
      maxSize: this.maxSize
    };
    console.log(createLunchDto);
    this.lunchService.createLunch(createLunchDto)
      .subscribe((response: any) => {
        this.waiting = false;
        this.displaySuccessWithTimeOut("Restaurant added", 3);
        this.reset();
      }, (error: any) => {
        this.waiting = false;
        this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error))
      });
    this.waiting = true;
  }

  reset() {
    this.lunchName = null;
    this.restaurantName = null;
    this.startTime = null;
    this.maxSize = null;
  }

  select(selected: CompleterItem) {
    this.selectedRestaurant = selected.originalObject;
  }
}
