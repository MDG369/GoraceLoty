import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  private apiPath: string = "/api/hotels"
  constructor(private httpClient: HttpClient) {
  }
  getHotels() {
    return this.httpClient.get<string>(this.apiPath);
  }
}
