import {Component, EventEmitter, Injectable, Input, OnInit, Output} from '@angular/core';
import {AlertService} from '../../services/alert.service';
import {CommentService} from '../../services/comment.service';
import {LunchDto} from '../../types';

@Component({
  selector: 'message-input',
  templateUrl: './comment-input.component.html',
  styleUrls: ['./comment-input.component.scss']

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

  constructor(private alertService: AlertService, private messageService: CommentService) {
  }

  public postMessage() {
    if (!this.messageText || this.messageText.length < 3) {
      this.alertService.error("Message cannot be so short");
      return;
    }
    this.messageService.postMessage(this.lunch.id, {text: this.messageText})
      .subscribe(() => {
        this.waiting = false;
        this.ngOnInit();
        this.newMessage.emit();
      }, ((err) => {
        this.waiting = false;
        this.alertService.error(err, true, 5000);
      }));
    this.waiting = true;
  }
}
