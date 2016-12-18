import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/Rx';
import {CreateLunchDto, LunchDto, LunchDetailDto} from '../dto/types';

@Injectable()
export class LunchService {
  private lunchUrl: string;
  private myLunchUrl: string;
  private leaveLunchUrl: string;
  private participantUrl: string;
  private randomRestaurantUrl: string;

  constructor(private http: Http) {
    this.lunchUrl = '/rest/lunch';
    this.leaveLunchUrl = '/rest/lunch/leave';
    this.myLunchUrl = '/rest/my-lunch';
    this.participantUrl = '/rest/participants';
    this.randomRestaurantUrl = '/rest/restaurants/random';

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

  public requestJoin(lunch: LunchDto, onSuccess: (response: any) => void, onFail: (error: any) => void) {
    this.join(lunch.id).subscribe(onSuccess, onFail);
  }

  private join(id: number): Observable<any> {
    return this.http.put(this.lunchUrl + "/" + id, "")
      .map(response => response.json());
  }

  public requestLeave(lunchDto: LunchDto, onSuccess: (response: any) => void, onFail: (error: any) => void) {
    if (confirm("are you sure you want to leave?")) {
      this.leave(lunchDto).subscribe(onSuccess, onFail);
    }
  }

  private leave(lunch: LunchDto): Observable<any> {
    return this.http.post(this.leaveLunchUrl, lunch);
  }

  getMyLunchList(): Observable<LunchDto[]> {
    return this.http.get(this.myLunchUrl).map(response => response.json());
  }

  getLunchDetails(lunchId: number): Observable<LunchDetailDto> {
    return this.http.get(`${this.lunchUrl}/${lunchId}`)
      .map(response => response.json());
  }

  getParticipants(lunchId: number) {
    return this.http.get(`${this.participantUrl}/${lunchId}`)
      .map(response => response.json());
  }

  getRandomRestaurant() {
    return this.http.get(`${this.randomRestaurantUrl}`)
      .map(response => response.json());
  }
}
