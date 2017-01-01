import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/Rx';
import {CreateRestaurantDto, RestaurantDto} from '../../lunch/dto/types';

@Injectable()
export class RestaurantService {
  restaurants: string;
  restaurant: string;

  constructor(private http: Http) {
    this.restaurants = '/rest/restaurants';
    this.restaurant = '/rest/restaurant';
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

  public add(restaurantDto: CreateRestaurantDto): Observable<any> {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.restaurants, restaurantDto, {headers}).map((response: Response) => {
      return response.json();
    });
  }

  public getSerachUrl(): string {
    return `${this.restaurants}/search/`
  }
}
