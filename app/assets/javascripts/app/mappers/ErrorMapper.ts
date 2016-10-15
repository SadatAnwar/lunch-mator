export class ErrorMapper {

  public static map(error: any) {
    var error = JSON.parse(error._body);
    return error;
  }
}
