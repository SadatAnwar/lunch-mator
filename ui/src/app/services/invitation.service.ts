import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {Observable} from 'rxjs';
import {InvitationDto} from '../types';

@Injectable()
export class InvitationService {
  private invitationUrl = "rest/hipchat-invite";

  constructor(private http: Http) {
  }

  public invite(invite: InvitationDto): Observable<any> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(`${this.invitationUrl}`, invite, headers).map(() => {
      return
    })
  }
}
