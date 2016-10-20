import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "app/authentication/services/authentication.service";
import {ErrorMapper} from "app/mappers/ErrorMapper";
import {UserIdentity} from "../../types/user-identity";
import {ErrorDetail} from "../../../common/types/ErrorDetail";
import {AlertDisplay} from "../../../common/services/AlertDisplay";
import {AlertLevel} from "../../../common/types/Alert";

@Component({
  selector: 'login',
  templateUrl: 'assets/javascripts/app/authentication/components/login/login.component.html'
})
export class LoginComponent extends AlertDisplay {
  email: string;
  password: string;
  error: ErrorDetail;
  waiting: boolean = false;


  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
    super();
  }

  signIn() {
    if (!this.formValidation({email: this.email, password: this.password})) {
      this.displayAlert(AlertLevel.ERROR, "please enter a valid email");
      return;
    }
    this.authenticationService.signIn({email: this.email, password: this.password})
      .subscribe((response: any) => {
        this.waiting = false;
        this.redirectToWelcome();
      }, (error: any) => {
        this.waiting = false;
        this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error).message)
      });
    this.waiting = true;
  }

  private redirectToWelcome() {
    this.router.navigateByUrl('welcome');
  }

  formValidation(userIdentity: UserIdentity): boolean {
    if (userIdentity.email == null || userIdentity.email.length == 0) {
      return false;
    }
    if (userIdentity.password == null || userIdentity.password.length == 0) {
      return false;
    }
    return true;
  }
}
