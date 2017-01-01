import {Component, Input, EventEmitter, Output, OnInit} from '@angular/core';
import {RestaurantDto} from 'app/lunch/dto/types';
import {Router} from '@angular/router';

@Component({
  selector: 'restaurant-item',
  templateUrl: 'assets/javascripts/app/restaurant/components/item/restaurant-item.component.html'
})
export class RestaurantItemComponent implements OnInit {
  @Input()
  restaurant: RestaurantDto;
  hasMap = false;

  @Output()
  requestStart = new EventEmitter();

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    if (this.restaurant.website && this.restaurant.website.startsWith('http')) {
      this.hasMap = true;
    }
  }

  start(restaurant: RestaurantDto) {
    this.requestStart.emit(restaurant);
  }

  // details(restaurant: RestaurantDto) {
  //   this.router.navigate(['/restaurant', restaurant.id]);
  // }

}
