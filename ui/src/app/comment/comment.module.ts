import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CommentListComponent} from './comment-list.component';
import {CommentService} from '../services/comment.service';
import {CommentMessageComponent} from './comment-message.component';
import {CommentInputComponent} from './comment-input.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  imports: [CommonModule, FormsModule],
  declarations: [CommentListComponent, CommentMessageComponent, CommentInputComponent],
  providers: [CommentService],
  exports: [CommentListComponent, CommentInputComponent]
})
export class CommentModule {
}
