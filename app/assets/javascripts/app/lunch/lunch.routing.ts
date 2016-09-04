import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LunchListComponent} from 'app/lunch/components/list/lunch-list.component';

const lunchRoutes: Routes = [
    {
        path: 'lunch/list',
        component: LunchListComponent
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(lunchRoutes);
