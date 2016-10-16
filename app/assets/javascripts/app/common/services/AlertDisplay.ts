import {Alert} from '../types/Alert';

export abstract class AlertDisplay {
  protected alert = new Alert();

  protected displaySuccess(message: string) {
    this.alert.successMessage = message;
  }

  protected displaySuccessWithTimeOut(message: string, clearAfter: number) {
    this.alert.successMessage = message;
    setTimeout(() => {
      this.clear();
    }, clearAfter*1000);

  }

  protected displayAlert(message: string) {
    this.alert.message = message;
  }

  protected clear() {
    this.alert = new Alert();
  }
}
