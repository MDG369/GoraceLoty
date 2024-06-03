import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TravelAgencyService {
  private apiUrl = 'api/OfferReservation/price'; // Base URL from environment

  constructor(private http: HttpClient) { }

  getAdjustedPrice(numAdults: number, numChildren: number,transportId: number, hotelId: number, duration: number, numOfSingleRooms: number,
                   numOfDoubleRooms: number, numOfTripleRooms: number, numOfStudios: number, numOfApartments: number): Observable<number> {
    let params = new HttpParams()
      .set('numAdults', numAdults.toString())
      .set('numChildren', numChildren.toString())
      .set('transportId', transportId.toString())
      .set('hotelId', hotelId.toString())
      .set('duration', duration.toString())
      .set('numOfSingleRooms', numOfSingleRooms.toString())
      .set('numOfDoubleRooms', numOfDoubleRooms.toString())
      .set('numOfTripleRooms', numOfTripleRooms.toString())
      .set('numOfStudios', numOfStudios.toString())
      .set('numOfApartments', numOfApartments.toString());

    // Adjust the endpoint if needed based on your actual API URL setup
    return this.http.get<number>(`${this.apiUrl}`, { params });
  }
}
