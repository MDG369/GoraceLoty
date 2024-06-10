import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TransportService } from '../services/transport.service';
import { OfferService } from '../services/offer.service';
import { Transport } from '../entity/Transport';
import {HotelService} from "../services/hotel.service";
import {Hotel} from "../entity/Hotel"; // Assuming you have an Offer entity
import { MessageService } from 'primeng/api';
import {Subscription} from "rxjs";
import {ChangesMessage} from "../entity/ChangesMessage";
import {ChangesService} from "../services/changes.service";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-offer-details',
  templateUrl: './offer.details.component.html'
})
export class OfferDetailsComponent implements OnInit {
  offerId: number = 0;
  transport: Transport = new Transport();
  hotel: Hotel = new Hotel(1, "", 1, "" ,"" ,true ,"" );
  isModalOpen: boolean = false;
  private changesSubscription: Subscription;


  constructor(
    private route: ActivatedRoute,
    private transportService: TransportService,
    private hotelService: HotelService,
    private offerService: OfferService,
    private messageService: MessageService,
    private changesService: ChangesService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        console.log("Offer ID:", id);
        this.offerId = Number(id);
        this.loadOfferDetails(this.offerId);
      } else {
        console.error('No ID provided in route parameters');
        // Handle redirection or display error message
      }
    });
    this.changesSubscription = this.changesService.getChangesObservable().subscribe(
      (change: ChangesMessage) =>
    {
      this.checkIfCurrentOfferBooked(change);
    });
  }

  loadOfferDetails(offerId: number): void {
    this.offerService.getOfferById(offerId).subscribe({
      next: offer => {
        console.log("Loaded offer details:", offer);
        if (offer && offer.transportID) {
           this.loadMatchingTransport(offer.transportID);
        }
        if(offer && offer.hotelID){
           this.loadMatchingHotel(offer.hotelID);
        }
      },
      error: error => {
        console.error('Error loading offer details', error);
        // Handle error, e.g., show user notification or redirect
      }
    });
  }

  loadMatchingTransport(transportId: number): void {
    this.transportService.getMatchingTransport(transportId).subscribe({
      next: transport => {
        this.transport = transport;
      },
      error: error => {
        console.error('Error loading transport', error);
        // Handle error, potentially setting transport to null or providing user feedback
      }
    });
  }
  loadMatchingHotel(hotelId: number): void {
    this.hotelService.getMatchingHotel(hotelId).subscribe({
      next: hotel => {
        console.log("Transport details loaded:", hotel);
        this.hotel = hotel;
      },
      error: error => {
        console.error('Error loading transport', error);
        // Handle error, potentially setting transport to null or providing user feedback
      }
    });
  }
  openBookingModal(): void {
    this.isModalOpen = true;  // Toggle modal visibility
  }

  closeBookingModal(): void {
    this.isModalOpen = false;  // You can call this method to close the modal
  }
  showSuccessfulMessage() {
    this.messageService.add({
      severity: 'success', summary: 'Success', detail: 'Oferta zarezerwowana, przejdź do zakładki rezerwacje aby ją opłacić'
    })
  }

  checkIfCurrentOfferBooked(change) {
    if (change.offerId == this.offerId && change.clientId != this.authService.userId) {
      this.messageService.add({
        severity: 'secondary', summary: 'Uwaga', detail: 'Obecna oferta zarezerwowana przez innego użytkownika!'
      })
    }
  }

}
