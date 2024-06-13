// transport.service.ts
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import { OfferChange } from '../entity/OfferChange'; // Ensure you have an Offer model
import{ReservationRequest} from "../entity/ReservationRequest";

@Injectable({
  providedIn: 'root'
})
export class OfferChangeService {
  private apiUrl = '/api/offers/changes';
  private sagaUrl = '/api/offers'
  constructor(private http: HttpClient) {}

  getOfferChanges(): Observable<OfferChange[]> {
    let params = new HttpParams();

    const fullUrl = `${this.apiUrl}?${params.toString()}`; // Construct full URL
    console.log('Making request to:', fullUrl);

    return this.http.get<OfferChange[]>(fullUrl);
    //{ params }
  }

}
