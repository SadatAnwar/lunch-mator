import {ModuleWithProviders} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {AddRestaurantComponent} from "./components/addrestaurant/addrestaurant.component";


const restaurantRoutes: Routes = [
  {
    path: 'add-restaurant',
    component: AddRestaurantComponent
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(restaurantRoutes);
