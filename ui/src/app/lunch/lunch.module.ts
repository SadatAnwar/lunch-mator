import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LunchItemComponent} from './lunch-item.component';
import {LunchListComponent} from './lunch-list.component';
import {CommonModules} from '../common/common.modules';
import {AddLunchComponent} from './add-lunch.component';
import {FormsModule} from '@angular/forms';
import {LunchService} from '../services/lunch.service';
import {MyLunchListComponent} from './my-lunch-list.component';
import {LunchDetailComponent} from './lunch-detail.component';
import {CalenderService} from '../services/calander.service';
import {SelectModule} from 'ng2-select';
import {UserLookupService} from '../services/user-lookup.service';
import {InvitationService} from '../services/invitation.service';
import {AppRoutingModule} from '../app.routing';
import {CommentModule} from '../comment/comment.module';
import { TypeaheadModule } from 'ngx-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    CommonModules,
    SelectModule,
    FormsModule,
    AppRoutingModule,
    CommentModule,
    TypeaheadModule
  ],
  declarations: [
    LunchItemComponent,
    LunchListComponent,
    LunchDetailComponent,
    MyLunchListComponent,
    AddLunchComponent
  ],
  providers: [LunchService, CalenderService, UserLookupService, InvitationService]
})
export class LunchModule {
}
