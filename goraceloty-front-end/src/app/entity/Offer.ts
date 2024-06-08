// src/app/entity/offer.model.ts
export interface Offer {
  id: number;
  transportID: number;
  hotelID: number;
  cityArrival: string;
  cityDeparture: string;
  dateStart: string;
  dateEnd: string;
}
