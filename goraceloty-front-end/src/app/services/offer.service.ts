import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import { Offer } from "../entity/Offer";

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  private apiPath: string = "/offer/offers"
  constructor(private httpClient: HttpClient) {
  }
  getOffers() {
    return this.httpClient.get<Offer[]>(this.apiPath);
  }

}
