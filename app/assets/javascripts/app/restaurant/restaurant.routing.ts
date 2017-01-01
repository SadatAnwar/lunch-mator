import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AddRestaurantComponent} from './components/addrestaurant/add-restaurant.component';
import {RestaurantListComponent} from './components/list/restaurant-list.component';

const restaurantRoutes: Routes = [
  {
    path: 'add-restaurant',
    component: AddRestaurantComponent
  },
  {
    path: 'restaurants',
    component: RestaurantListComponent
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(restaurantRoutes);
