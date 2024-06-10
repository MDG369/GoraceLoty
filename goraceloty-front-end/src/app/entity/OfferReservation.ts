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

  static countHotelOccurrences(reservations: OfferReservation[]): { [key: number]: number } {
    return reservations.reduce((acc, reservation) => {
      acc[reservation.hotelID] = (acc[reservation.hotelID] || 0) + 1;
      return acc;
    }, {} as { [key: number]: number });
  }

  static countTransportOccurrences(reservations: OfferReservation[]): { [key: number]: number } {
    return reservations.reduce((acc, reservation) => {
      acc[reservation.transportID] = (acc[reservation.transportID] || 0) + 1;
      return acc;
    }, {} as { [key: number]: number });
  }
}
