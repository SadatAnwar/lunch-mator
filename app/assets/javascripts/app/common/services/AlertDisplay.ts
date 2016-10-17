import {Alert, AlertLevel} from '../types/Alert';

export abstract class AlertDisplay {
  private alertClass: string = "alert-box-hidden";
  private alert = new Alert();

  protected displaySuccessWithTimeOut(message: string, clearAfter: number) {
    this.alert.successMessage = message;
    setTimeout(() => {
      this.clear();
    }, clearAfter * 1000);

  }

  private clearAfter(timeOut: number) {
    setTimeout(() => {
      this.clear();
    }, timeOut * 1000);

  }

  private displaySuccess(message: string, timeOut: number) {
    this.alert.successMessage = message;
    if (timeOut > 0) {
      this.clearAfter(timeOut);
    }
  }

  private displayInfo(message: string, timeOut: number) {
    this.alert.info = message;
    if (timeOut > 0) {
      this.clearAfter(timeOut);
    }
  }

  private displayError(message: string, timeOut: number) {
    this.alert.error = message;
    if (timeOut > 0) {
      this.clearAfter(timeOut);
    }
  }

  protected displayAlert(level: AlertLevel, message: string, timeout: number = -1) {
    if (level == AlertLevel.INFO) {
      this.displayInfo(message, timeout);
    }
    switch (level) {
      case AlertLevel.INFO:
        this.displayInfo(message, timeout);
        break;
      case AlertLevel.ERROR:
        this.displayError(message, timeout);
        break;
      case AlertLevel.SUCCESS:
        this.displaySuccess(message, timeout)
    }
    this.alertClass = "alert-box-visible"
  }

  protected clear() {
    this.alert = new Alert();
    this.alertClass = "alert-box-hidden"
  }
}
