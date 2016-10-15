import {Component} from "@angular/core";

@Component({
  selector: 'header',
  templateUrl: 'assets/javascripts/app/common/components/header/header.component.html'
})

export class HeaderComponent {
  login: boolean = false;

  loginSucces() {
    this.login = true;
  }

  logOut() {
    this.login = false;
  }
}
