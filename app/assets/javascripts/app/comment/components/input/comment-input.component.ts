import {Component, EventEmitter, Injectable, Input, OnInit, Output} from '@angular/core';
import {CommentService} from '../../../services/comment.service';
import {LunchDto} from '../../../dto/types';

@Component({
  selector: 'message-input',
  templateUrl: 'assets/javascripts/app/comment/components/input/comment-input.component.html'
})

@Injectable()
export class CommentInputComponent implements OnInit {
  @Input()
  private lunch: LunchDto;

  @Output()
  private newMessage = new EventEmitter();

  private messageText: string;

  ngOnInit(): void {
    this.messageText = "";
  }

  constructor(private messageService: CommentService) {
  }

  public postMessage() {
    this.messageService.postMessage(this.lunch.id, {text: this.messageText})
      .subscribe(() => {
        this.ngOnInit();
        this.newMessage.emit();
      })
    ;
  }
}
