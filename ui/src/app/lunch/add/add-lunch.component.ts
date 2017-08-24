import {Component, OnInit, ViewChild} from '@angular/core';
import {Response} from '@angular/http';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {CompleterData, CompleterItem, CompleterService} from 'ng2-completer';
import 'rxjs/add/observable/of';
import {UserSelectComponent} from '../../common/user-select/user-select.component';
import {AlertService} from '../../services/alert.service';
import {CalenderService} from '../../services/calander.service';
import {LunchService} from '../../services/lunch.service';
import {RestaurantService} from '../../services/restaurant.services';
import {CreateLunchDto, RestaurantDto} from '../../types';
import {PlatformIdentificationService} from '../../services/platform-identification.service';

@Component({
  selector: 'add-lunch',
  templateUrl: './add-lunch.component.html',
  styleUrls: ["./add-lunch.component.scss"]
})

export class AddLunchComponent implements OnInit {
  waiting: boolean = false;
  lunchName: string = "";
  maxSize: number;
  restaurantName: string;
  selectedRestaurant: RestaurantDto;
  anonymous: boolean = false;

  //Time
  startYY: number;
  startDD: number;
  startMM: number;
  startHH: number;
  startMin: number;
  dataService: CompleterData;
  @ViewChild(UserSelectComponent)
  private userSelectComponent: UserSelectComponent;

  constructor(private alertService: AlertService,
              private lunchService: LunchService,
              private platformIdentificationService: PlatformIdentificationService,
              private completerService: CompleterService,
              private restaurantService: RestaurantService,
              private calenderService: CalenderService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
    this.dataService = completerService.remote("/rest/restaurants/search/", "name", 'name');

  }

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .map((params: Params) => {

        return +params['restaurantId'];
      })
      .subscribe((restaurantId: number) => {
        if (!restaurantId) {

          return;
        }
        this.restaurantService.getRestaurant(restaurantId)
          .subscribe((restaurant: RestaurantDto) => {
              this.restaurantName = restaurant.name;
              this.selectedRestaurant = restaurant;
            },
            (error: Response) => {
              this.alertService.error(`Error:  ${error.text()}`);
            });
      });
  }

  createTable() {
    if (!this.selectedRestaurant) {
      this.alertService.error("Make sure you select a valid restaurant. You can add one by going to add restaurant. If this error persists, try reloading the page");

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
        this.alertService.success("New lunch started", true);
        this.userSelectComponent.sendInvitation(lunchId);

        this.calenderService.createCalander(createLunchDto.lunchName, this.selectedRestaurant.name, this.selectedRestaurant.website, new Date(createLunchDto.startTime));
        this.router.navigateByUrl(`s/lunch/${lunchId}`);

      }, (error: any) => {
        this.waiting = false;
        this.alertService.error(`Error: [${error}]`)
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
    if (selected) {
      this.selectedRestaurant = selected.originalObject;
    }
  }

  private validateForm(createLunchDto: CreateLunchDto): boolean {
    if (createLunchDto.lunchName.length > 40) {
      this.alertService.error("Lunch name cannot be more than 40 characters");
      return false;
    }

    if (createLunchDto.anonymous == null) {
      createLunchDto.anonymous = false;
    }
    if (!createLunchDto.maxSize) {
      createLunchDto.maxSize = 5;
    }
    if (createLunchDto.maxSize > 50 || createLunchDto.maxSize < 2) {
      this.alertService.error("You surely cant have a place that takes so many people? oO");
      return false;
    }

    if (createLunchDto.startTime < new Date().getTime()) {
      this.alertService.error("Pretty sure time travel ain't invented yet! Your lunch cant be in the past");
      return false;
    }
    let twoMonths = new Date();
    twoMonths.setDate(twoMonths.getDate() + 60);
    if (createLunchDto.startTime > twoMonths.getTime()) {
      this.alertService.error("You can't surely plan that far ahead? your lunch is too far in the future");
      return false;
    }

    if (this.startYY > this.yearYY(new Date()) + 1) {
      this.startYY = this.yearYY(new Date());
    }

    return true;
  }

  public randomRestaurant() {
    this.restaurantService.getRandomRestaurant()
      .subscribe((restaurant: RestaurantDto) => {
        this.restaurantName = restaurant.name;
        this.selectedRestaurant = restaurant;
      }, (error: any) => {
        this.alertService.error(`Error occured: [${error}]`);
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

  public isWindows() {
    return this.platformIdentificationService.isWindows();
  }

  private yearYY(date: Date) {
    return date.getFullYear() - 2000;
  }
}

