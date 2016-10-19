export interface LunchDto {
  restaurant: RestaurantDto;
  id: number;
  size: number;
  start: Date;
  anonymous: boolean;
}

export interface RestaurantDto {
  id?: number;
  name: string;
  website: string;
  description: string;
}

export interface CreateLunchDto {
  restaurantId: number;
  lunchName: string;
  startTime: number;
  anonymous: boolean;
  maxSize: number;
}
