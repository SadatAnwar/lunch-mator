import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticationGuard} from './authentication/guard/authentication.guard';
import {LoginComponent} from './authentication/login/login.component';
import {AboutComponent} from './common/about/about.component';
import {AddLunchComponent} from './lunch/add/add-lunch.component';
import {LunchDetailComponent} from './lunch/details/lunch-detail.component';
import {LunchListComponent} from './lunch/list/lunch-list.component';
import {MyLunchListComponent} from './lunch/my-lunch/my-lunch-list.component';
import {AddRestaurantComponent} from './restaurant/add/add-restaurant.component';
import {RestaurantListComponent} from './restaurant/list/restaurant-list.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 's/welcome',
    pathMatch: 'full'
  },
  {
    path: 'welcome',
    redirectTo: '/s/welcome',
    pathMatch: 'full'
  },
  {
    path: 'restaurant',
    redirectTo: 's/restaurant/list',
    pathMatch: 'full'
  },
  {
    path: 'lunch',
    redirectTo: 's/lunch/list',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'about',
    component: AboutComponent
  },
  {
    path: 's',
    canActivate: [AuthenticationGuard],
    children: [
      {
        path: 'welcome',
        component: LunchListComponent
      },
      {
        path: 'restaurant',
        children: [
          {
            path: 'list',
            component: RestaurantListComponent
          },
          {
            path: 'add',
            component: AddRestaurantComponent
          }
        ]
      },
      {
        path: 'lunch',
        children: [
          {
            path: 'list',
            component: LunchListComponent
          },
          {
            path: 'my',
            component: MyLunchListComponent
          },
          {
            path: 'add',
            component: AddLunchComponent
          },
          {
            path: ':id',
            component: LunchDetailComponent
          }
        ]
      }
    ]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
