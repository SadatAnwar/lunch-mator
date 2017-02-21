import {Http, Response} from '@angular/http';
import {HipChatUser} from '../../dto/types';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class UserLookupService {
  private lookupUrl = "rest/hipchat-users";

  constructor(private http: Http) {
  }

  public search(name: string): Observable<HipChatUser[]> {

    return this.http.get(`${this.lookupUrl}/${name}`).map((response: Response) => {
      return response.json()
    })
  }
}
