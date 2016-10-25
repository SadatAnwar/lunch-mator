import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs";
import "rxjs/Rx";
import {CreateLunchDto} from "../dto/types";

@Injectable()
export class LunchService {
  url: string;
  myLunch: string;

  constructor(private http: Http) {
    this.url = '/rest/lunch';
    this.myLunch = '/rest/my-lunch';
  }

  createLunch(lunch: CreateLunchDto): Observable<any> {
    console.log(lunch);
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url, lunch, {headers}).map((response: Response) => {
      return response.json;
    }).catch((error: any) => {
      console.log(error);
      return error;
    });
  }

  getLunchList(): Observable<any> {
    return this.http.get(this.url).map(response => response.json()).catch((error: any) => {
      console.log(error);
      return error;
    });
  }

  join(id: number): Observable<any> {
    return this.http.put(this.url + "/" + id, "").map(response => {
      console.log(response);
      response.json();
    }).catch((error: any) => {
      console.log(error);
      return error;
    });
  }

  getMyLunchList() {
    return this.http.get(this.myLunch).map(response => response.json()).catch((error: any) => {
      console.log(error);
      return error;
    });
  }
}
