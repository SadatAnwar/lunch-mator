import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {routing} from "app/authentication/authentication.routing";
import {RegistrationComponent} from "app/authentication/components/registration/registration.component";
import {RegistrationService} from "app/authentication/services/registration.service";
import {PasswordValidationService} from "app/authentication/services/passwordvalidation.service";
import {LoginComponent} from "app/authentication/components/login/login.component";
import {AuthenticationService} from "app/authentication/services/authentication.service";
import {CommonModules} from "../common/common.modules";

@NgModule({
  imports: [CommonModule, FormsModule, CommonModules, routing],
  declarations: [RegistrationComponent, LoginComponent],
  providers: [RegistrationService, PasswordValidationService, AuthenticationService]
})
export class AuthenticationModule {
}
