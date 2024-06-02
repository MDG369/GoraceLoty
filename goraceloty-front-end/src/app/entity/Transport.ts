export class Transport {
  transportID?: number;
  typeOfTransport?: string;
  numTotalSeats?: number;
  numAvailableSeats?: number;
  numBasePrice?: number;
  cityDeparture?: string;
  cityArrival?: string;
  dateDeparture?: Date;
  dateArrival?: Date;

  constructor(init?: Partial<Transport>) {
    Object.assign(this, init);
  }
}
