import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'header',
  templateUrl: 'header.component.html'
})

export class HeaderComponent implements OnInit {
  login: boolean = true;

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
