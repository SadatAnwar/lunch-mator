export interface LunchDto {
    restaurant: RestaurantDto;
    seatsLeft: number;
}

export interface RestaurantDto {
    name: string;
    description: string;
}
