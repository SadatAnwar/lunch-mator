import {Component} from "@angular/core";
import {CompleterService, CompleterData} from "ng2-completer";
import {ErrorMapper} from "../../../mappers/ErrorMapper";
import {RestaurantService} from "../../services/restaurant.services";
import {AlertDisplay} from "../../../common/services/AlertDisplay";
import {ErrorDetail} from "../../../common/types/ErrorDetail";
import {AlertLevel} from "../../../common/types/Alert";

@Component({
  selector: 'add-restaurant',
  templateUrl: 'assets/javascripts/app/restaurant/components/addrestaurant/add-restaurant.component.html'
})
export class AddRestaurantComponent extends AlertDisplay {
  waiting: boolean = false;
  restaurantName: string;
  website: string;
  description: string;
  error: ErrorDetail;
  dataService: CompleterData;

  constructor(private completerService: CompleterService, private restaurantService: RestaurantService) {
    super();
    this.dataService = completerService.remote("/rest/restaurants/search/", "name", 'name');
  }

  add() {
    if (this.restaurantName == null || this.restaurantName.length == 0) {
      this.displayAlert(AlertLevel.ERROR, "Restaurant must have a name")
    }
    var restaurantDto = {name: this.restaurantName, website: this.website, description: this.description};
    console.log(restaurantDto);
    this.restaurantService.add(restaurantDto)
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
    this.restaurantName = null;
    this.description = null;
    this.website = null;
    this.error = null;
  }
}
