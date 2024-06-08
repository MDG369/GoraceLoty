import {Injectable} from "@angular/core";
import {ChangesMessage} from "../entity/ChangesMessage";
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ChangesService {

  changes: ChangesMessage[] = [];
  private changesSubject: Subject<ChangesMessage[]> = new Subject<ChangesMessage[]>();


  addChange(change: ChangesMessage) {
    this.changes.push(change);
    this.changesSubject.next(this.changes);
  }

  getChangesObservable(): Observable<ChangesMessage[]> {
    return this.changesSubject.asObservable();
  }

  changePopularity() {}

  getChanges(): ChangesMessage[] {
    return this.changes;
  }
  // Perhaps give a time threshold?
  getNumberOfChangesForTransportId(transportId: number) {
    this.changes.filter(change => change.transportId == transportId).length;
  }

  getNumberOfChangesForHotelId(hotelId: number) {
    return this.changes.filter(change => change.hotelId == hotelId).length;
  }

  getNumberOfChangesForOfferId(offerId: number) {
    return this.changes.filter(change => change.offerId == offerId).length;
  }
}
