import {Component, OnInit} from '@angular/core';
import {LunchDto} from 'app/lunch/dto/types';

@Component({
    selector: 'lunch-list',
    templateUrl: 'assets/javascripts/app/lunch/components/list/lunch-list.component.html'
})
export class LunchListComponent implements OnInit {
    lunchList: LunchDto[];

    ngOnInit(): void {
        this.lunchList = this.getLunchList();
    }

    //TODO: replace with the LunchService
    private getLunchList() {
        return [
            {
                restaurant: {
                    name: 'Da Dante',
                    description: 'Italian'
                },
                seatsLeft: 3
            },
            {
                restaurant: {
                    name: 'Die Feinbäckerei',
                    description: 'German'
                },
                seatsLeft: 5
            },
            {
                restaurant: {
                    name: 'Imren',
                    description: 'Disgusting Döner'
                },
                seatsLeft: 6
            }
        ];
    }
}
