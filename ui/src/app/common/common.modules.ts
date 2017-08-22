import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {SelectModule} from 'ng2-select';
import {AppRoutingModule} from '../app.routing';
import {AlertService} from '../services/alert.service';
import {InvitationService} from '../services/invitation.service';
import {UserLookupService} from '../services/user-lookup.service';
import {AboutComponent} from './about/about.component';
import {AlertComponent} from './alert/alert.component';
import {ErrorDetailComponent} from './error-details/error-detail.component';
import {UserSelectComponent} from './user-select/user-select.component';
import {WelcomeComponent} from './welcome/welcome.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AppRoutingModule,
    SelectModule,
  ],
  declarations: [
    AboutComponent,
    AlertComponent,
    ErrorDetailComponent,
    UserSelectComponent,
    WelcomeComponent
  ],
  exports: [
    AboutComponent,
    AlertComponent,
    ErrorDetailComponent,
    UserSelectComponent,
    WelcomeComponent
  ],
  providers: [UserLookupService, AlertService, InvitationService]
})
export class CommonModules {
}
