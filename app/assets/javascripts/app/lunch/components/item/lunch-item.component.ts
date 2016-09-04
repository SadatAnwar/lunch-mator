import {Component, Input} from '@angular/core';
import {LunchDto} from 'app/lunch/dto/types';

@Component({
    selector: 'lunch-item',
    templateUrl: 'assets/javascripts/app/lunch/components/item/lunch-item.component.html'
})
export class LunchItemComponent {
    @Input()
    lunch: LunchDto;
}
