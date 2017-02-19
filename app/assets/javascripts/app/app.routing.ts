import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LunchListComponent} from './lunch/components/list/lunch-list.component';
import {AboutComponent} from './common/components/about/about.component';
import {AddRestaurantComponent} from './restaurant/components/addrestaurant/add-restaurant.component';
import {RestaurantListComponent} from './restaurant/components/list/restaurant-list.component';
import {LunchDetailComponent} from './lunch/components/detail/lunch-detail.component';
import {AddLunchComponent} from './lunch/components/addlunch/add-lunch.component';
import {MyLunchListComponent} from './lunch/components/mylunch/my-lunch-list.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/welcome',
    pathMatch: 'full'
  },
  {
    path: 'welcome',
    component: LunchListComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: 'add-restaurant',
    component: AddRestaurantComponent
  },
  {
    path: 'restaurants',
    component: RestaurantListComponent
  },
  {
    path: 'lunch/list',
    component: LunchListComponent
  },
  {
    path: 'lunch/my-lunch',
    component: MyLunchListComponent
  },
  {
    path: 'add-lunch',
    component: AddLunchComponent
  },
  {
    path: 'lunch/:id',
    component: LunchDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
