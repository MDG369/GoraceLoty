import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import { Hotel } from "../entity/Hotel";

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  private apiPath: string = "/hotel/hotels"
  constructor(private httpClient: HttpClient) {
  }
  getHotels() {
    return this.httpClient.get<Hotel[]>(this.apiPath);
  }
}
