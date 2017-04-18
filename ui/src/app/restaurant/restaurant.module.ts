import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {AddRestaurantComponent} from './add-restaurant.component';
import {Ng2CompleterModule} from 'ng2-completer';
import {RestaurantService} from '../services/restaurant.services';
import {CommonModules} from '../common/common.modules';
import {RestaurantListComponent} from './restaurant-list.component';
import {RestaurantItemComponent} from './restaurant-item.component';
import {AppRoutingModule} from '../app.routing';

@NgModule({
  imports: [CommonModule, FormsModule, Ng2CompleterModule, CommonModules, AppRoutingModule],
  declarations: [AddRestaurantComponent, RestaurantListComponent, RestaurantItemComponent],
  providers: [RestaurantService]
})
export class RestaurantModule {
}
