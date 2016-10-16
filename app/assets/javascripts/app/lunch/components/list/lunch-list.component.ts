import {Component, OnInit} from "@angular/core";
import {LunchDto} from "app/lunch/dto/types";

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
          description: 'Italian',
          website: ""
        },
        seatsLeft: 0,
        anonymous: true,
        start: new Date()
      },
      {
        restaurant: {
          name: 'Die Feinbäckerei',
          description: 'German',
          website: ""

        },
        seatsLeft: 5,
        anonymous: true,
        start: new Date()
      },
      {
        restaurant: {
          name: 'Imren',
          description: 'Disgusting Döner',
          website: ""
        },
        seatsLeft: 6,
        anonymous: false,
        start: new Date()
      }
    ];
  }
}
