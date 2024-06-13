import { Component, OnDestroy, OnInit } from '@angular/core';
import { OfferReservation } from '../entity/OfferReservation';
import { TravelAgencyService } from '../services/travel.agency.service';
import { WebsocketService } from '../services/websocket.service';
import { Subscription } from 'rxjs';
import {MessageService} from "primeng/api";
import {ChangesMessage} from "../entity/ChangesMessage";

@Component({
  selector: 'app-reservation-tab',
  templateUrl: './reservation-tab.component.html',
  styleUrl: './reservation-tab.component.css'
})
export class ReservationTabComponent implements OnInit {
  reservations: OfferReservation[] = [];
  message: string;
  messages: string[] = [];

  constructor(private travelAgencyService: TravelAgencyService, ) {
  }


  ngOnInit() {
    this.travelAgencyService.getUsersReservations(1).subscribe(data => this.reservations = data);
  }

  payReservation(reservationID: number) {
    this.travelAgencyService.payReservation(reservationID).subscribe(
      data => {
        window.location.reload();
      }
    )
  }

}
