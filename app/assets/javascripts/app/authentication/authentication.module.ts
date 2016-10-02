import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {routing} from 'app/authentication/authentication.routing';
import {LoginComponent} from 'app/authentication/components/login/login.component';
import {AuthenticationService} from 'app/authentication/services/authentication.service';

@NgModule({
  imports: [CommonModule, FormsModule, routing],
  declarations: [LoginComponent],
  providers: [AuthenticationService]
})
export class AuthenticationModule {
}
