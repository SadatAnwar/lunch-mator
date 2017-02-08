import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/Rx';
import {CreateLunchDto, LunchDto, LunchDetailDto} from '../../dto/types';

@Injectable()
export class LunchService {
  private lunchUrl = '/rest/lunch';
  private myLunchUrl = '/rest/my-lunch';
  private leaveLunchUrl = '/rest/lunch/leave';
  private participantUrl = '/rest/participants';

  constructor(private http: Http) {
  }

  public createLunch(lunch: CreateLunchDto): Observable<number> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.lunchUrl, lunch, {headers})
      .map((response: Response) => {
        return response.json();
      });
  }

  public getLunchList(): Observable<LunchDto[]> {
    return this.http.get(this.lunchUrl)
      .map(response => response.json());
  }

  public requestJoin(lunch: LunchDto, onSuccess: (response: any) => void, onFail: (error: any) => void) {
    this.join(lunch.id).subscribe(onSuccess, onFail);
  }

  public requestLeave(lunchDto: LunchDto, onSuccess: (response: any) => void, onFail: (error: any) => void) {
    this.leave(lunchDto).subscribe(onSuccess, onFail);
  }

  public getMyLunchList(): Observable<LunchDto[]> {
    return this.http.get(this.myLunchUrl).map(response => response.json());
  }

  public getLunchDetails(lunchId: number): Observable<LunchDetailDto> {
    return this.http.get(`${this.lunchUrl}/${lunchId}`)
      .map(response => response.json());
  }

  public getParticipants(lunchId: number) {
    return this.http.get(`${this.participantUrl}/${lunchId}`)
      .map(response => response.json());
  }

  private join(id: number): Observable<any> {
    return this.http.put(this.lunchUrl + "/" + id, "")
      .map(response => response.json());
  }

  private leave(lunch: LunchDto): Observable<any> {
    return this.http.post(this.leaveLunchUrl, lunch);
  }
}
