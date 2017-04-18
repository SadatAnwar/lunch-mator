import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'login',
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit, OnDestroy {
  private subscription: Subscription;

  private origin: String;

  constructor(private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.subscription = this.activatedRoute.queryParams.subscribe(
      (param: any) => {
        if (param['origin']) {
          this.origin = param['origin'];
        }
      });
  }

  public googleLogin() {
    if (this.origin) {
      window.location.href = `/google-login?origin=${this.origin}`;
    } else {
      window.location.href = '/google-login';
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
