import {Component, Input, trigger, state, animate, transition, style} from "@angular/core";
import {Alert} from "./types/Alert";

@Component({
  selector: 'alert-detail',
  templateUrl: 'alert.component.html',
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
  @Input()
  alert: Alert;
}


