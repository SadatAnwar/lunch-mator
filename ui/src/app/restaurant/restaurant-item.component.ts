import {Component, Input, EventEmitter, Output, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {RestaurantDto} from './types';

@Component({
  selector: 'restaurant-item',
  templateUrl: 'restaurant-item.component.html'
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

}
