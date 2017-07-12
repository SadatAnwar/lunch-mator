import {Injectable} from '@angular/core';

@Injectable()
export class PlatformIdentificationService {

  public isWindows(): boolean {
    return navigator.platform.toLowerCase().indexOf('mac') < 0
  }
}
