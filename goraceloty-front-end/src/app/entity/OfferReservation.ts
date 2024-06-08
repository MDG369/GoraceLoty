export class OfferReservation {
  constructor(reservationId: number, startDate: string, numAdult: number, numChildren: number, numOfDays: number, reservationTime: string, adjustedPrice: number, offerID: number, hotelID: number, transportID: number, isPaid: boolean) {
    this.reservationID = reservationId;
    this.startDate = startDate;
    this.numAdult = numAdult;
    this.numChildren = numChildren;
    this.numOfDays = numOfDays;
    this.reservationTime = reservationTime;
    this.adjustedPrice = adjustedPrice;
    this.offerID = offerID;
    this.hotelID = hotelID;
    this.transportID = transportID;
    this.isPaid = isPaid
  }
  reservationID: number;
  startDate: string;
  numAdult: number;
  numChildren: number;
  numOfDays: number;
  reservationTime: string;
  adjustedPrice: number;
  offerID: number;
  hotelID: number;
  transportID: number;
  isPaid: boolean;

}
