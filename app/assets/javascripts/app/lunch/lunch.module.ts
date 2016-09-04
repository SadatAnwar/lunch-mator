import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LunchItemComponent} from 'app/lunch/components/item/lunch-item.component';
import {LunchListComponent} from 'app/lunch/components/list/lunch-list.component';
import {routing} from 'app/lunch/lunch.routing';

@NgModule({
    imports: [ CommonModule, routing ],
    declarations: [ LunchItemComponent, LunchListComponent ]
})
export class LunchModule { }
