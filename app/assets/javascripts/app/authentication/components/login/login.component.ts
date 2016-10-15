import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {AuthenticationService} from "app/authentication/services/authentication.service";
import {ErrorDetail} from "app/common/types/error";
import {ErrorMapper} from "app/mappers/ErrorMapper";

@Component({
  selector: 'login',
  templateUrl: 'assets/javascripts/app/authentication/components/login/login.component.html'
})
export class LoginComponent {
  email: string;
  password: string;
  error: ErrorDetail;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  signIn() {
    this.authenticationService.signIn({email: this.email, password: this.password})
      .subscribe((response: any) => {
        this.redirectToWelcome();
      }, (error: any) => {
        this.error = ErrorMapper.map(error);
      });
  }

  private redirectToWelcome() {
    this.router.navigateByUrl('welcome');
  }
}
