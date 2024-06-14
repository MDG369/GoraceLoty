// offers.component.ts
import { Component, OnInit } from '@angular/core';
import { OfferService} from '../services/offer.service';
import { Offer } from '../entity/Offer';
import { Router } from '@angular/router';
import { Subscription } from "rxjs";
import {ChangesService} from "../services/changes.service";
import {ChangesMessage} from "../entity/ChangesMessage";
import {TravelAgencyService} from "../services/travel.agency.service";
import {OfferReservation} from "../entity/OfferReservation";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {
  offers: Offer[] = [];
  filteredOffers: Offer[] = [];
  cityArrival: string;      // Holds the value of city input
  dateStart?: string; // Holds the value of date start input
  dateEnd?: string;   // Holds the value of date end input
  private changesSubscription: Subscription;
  changes: ChangesMessage;
  hotelPopularity:{ [p: number]: number } = [];
  transportPopularity:{ [p: number]: number } = [];

  constructor(private offerService: OfferService,
              private router: Router,
              private changesService: ChangesService,
              private travelAgencyService: TravelAgencyService) {}

  ngOnInit(): void {
    this.loadOffers();
    this.changesSubscription = this.changesService.getChangesObservable().subscribe((changes: ChangesMessage) => {
      this.checkOfferPopularity();
    });
    this.checkOfferPopularity();
  }
  loadOffers(): void {
      this.offerService.getOffers(this.cityArrival, this.dateStart, this.dateEnd).subscribe({
        next: (offers) => {this.offers = offers;     this.checkOfferPopularity();
        },
        error: (error) => console.error('Failed to load offers', error)
      });
  }
  navigateToDetails(offerId: number): void {
    this.router.navigate(['/offer-details', offerId]);
  }

  checkOfferPopularity() {
    // setting offers popularity based on number of entries in the database
    let res: OfferReservation[] = [];
    this.travelAgencyService.getAllReservations().subscribe(data => {
      res = data;
      if (this.offers != null) {
        this.offers.forEach(offer => {
          offer.popularity = res.filter(res => res.offerID == offer.id).length
        });
      }
      this.hotelPopularity = OfferReservation.countHotelOccurrences(res);
      this.transportPopularity = OfferReservation.countTransportOccurrences(res);
    });
  }


}
