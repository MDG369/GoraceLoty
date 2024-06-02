// offers.component.ts
import { Component, OnInit } from '@angular/core';
import { OfferService} from '../services/offer.service';
import { Offer } from '../entity/Offer';
import {filter} from "rxjs";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {
  offers: Offer[] = [];
  filteredOffers: Offer[] = [];
  cityArrival?: string;      // Holds the value of city input
  dateStart?: string; // Holds the value of date start input
  dateEnd?: string;   // Holds the value of date end input

  constructor(private offerService: OfferService) {}

  ngOnInit(): void {
    this.loadOffers();
  }
  loadOffers(): void {
    if (this.cityArrival || this.dateStart || this.dateEnd) {  // Only load if any criteria are set
      this.offerService.getOffers(this.cityArrival, this.dateStart, this.dateEnd).subscribe({
        next: (offers) => this.offers = offers,
        error: (error) => console.error('Failed to load offers', error)
      });
    } else {
      this.offers = []; // Clear offers or set to default if no criteria are set
    }
  }
}
