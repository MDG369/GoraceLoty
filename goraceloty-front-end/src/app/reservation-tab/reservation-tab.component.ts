import { Component, OnInit } from '@angular/core';
import { OfferReservation } from '../entity/OfferReservation';
import { TravelAgencyService } from '../services/travel.agency.service';

@Component({
  selector: 'app-reservation-tab',
  templateUrl: './reservation-tab.component.html',
  styleUrl: './reservation-tab.component.css'
})
export class ReservationTabComponent implements OnInit {
  reservations: OfferReservation[] = [];

  constructor(private travelAgencyService: TravelAgencyService) {
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
