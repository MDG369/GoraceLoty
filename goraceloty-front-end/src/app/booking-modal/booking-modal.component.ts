import { Component, EventEmitter, Output, OnInit, Input } from '@angular/core';
import { TravelAgencyService } from "../services/travel.agency.service";
import { ReservationRequest } from "../entity/ReservationRequest";
import {OfferService} from "../services/offer.service";
import {MessageService} from "primeng/api";
import {AuthService} from "../services/auth.service";
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
  @Input()
  transportId: number = 1;  // Default values as placeholders
  @Input()
  hotelId: number = 1;
  @Input() offerId: number = 1 ;

  reservationRequest!: ReservationRequest
  constructor(private travelAgency: TravelAgencyService,
              private offerService: OfferService,
  private authService: AuthService,
  private messageService: MessageService) {}

  ngOnInit(): void {
    this.initializeReservationRequest();
  }
  price: number = 0;  // Initialize price here

  private initializeReservationRequest() {
    this.reservationRequest = new ReservationRequest();
    this.reservationRequest.hotelID = this.hotelId;
    this.reservationRequest.transportID = this.transportId;
    this.reservationRequest.clientID = this.authService.userId;
    this.reservationRequest.offerID = this.offerId;
    this.reservationRequest.startDate = '2024-06-04';
    console.log(this.reservationRequest);
    console.log(this.transportId);
  }

  increment(field: NumericKeysOfReservationRequest) {
    this.reloadPrice();
    if (this.reservationRequest[field] !== undefined) {
      this.reservationRequest[field]++;
    }
  }

  decrement(field: NumericKeysOfReservationRequest) {
    this.reloadPrice();
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
      this.reservationRequest.numOfApartments,
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

      this.succesfulTransaction.emit();
      this.closeModal.emit();

    });
  }

  @Output() closeModal = new EventEmitter<void>();
  @Output() succesfulTransaction = new EventEmitter<void>();
  close(): void {
    this.closeModal.emit();
  }
}
