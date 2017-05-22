import {Component} from '@angular/core';
import {CompleterService, CompleterData} from 'ng2-completer';
import {ErrorDetail} from '../../common/types/ErrorDetail';
import {ErrorMapper} from '../../mappers/ErrorMapper';
import {AlertService} from '../../services/alert.service';
import {RestaurantService} from '../../services/restaurant.services';
@Component({
  selector: 'add-restaurant',
  templateUrl: './add-restaurant.component.html'
})
export class AddRestaurantComponent {
  waiting: boolean = false;
  restaurantName: string;
  website: string;
  description: string;
  error: ErrorDetail;
  dataService: CompleterData;

  constructor(private alertService: AlertService, private completerService: CompleterService, private restaurantService: RestaurantService) {
    this.dataService = completerService.remote("/rest/restaurants/search/", "name", 'name');
  }

  add() {
    if (this.restaurantName == null || this.restaurantName.length == 0) {
      this.alertService.error("Restaurant must have a name")
    }
    let restaurantDto = {name: this.restaurantName, website: this.website, description: this.description};
    this.restaurantService.add(restaurantDto)
      .subscribe(() => {
        this.waiting = false;
        this.alertService.success("Restaurant added");
        this.reset();
      }, (error: any) => {
        this.waiting = false;
        this.alertService.error(ErrorMapper.map(error).message)
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
