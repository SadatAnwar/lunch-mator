import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {routing} from 'app/authentication/authentication.routing';
import {RegistrationComponent} from 'app/authentication/components/registration/registration.component';
import {RegistrationService} from 'app/authentication/services/registration.service';

@NgModule({
  imports: [CommonModule, FormsModule, routing],
  declarations: [RegistrationComponent],
  providers: [RegistrationService]
import {LoginComponent} from 'app/authentication/components/login/login.component';
import {AuthenticationService} from 'app/authentication/services/authentication.service';

@NgModule({
  imports: [CommonModule, FormsModule, routing],
  declarations: [LoginComponent],
  providers: [AuthenticationService]
})
export class AuthenticationModule {
}
