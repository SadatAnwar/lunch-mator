import {Component, Injectable, Input, OnInit} from '@angular/core';
import {CommentService} from '../services/comment.service';
import {CommentDto, LunchDto} from '../types';

@Component({
  selector: 'message-list',
  templateUrl: 'comment-list.component.html'
})

@Injectable()
export class CommentListComponent implements OnInit {

  @Input()
  lunch: LunchDto;

  messages: CommentDto[];

  constructor(private messageService: CommentService) {
  }

  ngOnInit(): void {
    this.messageService.getMessagesForLunch(this.lunch.id)
      .subscribe((response: CommentDto[]) => {
        this.messages = response;
      });
  }

}
