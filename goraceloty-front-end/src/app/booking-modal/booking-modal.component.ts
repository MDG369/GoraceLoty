// booking-modal.component.ts
import {Component, EventEmitter, Output, OnInit} from '@angular/core';
import {TravelAgencyService} from "../services/travel.agency.service";
import {ReservationRequest} from "../entity/ReservationRequest";

@Component({
  selector: 'app-booking-modal',
  templateUrl: './booking-modal.component.html',
  standalone: true,
  styleUrls: ['./booking-modal.component.css']
})

export class BookingModalComponent {
  bookingDetails = {
    adults: 1,
    kids: 0,
    rooms: 1,
    price: 1000
  };
  constructor (private travelAgency: TravelAgencyService){}
  //private reservationRequest: ReservationRequest){}

  increment(type: 'adults' | 'kids' | 'rooms') {
    this.bookingDetails[type]++;
  }

  decrement(type: 'adults' | 'kids' | 'rooms') {
    if (this.bookingDetails[type] > 0) {
      this.bookingDetails[type]--;
    }
  }

  confirmBooking(): void {
    this.travelAgency.getAdjustedPrice(1, this.bookingDetails['adults'])  // Assuming '1' is just a placeholder
      .subscribe({
        next: (price: number) => {
          this.bookingDetails.price = price;
          console.log('Updated booking price:', this.bookingDetails.price);
          // Optionally close the modal after confirming booking
          //this.closeModal.emit();
        },
        error: (error) => {
          console.error('Error fetching adjusted price', error);
        }
      });
  }

  protected readonly close = close;
  @Output() closeModal = new EventEmitter<unknown>();
}
