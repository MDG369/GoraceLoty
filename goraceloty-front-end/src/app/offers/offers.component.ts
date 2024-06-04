// offers.component.ts
import { Component, OnInit } from '@angular/core';
import { OfferService} from '../services/offer.service';
import { Offer } from '../entity/Offer';
import { Router } from '@angular/router';
import {filter} from "rxjs";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {
  offers: Offer[] = [];
  filteredOffers: Offer[] = [];
  cityArrival: string = 'Sal';      // Holds the value of city input
  dateStart?: string; // Holds the value of date start input
  dateEnd?: string;   // Holds the value of date end input

  constructor(private offerService: OfferService,
              private router: Router) {}

  ngOnInit(): void {
    this.loadOffers();
  }
  loadOffers(): void {

      this.offerService.getOffers(this.cityArrival, this.dateStart, this.dateEnd).subscribe({
        next: (offers) => this.offers = offers,
        error: (error) => console.error('Failed to load offers', error)
      });
  }
  navigateToDetails(offerId: number): void {
    this.router.navigate(['/offer-details', offerId]);
  }
}
