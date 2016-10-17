export class Alert {
  info: string;
  successMessage: string;
  error: string;
}

export class MappedAlert {
  message: string;
  level: AlertLevel;
}

export enum AlertLevel{
  SUCCESS,
  INFO,
  ERROR
}
