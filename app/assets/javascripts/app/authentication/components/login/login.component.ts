import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {AuthenticationService} from 'app/authentication/services/authentication.service';

@Component({
  selector: 'login',
  templateUrl: 'assets/javascripts/app/authentication/components/login/login.component.html'
})
export class LoginComponent {
  email: string;
  password: string;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  signIn() {
    this.authenticationService.signIn({email: this.email, password: this.password})
      .subscribe(() => {
        this.redirectToWelcome();
      });
  }

  private redirectToWelcome() {
    this.router.navigateByUrl('welcome');
  }
}
