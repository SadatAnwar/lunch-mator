import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {LoginComponent} from 'app/authentication/components/login/login.component';
import {CommonModules} from '../common/common.modules';
import {AppRoutingModule} from '../app.routing';

@NgModule({
  imports: [CommonModule, FormsModule, CommonModules, AppRoutingModule],
  declarations: [LoginComponent]
})
export class AuthenticationModule {
}
