import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {RegistrationService} from "app/authentication/services/registration.service";
import {ErrorMapper} from "app/mappers/ErrorMapper";
import {AlertDisplay} from "../../../common/services/AlertDisplay";
import {AlertLevel} from "../../../common/types/Alert";
import {PasswordValidationService} from "../../services/passwordvalidation.service";

@Component({
  selector: 'registration',
  templateUrl: 'assets/javascripts/app/authentication/components/registration/registration.component.html'
})

export class RegistrationComponent extends AlertDisplay {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
  random = null;
  waiting: boolean = false;

  constructor(private registrationService: RegistrationService,
              private passwordValidationService: PasswordValidationService,
              private router: Router) {
    super();
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
          this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error).message)
        });
      this.waiting = true;
    }
    else {
      this.alertPasswordMismatch();
    }

  }

  private alertPasswordMismatch() {
    this.displayAlert(AlertLevel.ERROR, this.passwordValidationService.getError());
  }

  private redirectToWelcome() {
    this.router.navigateByUrl('welcome');
  }

  private refresh() {
    this.clearAlert()
  }

  private randomGenerator() {
    return Math.floor((Math.random() * 6) + 1);
  }
}
