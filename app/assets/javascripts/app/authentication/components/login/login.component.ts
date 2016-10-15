import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "app/authentication/services/authentication.service";
import {ErrorDetail} from "app/common/types/error";
import {ErrorMapper} from "app/mappers/ErrorMapper";
import {UserIdentity} from "../../types/user-identity";

@Component({
  selector: 'login',
  templateUrl: 'assets/javascripts/app/authentication/components/login/login.component.html'
})
export class LoginComponent {
  email: string;
  password: string;
  error: ErrorDetail;
  waiting: boolean = false;


  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  signIn() {
    if (! this.formValidation({email: this.email, password: this.password})) {
      this.error = new Error();
      this.error.message = "please enter a valid email";
      return;
    }
    this.authenticationService.signIn({email: this.email, password: this.password})
      .subscribe((response: any) => {
        this.waiting = false;
        this.redirectToWelcome();
      }, (error: any) => {
        this.waiting = false;
        this.error = ErrorMapper.map(error);
      });
    this.waiting = true;
  }

  private redirectToWelcome() {
    this.router.navigateByUrl('welcome');
  }

  formValidation(userIdentity: UserIdentity): boolean {
    if (userIdentity.email == null || userIdentity.email.length == 0){
      return false;
    }
    if (userIdentity.password == null || userIdentity.password.length == 0){
      return false;
    }
    return true;
  }
}
