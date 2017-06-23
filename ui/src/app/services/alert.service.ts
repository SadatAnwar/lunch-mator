import {Injectable} from '@angular/core';
import {Router, NavigationStart} from '@angular/router';
import {Observable} from 'rxjs';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class AlertService {
  private alert = new Subject<any>();
  private keepAfterNavigationChange = false;

  constructor(private router: Router) {
    // clear alert message on route change
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (this.keepAfterNavigationChange) {
          // only keep for a single location change
          this.keepAfterNavigationChange = false;
        } else {
          // clear alert
          this.alert.next();
        }
      }
    });
  }

  success(message: string, keepAfterNavigationChange = false, timeOut = 3000) {
    this.keepAfterNavigationChange = keepAfterNavigationChange;
    this.alert.next({successMessage: message});

    setTimeout(() => this.clear(), timeOut);
  }

  error(message: string, keepAfterNavigationChange = true, timeOut = 3000) {
    this.keepAfterNavigationChange = keepAfterNavigationChange;
    this.alert.next({error: message});

    if (timeOut != null) {
      setTimeout(() => this.clear(), timeOut);
    }
  }

  getMessage(): Observable<any> {
    return this.alert.asObservable();
  }

  private clear() {
    console.log("clear alert");
    this.alert.next({});
  }
}
