import {Component} from '@angular/core';

@Component({
  selector: 'welcome',
  templateUrl: './welcome.component.html'
})
export class WelcomeComponent {
  login: boolean = true;

  constructor() {
    this.login = true;
  }
}
