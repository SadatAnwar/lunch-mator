import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from 'app/app.component';
import {routing} from 'app/app.routing';
import {HeaderComponent} from 'app/common/components/header/header.component';

@NgModule({
    imports:      [ BrowserModule, routing ],
    declarations: [ AppComponent, HeaderComponent ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }
