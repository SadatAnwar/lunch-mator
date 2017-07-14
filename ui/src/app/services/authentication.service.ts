import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie';

@Injectable()
export class AuthenticationService {

  constructor(private _cookieService: CookieService) {
  }

  public authenticate(): boolean {
    let cookie = this.getCookie('PLAY_SESSION');

    if (!cookie) {
      return false;
    }
    
    let email = cookie.split('-')
      .filter((part: string) => part.indexOf('email') > -1)
      .map((email: string) => email.split('=')[1]);

    return (email && email.length == 1);

  }

  private getCookie(key: string) {
    return this._cookieService.get(key);
  }

}
