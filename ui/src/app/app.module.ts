import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CollapseModule} from 'ngx-bootstrap/collapse';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app.routing';
import {AuthenticationModule} from './authentication/authentication.module';
import {CommonModules} from './common/common.modules';
import {HeaderComponent} from './common/header/header.component';
import {LunchModule} from './lunch/lunch.module';
import {RestaurantModule} from './restaurant/restaurant.module';
import {AlertService} from './services/alert.service';
import {PlatformIdentificationService} from './services/platform-identification.service';

@NgModule({
  imports: [
    AppRoutingModule,
    AuthenticationModule,
    BrowserModule,
    BsDropdownModule.forRoot(),
    BrowserAnimationsModule,
    CollapseModule.forRoot(),
    CommonModules,
    HttpModule,
    LunchModule,
    RestaurantModule
  ],
  declarations: [AppComponent, HeaderComponent],
  providers: [AlertService, PlatformIdentificationService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
