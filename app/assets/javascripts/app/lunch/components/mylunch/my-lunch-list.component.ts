import {Component, OnInit, Injectable} from "@angular/core";
import {LunchDto} from "app/lunch/dto/types";
import {LunchService} from "../../service/lunch.service";
import {AlertDisplay} from "../../../common/services/AlertDisplay";

@Component({
  selector: 'my-lunch-list',
  templateUrl: 'assets/javascripts/app/lunch/components/mylunch/my-lunch-list.component.html'
})

@Injectable()
export class MyLunchListComponent extends AlertDisplay implements OnInit {
  lunchs: LunchDto[];

  constructor(private lunchService: LunchService) {
    super();
  }

  ngOnInit(): void {
    this.getMyLunchList();
  }

  private getMyLunchList() {
    let lunch = this.lunchService.getMyLunchList()
      .subscribe((response: LunchDto[]) => {
        this.lunchs = response;
      }, (error: any) => {
      });
  }
}
