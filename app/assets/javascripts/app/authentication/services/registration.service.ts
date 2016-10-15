import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import {NewUser} from 'app/authentication/types/user';
import 'rxjs/Rx';

@Injectable()
export class RegistrationService {
  url: string;

  constructor(private http: Http) {
    this.url = '/rest/signUp'
  }

  signUp(user: NewUser): Observable<any> {
    console.log(user);
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url, user, {headers}).map((response: Response) => {
      return response.json;
    }).catch((error: any) => {
      console.log(error);
      return error;
    });
  }
}
