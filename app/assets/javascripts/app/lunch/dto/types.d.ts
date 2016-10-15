export interface LunchDto {
  restaurant: RestaurantDto;
  seatsLeft: number;
  start: Date;
  anonymous: boolean;
}

export interface RestaurantDto {
  name: string;
  description: string;
}
