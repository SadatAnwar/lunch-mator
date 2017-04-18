import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {CreateRestaurantDto, RestaurantDto} from '../types';
import {Observable} from 'rxjs';
import 'rxjs/Rx';

@Injectable()
export class RestaurantService {
  private restaurants = '/rest/restaurants';
  private restaurant = '/rest/restaurant';

  constructor(private http: Http) {
  }

  public getAllRestaurants(): Observable<RestaurantDto[]> {
    return this.http.get(this.restaurants).map((response: Response) => {
      return response.json();
    });
  }

  public getRestaurant(restaurantId: number): Observable<RestaurantDto> {
    return this.http.get(`${this.restaurant}/${restaurantId}`).map((response: Response) => {
      return response.json();
    });
  }

  public getRandomRestaurant(): Observable<RestaurantDto> {
    return this.http.get(`${this.restaurants}/random`)
      .map(response => response.json());
  }

  public add(restaurantDto: CreateRestaurantDto): Observable<void> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.restaurants, restaurantDto, {headers}).map(response => response.json());
  }

  public
  getSerachUrl(): string {
    return `${this.restaurants}/search/`
  }
}
