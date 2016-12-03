import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {routing} from 'app/authentication/authentication.routing';
import {LoginComponent} from 'app/authentication/components/login/login.component';
import {CommonModules} from '../common/common.modules';

@NgModule({
  imports: [CommonModule, FormsModule, CommonModules, routing],
  declarations: [LoginComponent]
})
export class AuthenticationModule {
}
