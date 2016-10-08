import {Error} from "app/authentication/types/error";

export class PasswordValidationService {
  PASSWORD_MIN_LENGTH = 5;
  error: Error = null;

  constructor() {
  }

  public isValid(): boolean {
    var b = this.error.message == null || this.error.message.length == 0;
    console.log(b);
    return b;
  }

  public validate(password: string, confirmPassword: string) {
    this.error = new Error();
    console.log(this.error);
    if (password.length < this.PASSWORD_MIN_LENGTH) {
      var message = "Your password has gotta be at least " + this.PASSWORD_MIN_LENGTH + " characters";
      this.addMessage(this.error, message)
    }
    if (password != confirmPassword) {
      var message = "Your passwords don't match";
      this.addMessage(this.error, message)
    }
  }

  private addMessage(error: Error, message: string) {
    if (error.message != null && error.message.length > 0) {
      error.message += " and ";
      message = message.toLocaleLowerCase();
    }
    error.message += message;
    console.log(error);
  }

  public getError() {
    return this.error;
  }
}
