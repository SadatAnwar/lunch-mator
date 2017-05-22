import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AlertService} from '../services/alert.service';
import {CommentService} from '../services/comment.service';
import {CommentInputComponent} from './input/comment-input.component';
import {CommentListComponent} from './list/comment-list.component';
import {CommentMessageComponent} from './message/comment-message.component';

@NgModule({
  imports: [CommonModule, FormsModule],
  declarations: [CommentListComponent, CommentMessageComponent, CommentInputComponent],
  providers: [AlertService, CommentService],
  exports: [CommentListComponent, CommentInputComponent]
})
export class CommentModule {
}
