import {Component} from '@angular/core';
import {CompleterService, CompleterData, CompleterItem} from 'ng2-completer';
import {ErrorMapper} from '../../../mappers/ErrorMapper';
import {RestaurantService} from '../../services/restaurant.services';
import {AlertDisplay} from '../../../common/services/AlertDisplay';
import {ErrorDetail} from '../../../common/types/ErrorDetail';

@Component({
  selector: 'add-restaurant',
  templateUrl: 'assets/javascripts/app/restaurant/components/addrestaurant/addrestaurant.component.html'
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
    console.log(this.restaurantName);
    var restaurantDto = {name: this.restaurantName, website: this.website, description: this.description};
    console.log(restaurantDto);
    this.restaurantService.add(restaurantDto)
      .subscribe((response: any) => {
        this.waiting = false;
        this.displaySuccessWithTimeOut("Yippee!!", 2.5);
        this.reset();
      }, (error: any) => {
        this.waiting = false;
        this.error = ErrorMapper.map(error);
      });
    this.waiting = true;
  }

  reset(){
    this.restaurantName = null;
    this.description = null;
    this.website = null;
  }

  select(selected: CompleterItem) {
    console.log(selected);
    console.log(selected.title);
  }
}
