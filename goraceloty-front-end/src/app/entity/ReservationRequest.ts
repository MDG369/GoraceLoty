export class ReservationRequest {
  reservationRequestID: string; // UUIDs are strings in TypeScript contexts
  offerID: number;
  hotelID: number;
  transportID: number;
  clientID: number;
  startDate: string; // Dates can be represented as ISO strings in TypeScript
  numOfDays: number;
  numOfSingleRooms: number;
  numOfDoubleRooms: number;
  numOfTripleRooms: number;
  numOfStudios: number;
  numOfApartments: number;
  numOfAdults: number;
  numOfChildren: number;

  constructor(init?: Partial<ReservationRequest>) {
    this.reservationRequestID = init?.reservationRequestID || this.generateUUID();
    this.offerID = init?.offerID || 0;
    this.hotelID = init?.hotelID || 0;
    this.transportID = init?.transportID || 0;
    this.clientID = init?.clientID || 0;
    this.startDate = init?.startDate || '';
    this.numOfDays = init?.numOfDays || 0;
    this.numOfSingleRooms = init?.numOfSingleRooms || 0;
    this.numOfDoubleRooms = init?.numOfDoubleRooms || 0;
    this.numOfTripleRooms = init?.numOfTripleRooms || 0;
    this.numOfStudios = init?.numOfStudios || 0;
    this.numOfApartments = init?.numOfApartments || 0;
    this.numOfAdults = init?.numOfAdults || 0;
    this.numOfChildren = init?.numOfChildren || 0;
  }

  private generateUUID(): string {
    // Placeholder for UUID generation - in a real application you might use a library
    return 'xxxx-xxxx-xxxx-xxxx'.replace(/[x]/g, c => {
      const r = Math.floor(Math.random() * 16);
      return r.toString(16);
    });
  }
}
