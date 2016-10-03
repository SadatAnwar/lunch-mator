import {Injectable} from '@angular/core';
import {UserIdentity} from 'app/authentication/types/user-identity';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/Rx';

@Injectable()
export class AuthenticationService {
  url: string;
  constructor(private http: Http) {
    this.url = '/rest/login'
  }

  signIn(userIdentity: UserIdentity): Observable<any> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url, userIdentity, {headers}).map((response: Response) => {
      return response.json;
    });
  }
}
