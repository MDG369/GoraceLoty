import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import { Transport } from "../entity/Transport";

@Injectable({
  providedIn: 'root'
})
export class TransportService {
  private apiPath: string = "/transport/transports"
  constructor(private httpClient: HttpClient) {
  }
  getOffers() {
    return this.httpClient.get<Transport[]>(this.apiPath);
  }

  getTransportsByIds(transportIds: number[]) {

  }
}
