import {Injectable} from "@angular/core";
import {Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs";
import "rxjs/Rx";
import {RestaurantDto} from "../../lunch/dto/types";

@Injectable()
export class RestaurantService {
  baseUrl: string;

  constructor(private http: Http) {
    this.baseUrl = '/rest/restaurants'
  }

  add(restaurantDto: RestaurantDto): Observable<any> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    console.log(restaurantDto);
    return this.http.post(this.baseUrl, restaurantDto, {headers}).map((response: Response) => {
      return response.json;
    });
  }
}
