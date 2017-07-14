import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/Rx';
import {CreateRestaurantDto, RestaurantDto} from '../types';

@Injectable()
export class RestaurantService {
  private restaurants = '/rest/restaurants';
  private restaurant = '/rest/restaurant';
  restaurantSearch = `${this.restaurants}/search`;

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

  public searchRestaurant(name: string): Observable<RestaurantDto[]> {
    console.log("Searching for ", name);
    return this.http.get(`${this.restaurantSearch}/${name}`).map((response: Response) => {
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
}
