import {Component, OnInit, Injectable} from '@angular/core';
import {LunchDto} from 'app/lunch/dto/types';
import {LunchService} from '../../service/lunch.service';
import {AlertLevel} from '../../../common/types/Alert';
import {ErrorMapper} from '../../../mappers/ErrorMapper';
import {AlertDisplay} from '../../../common/services/AlertDisplay';

@Component({
  selector: 'lunch-list',
  templateUrl: 'assets/javascripts/app/lunch/components/mylunch/lunch-list.component.html',
})

@Injectable()
export class MyLunchListComponent extends AlertDisplay implements OnInit {
  lunchList: LunchDto[];
  time: string;

  constructor(private lunchService: LunchService) {
    super();
  }

  ngOnInit(): void {
    this.getMyLunchList();
  }

  private getMyLunchList() {
    let lunch = this.lunchService.getMyLunchList()
      .subscribe((response: LunchDto[]) => {
        console.log(response);
        this.lunchList = response;
      }, (error: any) => {
        this.displayAlert(AlertLevel.ERROR, ErrorMapper.map(error).message)
      });
    console.log(lunch);
  }

}
