import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import { Transport } from '../entity/Transport'; // Adjust the path as necessary

@Injectable({
  providedIn: 'root'
})
export class TransportService {
  private apiUrl = '/api/transports/matching'; // Adjust API URL as necessary

  constructor(private http: HttpClient) {}

  getTransports(): Observable<Transport[]> {
    return this.http.get<Transport[]>(`$this.apiUrl`);
  }
  getMatchingTransport(offerId: number): Observable<Transport>{
    // Create HttpParams object
    let params = new HttpParams();
    params = params.append('transportID', offerId.toString());

    return this.http.get<Transport[]>(`${this.apiUrl}`, { params }).pipe(
      map(transports => transports[0])
    );
  }


  // getMatchingTransport(transportId: number): Observable<Transport> {
  //   let params = new HttpParams();
  //   params = params.append('transportID', transportId.toString());
  //
  //   // Make the HTTP GET request with params
  //   return this.http.get<Transport>(this.apiUrl, { params });
  // }
}
