import {Component, EventEmitter, Injectable, Input, OnInit, Output} from '@angular/core';
import {CommentService} from '../services/comment.service';
import {LunchDto} from '../types';

@Component({
  selector: 'message-input',
  templateUrl: 'comment-input.component.html'
})

@Injectable()
export class CommentInputComponent implements OnInit {
  @Input()
  lunch: LunchDto;

  @Output()
  newMessage = new EventEmitter();

  messageText: string;
  waiting: boolean = false;

  ngOnInit(): void {
    this.messageText = "";
  }

  constructor(private messageService: CommentService) {
  }

  public postMessage() {
    this.messageService.postMessage(this.lunch.id, {text: this.messageText})
      .subscribe(() => {
        this.waiting = false;
        this.ngOnInit();
        this.newMessage.emit();
      });
    this.waiting = true;
  }
}
