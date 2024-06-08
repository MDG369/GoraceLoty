// offers.component.ts
import { Component, OnInit } from '@angular/core';
import { OfferService} from '../services/offer.service';
import { Offer } from '../entity/Offer';
import { Router } from '@angular/router';
import { Subscription } from "rxjs";
import {ChangesService} from "../services/changes.service";
import {ChangesMessage} from "../entity/ChangesMessage";

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
  private changesSubscription: Subscription;
  changes: ChangesMessage;

  constructor(private offerService: OfferService,
              private router: Router,
              private changesService: ChangesService) {}

  ngOnInit(): void {
    this.loadOffers();
    this.changesSubscription = this.changesService.getChangesObservable().subscribe((changes: ChangesMessage[]) => {
      this.checkOfferPopularity();
      console.log('Changes array updated:', this.changes);
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
    this.offers.forEach(offer => {
      console.log(offer);
      offer.popularity = this.changesService.getNumberOfChangesForOfferId(offer.id);
    })
  }


}
