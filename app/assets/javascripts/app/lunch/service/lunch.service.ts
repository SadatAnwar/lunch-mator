import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/Rx';
import {CreateLunchDto, LunchDto, LunchDetailDto} from '../dto/types';

@Injectable()
export class LunchService {
  lunchUrl: string;
  myLunchUrl: string;
  lunchDetailUrl: string;
  leaveLunchUrl: string;

  constructor(private http: Http) {
    this.lunchUrl = '/rest/lunch';
    this.leaveLunchUrl = '/rest/lunch/leave';
    this.myLunchUrl = '/rest/my-lunch';
    this.lunchDetailUrl = '/rest/lunch-detail/'
  }

  createLunch(lunch: CreateLunchDto): Observable<number> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.lunchUrl, lunch, {headers})
      .map((response: Response) => {
        return response.json();
      });
  }

  getLunchList(): Observable<LunchDto[]> {
    return this.http.get(this.lunchUrl)
      .map(response => response.json());
  }

  join(id: number): Observable<any> {
    return this.http.put(this.lunchUrl + "/" + id, "")
      .map(response => response.json());
  }

  leave(lunch: LunchDto): Observable<any> {
    return this.http.post(this.leaveLunchUrl, lunch);
  }

  getMyLunchList(): Observable<LunchDto[]> {
    return this.http.get(this.myLunchUrl).map(response => response.json());
  }

  getLunchDetails(lunchId: number): Observable<LunchDetailDto> {
    return this.http.get(this.lunchDetailUrl + lunchId)
      .map(response => response.json());
  }
}
