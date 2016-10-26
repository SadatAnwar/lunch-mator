export interface LunchDto {
  restaurant: RestaurantDto;
  id: number;
  lunchName: string;
  maxSize: number;
  spotsLeft: number;
  startTime: number;
  anonymous: boolean;
}

export interface RestaurantDto {
  id: number;
  name: string;
  website: string;
  description: string;
}

export interface CreateRestaurantDto {
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

export interface ErrorDto {
  message: string;
}

export interface DateTime {
  YY: number;
  MM: number;
  DD: number;
  hh: number;
  mm: number;

}

export interface ParticipantDto {
  name: string;
  joined: Date;
}

export interface LunchDetailDto extends LunchDto {
  participants: ParticipantDto[];
}
