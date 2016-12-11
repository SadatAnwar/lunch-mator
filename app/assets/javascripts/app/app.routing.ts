import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LunchListComponent} from './lunch/components/list/lunch-list.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/lunch/list',
    pathMatch: 'full'
  },
  {
    path: 'lunch/list',
    component: LunchListComponent
  },
  {
    path: 'welcome',
    component: LunchListComponent
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
