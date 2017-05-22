import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommentService} from '../services/comment.service';
import {CommentInputComponent} from './comment-input.component';
import {CommentListComponent} from './comment-list.component';
import {CommentMessageComponent} from './comment-message.component';

@NgModule({
  imports: [CommonModule, FormsModule],
  declarations: [CommentListComponent, CommentMessageComponent, CommentInputComponent],
  providers: [CommentService],
  exports: [CommentListComponent, CommentInputComponent]
})
export class CommentModule {
}
