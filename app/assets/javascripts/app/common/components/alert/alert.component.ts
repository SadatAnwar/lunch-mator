import {Component, Input} from '@angular/core';
import {Alert} from '../../types/Alert';

@Component({
  selector: 'alert-detail',
  templateUrl: 'assets/javascripts/app/common/components/alert/alert.component.html'
})

export class AlertComponent {
  @Input()
  alert: Alert;

  @Input()
  alertClass: string;
}


