import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, Router} from '@angular/router';
import {AlertService} from '../../services/alert.service';
import {AuthenticationService} from '../../services/authentication.service';

@Injectable()
export class AuthenticationGuard implements CanActivate {
  constructor(private router: Router, private authenticationService: AuthenticationService, private alertService: AlertService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    let authenticated = this.authenticationService.authenticate();
    if (authenticated) {
      return true;
    }
    // Explicit navigation to any URL while not being authenticated
    this.alertService.error("Please log-in first", true);
    this.router.navigate(['/login']);
    return false;
  }
}
