import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpModule} from '@angular/http';
import {AppComponent} from './app.component';
import {HeaderComponent} from './common/header.component';
import {AuthenticationModule} from './authentication/authentication.module';
import {AppRoutingModule} from './app.routing';
import {CommonModules} from './common/common.modules';

@NgModule({
  imports: [
    AppRoutingModule,
    AuthenticationModule,
    BrowserModule,
    CommonModules,
    HttpModule
  ],
  declarations: [AppComponent, HeaderComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
