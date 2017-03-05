import {Http, Response} from '@angular/http';
import {Injectable} from '@angular/core';
import {HipChatUser} from '../dto/types';
import {Observable} from 'rxjs';

@Injectable()
export class UserLookupService {
  private lookupUrl = 'rest/hipchat-users';
  private usersUrl = '/rest/users';

  constructor(private http: Http) {
  }

  public search(name: string): Observable<HipChatUser[]> {
    return this.http.get(`${this.lookupUrl}/${name}`).map((response: Response) => {
      return response.json()
    })
  }

  public getUser(id: number): Observable<any> {
    return this.http.get(`${this.usersUrl}/${id}`).map((response: Response) => {
      return response.json()
    })
  }
}
