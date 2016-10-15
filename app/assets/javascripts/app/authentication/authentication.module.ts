import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {routing} from 'app/authentication/authentication.routing';
import {RegistrationComponent} from 'app/authentication/components/registration/registration.component';
import {RegistrationService} from 'app/authentication/services/registration.service';
import {PasswordValidationService} from 'app/authentication/services/passwordvalidation.service';
import {LoginComponent} from 'app/authentication/components/login/login.component';
import {AuthenticationService} from 'app/authentication/services/authentication.service';
import {ErrorDetailComponent} from "../common/components/error/error-detail.component";

@NgModule({
  imports: [CommonModule, FormsModule, routing],
  declarations: [RegistrationComponent, ErrorDetailComponent, LoginComponent],
  providers: [RegistrationService, PasswordValidationService, AuthenticationService]
})
export class AuthenticationModule {
}
