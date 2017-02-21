import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {ErrorDetailComponent} from 'app/common/components/error/error-detail.component';
import {AlertComponent} from './components/alert/alert.component';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {AboutComponent} from './components/about/about.component';
import {AppRoutingModule} from '../app.routing';

@NgModule({
  imports: [CommonModule, FormsModule, AppRoutingModule],
  declarations: [ErrorDetailComponent, AlertComponent, AboutComponent, WelcomeComponent],
  exports: [ErrorDetailComponent, AlertComponent, AboutComponent, WelcomeComponent]
})
export class CommonModules {
}
