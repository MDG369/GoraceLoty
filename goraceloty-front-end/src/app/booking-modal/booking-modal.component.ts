import { Component, EventEmitter, Output, OnInit, Input } from '@angular/core';
import { TravelAgencyService } from "../services/travel.agency.service";
import { ReservationRequest } from "../entity/ReservationRequest";
import {OfferService} from "../services/offer.service";

type NumericKeysOfReservationRequest = {
  [Key in keyof ReservationRequest]: ReservationRequest[Key] extends number ? Key : never
}[keyof ReservationRequest];

@Component({
  selector: 'app-booking-modal',
  standalone: true,
  styleUrls: ['./booking-modal.component.css'],
  templateUrl: './booking-modal.component.html'
})

export class BookingModalComponent implements OnInit {
  // @ts-ignore
  @Input() transportId: number = 1;  // Default values as placeholders
  // @ts-ignore
  @Input() hotelId: number = 1;
  // @ts-ignore
  @Input() offerId: number = 1;

  reservationRequest!: ReservationRequest
  constructor(private travelAgency: TravelAgencyService,
              private offerService: OfferService) {}

  ngOnInit(): void {
    this.initializeReservationRequest();
  }
  price: number = 1000;  // Initialize price here

  private initializeReservationRequest() {
    this.reservationRequest = new ReservationRequest();
    this.reservationRequest.hotelID = this.hotelId;
    this.reservationRequest.transportID = this.transportId;
    this.reservationRequest.clientID = 1;
    this.reservationRequest.offerID = this.offerId;
    console.log(this.reservationRequest);
    console.log(this.transportId);
  }

  increment(field: NumericKeysOfReservationRequest) {
    if (this.reservationRequest[field] !== undefined) {
      this.reservationRequest[field]++;
    }
  }

  decrement(field: NumericKeysOfReservationRequest) {
    if (this.reservationRequest[field] !== undefined && this.reservationRequest[field] > 0) {
      this.reservationRequest[field]--;
    }
  }

  reloadPrice(): void {
    this.travelAgency.getAdjustedPrice(
      this.reservationRequest.numOfAdults,
      this.reservationRequest.numOfChildren,
      this.reservationRequest.transportID,
      this.reservationRequest.hotelID,
      this.reservationRequest.numOfDays,
      this.reservationRequest.numOfSingleRooms,
      this.reservationRequest.numOfDoubleRooms,
      this.reservationRequest.numOfTripleRooms,
      this.reservationRequest.numOfStudios,
      this.reservationRequest.numOfApartments
    ).subscribe({
      next: (newPrice: number) => {
        this.price = newPrice;
        console.log(`Adjusted price is ${this.price}`);
      },
      error: (error) => {
        console.error('Error fetching adjusted price', error);
      }
    });
  }

  confirmBooking():void{
    this.offerService.startSaga(this.reservationRequest).subscribe(data=>{
      console.log(`Kliknieto rezerwacje`);
    });
  }

  @Output() closeModal = new EventEmitter<void>();

  close(): void {
    this.closeModal.emit();
  }
}
