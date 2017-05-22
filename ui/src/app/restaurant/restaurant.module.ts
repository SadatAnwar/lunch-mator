import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Ng2CompleterModule} from 'ng2-completer';
import {AppRoutingModule} from '../app.routing';
import {CommonModules} from '../common/common.modules';
import {AlertService} from '../services/alert.service';
import {RestaurantService} from '../services/restaurant.services';
import {AddRestaurantComponent} from './add/add-restaurant.component';
import {RestaurantItemComponent} from './item/restaurant-item.component';
import {RestaurantListComponent} from './list/restaurant-list.component';

@NgModule({
  imports: [CommonModule, FormsModule, CommonModules, AppRoutingModule, Ng2CompleterModule],
  declarations: [AddRestaurantComponent, RestaurantListComponent, RestaurantItemComponent],
  providers: [RestaurantService, AlertService]
})
export class RestaurantModule {
}
