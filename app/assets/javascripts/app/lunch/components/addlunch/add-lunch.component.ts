import {Component} from '@angular/core';
import {CompleterService, CompleterData, CompleterItem} from 'ng2-completer';
import {AlertDisplay} from '../../../common/services/AlertDisplay';
import {AlertLevel} from '../../../common/types/Alert';
import {RestaurantDto, CreateLunchDto} from '../../dto/types';
import {ErrorMapper} from '../../../mappers/ErrorMapper';
import {LunchService} from '../../service/lunch.service';

@Component({
  selector: 'add-lunch',
  templateUrl: 'assets/javascripts/app/lunch/components/addlunch/add-lunch.component.html'
})

export class AddLunchComponent extends AlertDisplay {
  waiting: boolean = false;
  lunchName: string = "";
  restaurantName: string = "";
  startTime: string ;
  startDate: string ;
  maxSize: number = 0;
  dataService: CompleterData;
  randomWord: string = "an awesome";
  selectedRestaurant: RestaurantDto;
  anonymous: boolean = false;

  constructor(private completerService: CompleterService, private lunchService: LunchService) {
    super();
    this.dataService = completerService.remote("/rest/restaurants/search/", "name", 'name');
  }

  createTable() {
    if (!this.selectedRestaurant) {
      this.displayAlert(AlertLevel.ERROR, "Restaurant cant be null <a href='/'> here</a>>");
      return;
    }
    let createLunchDto = {
      restaurantId: this.selectedRestaurant.id,
      lunchName: this.lunchName,
      startTime: new Date(this.startDate+'T'+this.startTime).getMilliseconds(),
      anonymous: this.anonymous,
      maxSize: this.maxSize
    };

    if (!this.validateForm(createLunchDto)) {
      return;
    }
    console.log(createLunchDto);
    this.lunchService.createLunch(createLunchDto)
      .subscribe((response: any) => {
        this.waiting = false;
        this.displayAlert(AlertLevel.SUCCESS, "Lunch started", 3);
        this.reset();
      }, (error: any) => {
        this.waiting = false;
        this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error).message)
      });
    this.waiting = true;
  }

  reset() {
    this.lunchName = null;
    this.restaurantName = null;
    this.startTime = null;
    this.maxSize = null;
    this.anonymous = false;
  }

  select(selected: CompleterItem) {
    this.selectedRestaurant = selected.originalObject;
  }

  private validateForm(createLunchDto: CreateLunchDto): boolean {
    if (createLunchDto.anonymous == null) {
      createLunchDto.anonymous = false;
    }
    if (createLunchDto.maxSize > 50) {
      this.displayAlert(AlertLevel.ERROR, "You surely cant have a place that takes so many people? oO", 3);
      return false;
    }

    if (createLunchDto.startTime < new Date().getMilliseconds()) {
      this.displayAlert(AlertLevel.ERROR, "Pretty sure time travel ain't invented yet! Your lunch cant be in the past", 3);
      return false;
    }

    return true;
  }
}
