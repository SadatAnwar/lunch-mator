import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/Rx';
import {CreateLunchDto, LunchDto} from '../dto/types';

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

  createLunch(lunch: CreateLunchDto): Observable<any> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.lunchUrl, lunch, {headers}).map((response: Response) => {
      return response.json;
    }).catch((error: any) => {
      console.error(error);
      return error;
    });
  }

  getLunchList(): Observable<any> {
    return this.http.get(this.lunchUrl).map(response => response.json()).catch((error: any) => {
      return error;
    });
  }

  join(id: number): Observable<any> {
    return this.http.put(this.lunchUrl + "/" + id, "").map(response => {
      response.json();
    }).catch((error: any) => {
      console.error(error);
      return error;
    });
  }

  leave(lunch: LunchDto): Observable<any> {
    return this.http.post(this.leaveLunchUrl, lunch).map(response => {
      return response;
    }).catch((error: any) => {
      console.error(error);
      return error;
    });
  }

  getMyLunchList() {
    return this.http.get(this.myLunchUrl).map(response => response.json()).catch((error: any) => {
      console.error(error);
      return error;
    });
  }

  getLunchDetails(lunchId: number) {
    return this.http.get(this.lunchDetailUrl + lunchId).map(response => response.json()).catch((error: any) => {
      console.error(error);
      return error;
    });
  }
}
