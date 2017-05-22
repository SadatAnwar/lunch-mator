import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CookieModule} from 'ngx-cookie';
import {AppRoutingModule} from '../app.routing';
import {CommonModules} from '../common/common.modules';
import {AuthenticationService} from '../services/authentication.service';
import {AuthenticationGuard} from './guard/authentication.guard';
import {LoginComponent} from './login/login.component';
import {AlertService} from '../services/alert.service';

@NgModule({
  imports: [AppRoutingModule, CommonModule, FormsModule, CommonModules, CookieModule.forChild()],
  declarations: [LoginComponent],
  providers: [AuthenticationService, AuthenticationGuard, AlertService]
})
export class AuthenticationModule {
}
