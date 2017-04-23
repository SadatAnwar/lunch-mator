import {AppComponent} from './app.component';
import {AppRoutingModule} from './app.routing';
import {AuthenticationModule} from './authentication/authentication.module';
import {BrowserModule} from '@angular/platform-browser';
import {HeaderComponent} from './common/header.component';
import {HttpModule} from '@angular/http';
import {LunchModule} from './lunch/lunch.module';
import {NgModule} from '@angular/core';
import {RestaurantModule} from './restaurant/restaurant.module';
import {BsDropdownModule} from 'ngx-bootstrap';

@NgModule({
  declarations: [AppComponent, HeaderComponent],
  imports: [
    AppRoutingModule,
    AuthenticationModule,
    BrowserModule,
    HttpModule,
    LunchModule,
    BsDropdownModule.forRoot(),
    RestaurantModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
