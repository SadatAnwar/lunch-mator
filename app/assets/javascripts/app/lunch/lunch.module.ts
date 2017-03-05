import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LunchItemComponent} from 'app/lunch/components/item/lunch-item.component';
import {LunchListComponent} from 'app/lunch/components/list/lunch-list.component';
import {CommonModules} from '../common/common.modules';
import {Ng2CompleterModule} from 'ng2-completer';
import {AddLunchComponent} from './components/addlunch/add-lunch.component';
import {FormsModule} from '@angular/forms';
import {LunchService} from '../services/lunch.service';
import {MyLunchListComponent} from './components/mylunch/my-lunch-list.component';
import {LunchDetailComponent} from './components/detail/lunch-detail.component';
import {CalenderService} from '../services/calander.service';
import {SelectModule} from 'ng2-select';
import {UserLookupService} from '../services/user-lookup.service';
import {InvitationService} from '../services/invitation.service';
import {AppRoutingModule} from '../app.routing';
import {CommentModule} from '../comment/comment.module';

@NgModule({
  imports: [
    CommonModule,
    CommonModules,
    Ng2CompleterModule,
    SelectModule,
    FormsModule,
    AppRoutingModule,
    CommentModule
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
