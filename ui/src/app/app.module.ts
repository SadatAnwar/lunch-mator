import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpModule} from '@angular/http';
import {AppComponent} from './app.component';
import {HeaderComponent} from './common/header.component';
import {LunchModule} from './lunch/lunch.module';
import {AuthenticationModule} from './authentication/authentication.module';
import {RestaurantModule} from './restaurant/restaurant.module';
import {AppRoutingModule} from './app.routing';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    LunchModule,
    AuthenticationModule,
    RestaurantModule,
    AppRoutingModule
  ],
  declarations: [AppComponent, HeaderComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
