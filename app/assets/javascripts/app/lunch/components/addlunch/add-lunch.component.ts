import {Component, OnInit, EventEmitter} from '@angular/core';
import {CompleterService, CompleterData, CompleterItem} from 'ng2-completer';
import {AlertDisplay} from '../../../common/services/AlertDisplay';
import {AlertLevel} from '../../../common/types/Alert';
import {RestaurantDto, CreateLunchDto, HipChatUser, HipChatPing, InvitationDto} from '../../../dto/types';
import {LunchService} from '../../service/lunch.service';
import {CalenderService} from '../../service/calander.service';
import {Router, Params, ActivatedRoute} from '@angular/router';
import {RestaurantService} from '../../../restaurant/services/restaurant.services';
import {Response} from '@angular/http';
import {UserLookupService} from '../../service/user-lookup.service';
import {InvitationService} from '../../service/invitation.service';

@Component({
  selector: 'add-lunch',
  templateUrl: 'assets/javascripts/app/lunch/components/addlunch/add-lunch.component.html'
})

export class AddLunchComponent extends AlertDisplay implements OnInit {
  private waiting: boolean = false;
  private lunchName: string = "";
  private restaurantName: string = "";
  private maxSize: number;
  private dataService: CompleterData;
  private selectedRestaurant: RestaurantDto;
  private anonymous: boolean = false;
  private inviteUsers: HipChatPing[] = [];

  //Time
  startYY: number;
  startDD: number;
  startMM: number;
  startHH: number;
  startMin: number;

  constructor(private completerService: CompleterService,
              private lunchService: LunchService,
              private restaurantService: RestaurantService,
              private calenderService: CalenderService,
              private userLookupService: UserLookupService,
              private activatedRoute: ActivatedRoute,
              private invitationService: InvitationService,
              private router: Router) {
    super();

    this.dataService = completerService.remote("/rest/restaurants/", "name", 'name');
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
              this.displayAlert(AlertLevel.ERROR, `Error:  ${error.text()}`, 5);
            });
      });
  }

  createTable() {
    if (!this.selectedRestaurant) {
      this.displayAlert(AlertLevel.ERROR, "Make sure you select a valid restaurant. You can add one by going to add restaurant. If this error persists, try reloading the page", 5);

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

        if (this.inviteUsers.length > 0) {
          let invitation: InvitationDto = {users: this.inviteUsers, lunchId: lunchId};
          this.invitationService.invite(invitation).subscribe(() => console.log("invitation success for ", invitation));
        }

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
    if (createLunchDto.lunchName.length > 40) {
      this.displayAlert(AlertLevel.ERROR, "Lunch name cannot be more than 20 characters", 5);
      return false;
    }

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
    this.restaurantService.getRandomRestaurant()
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

  public items$: EventEmitter<any> = new EventEmitter<any>();

  private value: HipChatUser[];

  public selected(item: any): void {
    this.items$.next([]);
  }

  public removeUser(item: any): void {
    this.items$.next([]);
  }

  public searchUsers(value: any): void {
    if (value.trim().length > 0) {
      this.userLookupService.search(value.trim()).subscribe((users: HipChatUser[]) => {
        const items = [];
        users.forEach((user: HipChatUser) => {
          let entry: any = {};
          entry.text = user.name;
          entry.id = user.mention_name;

          items.push(entry);
        });
        this.items$.emit(items);
      })
    }
  }

  public refreshValue(value: any[]): void {
    this.inviteUsers = [];
    value.forEach(item => {
      let hipchatPing: HipChatPing = {mention_name: item.id};
      this.inviteUsers.push(hipchatPing);
    });

  }
}

