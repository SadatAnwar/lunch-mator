import {Component, Input, EventEmitter, Output} from "@angular/core";
import {LunchDto} from "app/lunch/dto/types";
import {Router} from "@angular/router";
import {CalenderService} from "../../service/calander.service";

@Component({
  selector: 'lunch-item',
  templateUrl: 'assets/javascripts/app/lunch/components/item/lunch-item.component.html'
})
export class LunchItemComponent {

  @Input()
  lunch: LunchDto;

  @Output()
  joined = new EventEmitter();

  startTime: string;

  constructor(private router: Router, private calenderService: CalenderService) {
  }

  ngAfterContentInit() {
    this.startTime = this.calenderService.format(new Date(this.lunch.startTime));
  }

  join(lunch: LunchDto) {
    this.joined.emit(lunch);
  }

  onSelect(lunch: LunchDto) {
    this.router.navigate(['/lunch', lunch.id]);
  }
}
