import {Component, Input} from "@angular/core";
import {LunchDto} from "app/lunch/dto/types";
import {LunchService} from "../../service/lunch.service";
import {AlertLevel} from "../../../common/types/Alert";
import {ErrorMapper} from "../../../mappers/ErrorMapper";
import {AlertDisplay} from "../../../common/services/AlertDisplay";

@Component({
  selector: 'lunch-item',
  templateUrl: 'assets/javascripts/app/lunch/components/item/lunch-item.component.html'
})
export class LunchItemComponent extends AlertDisplay {
  @Input()
  lunch: LunchDto;

  constructor(private lunchService: LunchService) {
    super();
  }

  join(lunch: LunchDto) {
    console.log(lunch);
    this.lunchService.join(lunch.id)
      .subscribe((response: any) => {
        console.log(response);
        this.displayAlert(AlertLevel.SUCCESS, "Joined " + lunch);
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error))
      });
  }
}
