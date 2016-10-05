import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {RegistrationService} from 'app/authentication/services/registration.service';
import {Http} from '@angular/http';

@Component({
  selector: 'registration',
  templateUrl: 'assets/javascripts/app/authentication/components/registration/registration.component.html',
  providers: [Http]
})
export class RegistrationComponent {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;

  constructor(private registrationService: RegistrationService, private router: Router) {
  }

  ngAfterViewInit() {
    $('#passwordMismatch').hide();
  }

  register() {
    if (this.validatePassword()) {
      var user = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password
      };

      this.registrationService.register(user)
        .subscribe(() => {
          this.redirectToWelcome();
        });
    }
    else {
      RegistrationComponent.alertPasswordMismatch();
    }

  }

  private static alertPasswordMismatch() {
    console.log("password mismatch");
    $('#passwordMismatch').show(300);
    $('#passwordMismatch').hide(300);
  }

  private validatePassword() {
    console.log("password = " + this.password);
    console.log("confirmPassword = " + this.confirmPassword);
    return this.password == this.confirmPassword
  }

  private redirectToWelcome() {
    this.router.navigateByUrl('welcome');
  }
}
