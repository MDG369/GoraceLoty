// transport.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Offer } from '../entity/Offer'; // Ensure you have an Offer model

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  private apiUrl = '/api/offers/offers';

  constructor(private http: HttpClient) {}

  getOffers(cityArrival?: string, dateStart?: string, dateEnd?: string): Observable<Offer[]> {
    let params = new HttpParams();

    // Append parameters if they are provided
    if (cityArrival) {
      params = params.append('cityArrival', cityArrival);
    }
    if (dateStart) {
      params = params.append('dateStart', dateStart);
    }
    if (dateEnd) {
      params = params.append('dateEnd', dateEnd);
    }
    const fullUrl = `${this.apiUrl}?${params.toString()}`; // Construct full URL
    console.log('Making request to:', fullUrl);

    return this.http.get<Offer[]>(this.apiUrl);
    //{ params }
  }
}
