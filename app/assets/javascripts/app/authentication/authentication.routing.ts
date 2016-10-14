import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from 'app/authentication/components/login/login.component';
import {RegistrationComponent} from 'app/authentication/components/registration/registration.component';


const loginRoutes: Routes = [
    {
        path: 'login',
        component: LoginComponent
    },
  {
    path: 'registration',
    component: RegistrationComponent
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(loginRoutes);
