import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Ng2CompleterModule} from 'ng2-completer';
import {TypeaheadModule} from 'ngx-bootstrap';
import {AppRoutingModule} from '../app.routing';
import {CommentModule} from '../comment/comment.module';
import {CommonModules} from '../common/common.modules';
import {AlertService} from '../services/alert.service';
import {CalenderService} from '../services/calander.service';
import {InvitationService} from '../services/invitation.service';
import {LunchService} from '../services/lunch.service';
import {AddLunchComponent} from './add/add-lunch.component';
import {LunchDetailComponent} from './details/lunch-detail.component';
import {LunchItemComponent} from './item/lunch-item.component';
import {LunchListComponent} from './list/lunch-list.component';
import {MyLunchListComponent} from './my-lunch/my-lunch-list.component';

@NgModule({
  imports: [
    CommonModule,
    CommonModules,
    FormsModule,
    AppRoutingModule,
    CommentModule,
    Ng2CompleterModule,
    TypeaheadModule
  ],
  declarations: [
    LunchItemComponent,
    LunchListComponent,
    LunchDetailComponent,
    MyLunchListComponent,
    AddLunchComponent
  ],
  providers: [LunchService, CalenderService, InvitationService, AlertService]
})
export class LunchModule {
}
