import {Component, OnInit} from "@angular/core";
import {LunchDto} from "app/lunch/dto/types";
import {LunchService} from "../../service/lunch.service";
import {AlertLevel} from "../../../common/types/Alert";
import {ErrorMapper} from "../../../mappers/ErrorMapper";
import {AlertDisplay} from "../../../common/services/AlertDisplay";

@Component({
  selector: 'lunch-list',
  templateUrl: 'assets/javascripts/app/lunch/components/list/lunch-list.component.html'
})
export class LunchListComponent extends AlertDisplay implements OnInit {
  lunchList: LunchDto[];

  constructor(private lunchService: LunchService) {
    super();
  }

  ngOnInit(): void {
    this.getLunchList();
  }

  //TODO: replace with the LunchService
  private getLunchList() {
    let lunch = this.lunchService.getLunchList()
      .subscribe((response: LunchDto[]) => {
        console.log(response);
        this.lunchList = response;
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error).message)
      });
    console.log(lunch);
    return null;
  }
}
