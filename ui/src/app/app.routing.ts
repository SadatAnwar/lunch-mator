import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LunchListComponent} from './lunch/lunch-list.component';
import {AboutComponent} from './common/about.component';
import {AddRestaurantComponent} from './restaurant/add-restaurant.component';
import {RestaurantListComponent} from './restaurant/restaurant-list.component';
import {LunchDetailComponent} from './lunch/lunch-detail.component';
import {AddLunchComponent} from './lunch/add-lunch.component';
import {MyLunchListComponent} from './lunch/my-lunch-list.component';
import {LoginComponent} from './authentication/login.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/welcome',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
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
