// booking-modal.component.ts
import { Component } from '@angular/core';

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
    rooms: 1
  };

  increment(type: 'adults' | 'kids' | 'rooms') {
    this.bookingDetails[type]++;
  }

  decrement(type: 'adults' | 'kids' | 'rooms') {
    if (this.bookingDetails[type] > 0) {
      this.bookingDetails[type]--;
    }
  }

  confirmBooking() {
    console.log('Booking details:', this.bookingDetails);
    // Implement confirmation logic or API call here
  }

  protected readonly close = close;
}
