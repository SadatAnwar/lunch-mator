import {ErrorDto} from '../lunch/dto/types';
export class ErrorMapper {

  public static map(error: any): ErrorDto {
    console.log(error);
    var parsedError = JSON.parse(error._body);
    console.log(parsedError);
    return parsedError;
  }
}
