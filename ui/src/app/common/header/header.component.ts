import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'header',
  templateUrl: './header.component.html'
})

export class HeaderComponent implements OnInit {
  login: boolean = true;
  public isCollapsed: boolean = true;

  ngOnInit(): void {
  }

  loginSucces() {
    this.login = true;
  }

  logOut() {
    this.login = false;
  }

  dropdownToggle() {

  }
}
