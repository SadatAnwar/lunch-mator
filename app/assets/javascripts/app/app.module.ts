import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpModule} from '@angular/http';
import {AppComponent} from 'app/app.component';
import {routing} from 'app/app.routing';
import {HeaderComponent} from 'app/common/components/header/header.component';
import {LunchModule} from 'app/lunch/lunch.module';
import {AuthenticationModule} from 'app/authentication/authentication.module';
import {RestaurantModule} from './restaurant/restaurant.module';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    LunchModule,
    AuthenticationModule,
    RestaurantModule,
    routing
  ],
  declarations: [AppComponent, HeaderComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
