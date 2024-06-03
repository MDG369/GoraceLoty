import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TravelAgencyService {
  private apiUrl = 'api/OfferReservation/price'; // Base URL from environment

  constructor(private http: HttpClient) { }

  getAdjustedPrice(reservationId: number, numAdults: number): Observable<number> {
    let params = new HttpParams()
      .set('reservationId', reservationId.toString())
      .set('numAdults', numAdults.toString());

    // Adjust the endpoint if needed based on your actual API URL setup
    return this.http.get<number>(`${this.apiUrl}`, { params });
  }
}
