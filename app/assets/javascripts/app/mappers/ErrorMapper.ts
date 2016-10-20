import {ErrorDto} from "../lunch/dto/types";
export class ErrorMapper {

  public static map(error: any): ErrorDto {
    var error = JSON.parse(error._body);
    return error;
  }
}
