import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {ErrorDetailComponent} from './error-detail.component';
import {AlertComponent} from './alert.component';
import {WelcomeComponent} from './welcome.component';
import {AboutComponent} from './about.component';
import {AppRoutingModule} from '../app.routing';

@NgModule({
  imports: [CommonModule, FormsModule, AppRoutingModule],
  declarations: [ErrorDetailComponent, AlertComponent, AboutComponent, WelcomeComponent],
  exports: [ErrorDetailComponent, AlertComponent, AboutComponent, WelcomeComponent]
})
export class CommonModules {
}
