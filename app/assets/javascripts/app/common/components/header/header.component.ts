import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'header',
  templateUrl: 'assets/javascripts/app/common/components/header/header.component.html'
})

export class HeaderComponent implements OnInit {
  private login: boolean = true;

  ngOnInit(): void {
    if ('/login' == window.location.pathname) {
      this.login = false;
    }
  }

  loginSucces() {
    this.login = true;
  }

  logOut() {
    this.login = false;
  }
}
