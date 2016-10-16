import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {AddRestaurantComponent} from "./components/addrestaurant/addrestaurant.component";
import {Ng2CompleterModule} from "ng2-completer";
import {routing} from "./restaurant.routing";
import {RestaurantService} from "./services/restaurant.services";

@NgModule({
  imports: [CommonModule, FormsModule, Ng2CompleterModule, routing],
  declarations: [AddRestaurantComponent],
  providers: [RestaurantService]
})
export class RestaurantModule {
}
