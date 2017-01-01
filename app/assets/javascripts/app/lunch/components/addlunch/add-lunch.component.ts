import {Component} from '@angular/core';
import {CompleterService, CompleterData, CompleterItem} from 'ng2-completer';
import {AlertDisplay} from '../../../common/services/AlertDisplay';
import {AlertLevel} from '../../../common/types/Alert';
import {RestaurantDto, CreateLunchDto} from '../../dto/types';
import {LunchService} from '../../service/lunch.service';
import {CalenderService} from '../../service/calander.service';
import {Router} from '@angular/router';

@Component({
  selector: 'add-lunch',
  templateUrl: 'assets/javascripts/app/lunch/components/addlunch/add-lunch.component.html'
})

export class AddLunchComponent extends AlertDisplay {
  waiting: boolean = false;
  lunchName: string = "";
  restaurantName: string = "";
  maxSize: number;
  dataService: CompleterData;
  selectedRestaurant: RestaurantDto;
  anonymous: boolean = false;

  //Time
  startYY: number;
  startDD: number;
  startMM: number;
  startHH: number;
  startMin: number;

  constructor(private completerService: CompleterService,
              private lunchService: LunchService,
              private calenderService: CalenderService,
              private router: Router) {
    super();

    this.dataService = completerService.remote("/rest/restaurants/search/", "name", 'name');
  }

  createTable() {
    if (!this.selectedRestaurant) {
      this.displayAlert(AlertLevel.ERROR, "Make sure you select a valid restaurant. You can add one by going to add restaurant. If this error persists, try reloading the page");
      return;
    }
    let createLunchDto = {
      restaurantId: this.selectedRestaurant.id,
      lunchName: this.lunchName,
      startTime: new Date(2000 + this.startYY, this.startMM - 1, this.startDD, this.startHH, this.startMin).getTime(),
      anonymous: this.anonymous,
      maxSize: this.maxSize
    };

    if (!this.validateForm(createLunchDto)) {
      return;
    }
    this.lunchService.createLunch(createLunchDto)
      .subscribe((lunchId: number) => {
        this.waiting = false;
        this.displayAlert(AlertLevel.SUCCESS, "New lunch started", 3);
        this.calenderService.createCalander(createLunchDto.lunchName, this.selectedRestaurant.name, this.selectedRestaurant.website, new Date(createLunchDto.startTime));
        this.router.navigateByUrl(`/lunch/${lunchId}`);
        console.info(`Lunch ID: ${lunchId}`);
      }, (error: any) => {
        this.waiting = false;
        this.displayAlert(AlertLevel.ERROR, `Error: [${error}]`)
      });
    this.waiting = true;
  }

  reset() {
    this.lunchName = null;
    this.restaurantName = null;
    this.maxSize = null;
    this.anonymous = false;
    this.startYY = null;
    this.startDD = null;
    this.startMM = null;
    this.startHH = null;
    this.startMin = null;
  }

  selectRestaurant(selected: CompleterItem) {
    this.selectedRestaurant = selected.originalObject;
  }

  private validateForm(createLunchDto: CreateLunchDto): boolean {
    if (createLunchDto.anonymous == null) {
      createLunchDto.anonymous = false;
    }
    if (!createLunchDto.maxSize) {
      createLunchDto.maxSize = 5;
    }
    if (createLunchDto.maxSize > 50 || createLunchDto.maxSize < 2) {
      this.displayAlert(AlertLevel.ERROR, "You surely cant have a place that takes so many people? oO", 5);
      return false;
    }

    if (createLunchDto.startTime < new Date().getMilliseconds()) {
      this.displayAlert(AlertLevel.ERROR, "Pretty sure time travel ain't invented yet! Your lunch cant be in the past", 5);
      return false;
    }

    if (this.startYY > this.yearYY(new Date()) + 1) {
      this.startYY = this.yearYY(new Date());
    }

    return true;
  }

  public randomRestaurant() {
    this.lunchService.getRandomRestaurant()
      .subscribe((restaurant: RestaurantDto) => {
        this.restaurantName = restaurant.name;
        this.selectedRestaurant = restaurant;
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, `Error occured: [${error}]`);
      });

  }

  public tomorrow() {
    this.today();
    this.startDD = this.startDD + 1;
  }

  public today() {
    let date = new Date();
    this.startYY = this.yearYY(date);
    this.startDD = date.getDate();
    this.startMM = date.getMonth() + 1;
    this.startHH = 12;
    this.startMin = 0;
  }

  private yearYY(date: Date) {
    return date.getFullYear() - 2000;
  }
}
