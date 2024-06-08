import { Component, OnDestroy, OnInit } from '@angular/core';
import { OfferReservation } from '../entity/OfferReservation';
import { TravelAgencyService } from '../services/travel.agency.service';
import { WebsocketService } from '../services/ws.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-reservation-tab',
  templateUrl: './reservation-tab.component.html',
  styleUrl: './reservation-tab.component.css'
})
export class ReservationTabComponent implements OnInit, OnDestroy {
  reservations: OfferReservation[] = [];
  message: string;
  messages: string[] = [];

  constructor(private travelAgencyService: TravelAgencyService, private webSocketService: WebsocketService) {
  }
  private taskProgressSubscription: Subscription;


  ngOnInit() {
    this.webSocketService.tearDownWebsocketEvents();
    this.taskProgressSubscription = this.webSocketService.getTaskProgressObservable
      .subscribe((status: string) => this.updateCurrentProgress(status));

    this.travelAgencyService.getUsersReservations(1).subscribe(data => this.reservations = data);
  }

  ngOnDestroy() {
    console.log("Destroing");
    this.webSocketService.tearDownWebsocketEvents();
  }

  updateCurrentProgress(status: string) {
    console.log(status);
  }

  payReservation(reservationID: number) {
    this.travelAgencyService.payReservation(reservationID).subscribe(
      data => {
        window.location.reload();
      }
    )
  }

}
