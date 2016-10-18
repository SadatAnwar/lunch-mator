import {Component} from "@angular/core";
import {CompleterService, CompleterData, CompleterItem} from "ng2-completer";
import {AlertDisplay} from "../../../common/services/AlertDisplay";
import {AlertLevel} from "../../../common/types/Alert";

@Component({
  selector: 'add-lunch',
  templateUrl: 'assets/javascripts/app/lunch/components/addlunch/add-lunch.component.html'
})

export class AddLunchComponent extends AlertDisplay {
  waiting: boolean = false;
  lunchName: string;
  restaurantName: string;
  startTime: Date;
  maxSize: number;
  dataService: CompleterData;
  randomWord: string = "an Awesome";

  constructor(private completerService: CompleterService) {
    super();
    this.dataService = completerService.remote("/rest/restaurants/search/", "name", 'name');
  }

  createTable() {
    console.log(this.restaurantName);
    this.displayAlert(AlertLevel.SUCCESS, "Yippee!!", 5);
  }

  reset() {
    this.lunchName = null;
    this.restaurantName = null;
    this.startTime = null;
    this.maxSize = null;
  }

  select(selected: CompleterItem) {
    console.log(selected);
    console.log(selected.title);
  }
}
