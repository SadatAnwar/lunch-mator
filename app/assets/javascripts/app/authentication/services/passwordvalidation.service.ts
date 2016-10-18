export class PasswordValidationService {
  private PASSWORD_MIN_LENGTH = 5;
  errorMessage: string = null;

  constructor() {
  }

  public isValid(): boolean {
    return this.errorMessage == null || this.errorMessage.length == 0;
  }

  public validate(password: string, confirmPassword: string) {
    this.errorMessage = null;
    if (password.length < this.PASSWORD_MIN_LENGTH) {
      var message = "Your password has gotta be at least " + this.PASSWORD_MIN_LENGTH + " characters";
      this.addMessage(message)
    }
    if (password != confirmPassword) {
      var message = "Your passwords don't match";
      this.addMessage(message)
    }
  }

  private addMessage(message: string) {
    if (this.errorMessage == null) {
      this.errorMessage = message;
    }
    else {
      this.errorMessage += " and ";
      message = message.toLocaleLowerCase();
      this.errorMessage += message;
    }
  }

  public getError() {
    return this.errorMessage;
  }
}
