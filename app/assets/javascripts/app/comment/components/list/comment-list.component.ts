import {Component, Injectable, Input, OnInit} from '@angular/core';
import {CommentService} from '../../../services/comment.service';
import {LunchDto, CommentDto} from '../../../dto/types';

@Component({
  selector: 'message-list',
  templateUrl: 'assets/javascripts/app/comment/components/list/comment-list.component.html'
})

@Injectable()
export class CommentListComponent implements OnInit {

  @Input()
  private lunch: LunchDto;

  private messages: CommentDto[];

  constructor(private messageService: CommentService) {
  }

  ngOnInit(): void {
    this.messageService.getMessagesForLunch(this.lunch.id)
      .subscribe((response: CommentDto[]) => {
        this.messages = response;
      });
  }

}
