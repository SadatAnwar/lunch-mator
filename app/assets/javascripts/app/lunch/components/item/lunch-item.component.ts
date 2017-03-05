import {Component, Input, EventEmitter, Output, OnInit} from '@angular/core';
import {LunchDto} from '../../../dto/types';
import {Router} from '@angular/router';
import {CalenderService} from '../../../services/calander.service';

@Component({
  selector: 'lunch-item',
  templateUrl: 'assets/javascripts/app/lunch/components/item/lunch-item.component.html'
})
export class LunchItemComponent implements OnInit {
  @Input()
  private lunch: LunchDto;

  @Input()
  private myLunch: boolean;

  @Output()
  private requestJoined = new EventEmitter();

  @Output()
  private requestLeave = new EventEmitter();

  private startTime: string;
  private title: string;

  constructor(private router: Router, private calenderService: CalenderService) {
  }

  ngOnInit(): void {
    this.title = `${this.lunch.lunchName ? this.lunch.lunchName : "Lunch"} at ${this.lunch.restaurant.name}`;
  }

  ngAfterContentInit() {
    this.startTime = this.calenderService.format(new Date(this.lunch.startTime));
  }

  join(lunch: LunchDto) {
    this.requestJoined.emit(lunch);
  }

  details(lunch: LunchDto) {
    this.router.navigate(['/lunch', lunch.id]);
  }

  leave(lunch: LunchDto) {
    this.requestLeave.emit(lunch);
  }
}
