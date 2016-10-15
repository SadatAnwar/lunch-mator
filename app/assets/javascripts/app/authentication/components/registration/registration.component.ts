import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {RegistrationService} from "app/authentication/services/registration.service";
import {PasswordValidationService} from "app/authentication/services/passwordvalidation.service";
import {Http} from "@angular/http";
import {ErrorDetail} from "app/common/types/error";
import {ErrorMapper} from "app/mappers/ErrorMapper";

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
  error: ErrorDetail = null;
  random = null;
  waiting: boolean = false;

  constructor(private registrationService: RegistrationService,
              private passwordValidationService: PasswordValidationService,
              private router: Router) {
  }

  ngAfterViewInit() {
    var number = this.randomGenerator();
    console.log("random number is " + number);
    if (number % 2 == 0) {
      this.random = 1;
    }
  }

  register() {
    this.passwordValidationService.validate(this.password, this.confirmPassword);
    if (this.passwordValidationService.isValid()) {
      var user = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password
      };

      this.registrationService.signUp(user)
        .subscribe((response: any) => {
          this.waiting = false;
          this.redirectToWelcome();
        }, (error: any) => {
          this.waiting = false;
          this.error = ErrorMapper.map(error);
        });
      this.waiting = true;
    }
    else {
      this.alertPasswordMismatch();
    }

  }

  private alertPasswordMismatch() {
    this.error = this.passwordValidationService.getError();
  }

  private redirectToWelcome() {
    this.router.navigateByUrl('welcome');
  }

  private refresh() {
    this.error = null;
  }

  private randomGenerator() {
    return Math.floor((Math.random() * 6) + 1);
  }
}
