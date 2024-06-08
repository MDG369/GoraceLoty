import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Hotel } from '../entity/Hotel'; // Ensure the path is correct

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  private apiUrl = '/api/hotels/matching'; // Set to the endpoint of your hotel API

  constructor(private http: HttpClient) {}

  // Get all hotels
  getHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(`${this.apiUrl}`);
  }

  // Get a matching hotel by ID or other criteria
  getMatchingHotel(hotelId: number): Observable<Hotel > {
    let params = new HttpParams().set('hotelID', hotelId.toString());

    return this.http.get<Hotel[]>(`${this.apiUrl}`, { params }).pipe(
      map(hotels => hotels[0])
    );
  }
}
