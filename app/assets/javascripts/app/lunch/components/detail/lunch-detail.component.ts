import {Component} from "@angular/core";
import {LunchDetailDto} from "app/lunch/dto/types";
import {Router, ActivatedRoute, Params} from "@angular/router";
import {LunchService} from "../../service/lunch.service";
import {AlertLevel} from "../../../common/types/Alert";
import {AlertDisplay} from "../../../common/services/AlertDisplay";

@Component({
  selector: 'lunch-detail',
  templateUrl: 'assets/javascripts/app/lunch/components/detail/lunch-detail.component.html'
})
export class LunchDetailComponent extends AlertDisplay {

  lunch: LunchDetailDto;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: LunchService) {
    super();
    console.log("in constructor");
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      let id = +params['id']; // (+) converts string 'id' to a number
      console.log("id is: " + id);
      this.service.getLunchDetails(id)
        .subscribe((response: LunchDetailDto) => {
          console.log(response);
          this.lunch = response;
        }, (error: any) => {
          this.displayAlert(AlertLevel.ERROR, error)
        });
    });
  }
}
