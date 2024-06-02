export class Transport {
  transportID: number;
  typeOfTransport: string;
  numTotalSeats: number;
  numAvailableSeats: number;
  numBasePrice: number;
  cityDeparture: string;
  cityArrival: string;
  dateDeparture: Date;
  dateArrival: Date;

  constructor(
    transportID?: number,
    typeOfTransport?: string,
    numTotalSeats?: number,
    numAvailableSeats?: number,
    numBasePrice?: number,
    cityDeparture?: string,
    cityArrival?: string,
    dateDeparture?: Date,
    dateArrival?: Date
  ) {
    this.transportID = transportID || 0;
    this.typeOfTransport = typeOfTransport || '';
    this.numTotalSeats = numTotalSeats || 0;
    this.numAvailableSeats = numAvailableSeats || 0;
    this.numBasePrice = numBasePrice || 0;
    this.cityDeparture = cityDeparture || '';
    this.cityArrival = cityArrival || '';
    this.dateDeparture = dateDeparture || new Date();
    this.dateArrival = dateArrival || new Date();
  }
}
