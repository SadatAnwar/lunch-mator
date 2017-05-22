import {Component, trigger, state, animate, transition, style} from '@angular/core';
import {AlertService} from '../../services/alert.service';
import {Alert} from '../types/Alert';

@Component({
  selector: 'alert-detail',
  templateUrl: './alert.component.html',
  animations: [
    trigger('state', [
      state('true', style({
        opacity: 0
      })),
      state('false', style({
        opacity: 1
      })),
      transition('false => true', animate(500)),
      transition('true => false', animate(500))
    ])
  ]
})

export class AlertComponent {
  alert: Alert;

  constructor(private alertService: AlertService) {
  }

  ngOnInit() {
    this.alertService.getMessage().subscribe(message => {
      if (message) {
        this.alert = message;
      }
    });
  }
}
