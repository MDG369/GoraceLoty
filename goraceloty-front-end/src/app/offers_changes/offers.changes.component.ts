import { Component, OnInit } from '@angular/core';
import { OfferChangeService } from '../services/offer.changes.service';
import { OfferChange } from '../entity/OfferChange';

@Component({
  selector: 'app-offer-changes',
  templateUrl: './offers.changes.component.html',
  styleUrls: ['./offers.changes.component.css']
})
export class OfferChangesComponent implements OnInit {
  offerChanges: OfferChange[] = [];

  constructor(private offerChangeService: OfferChangeService) {}

  ngOnInit(): void {
    this.loadOfferChanges();
  }

  loadOfferChanges(): void {
    this.offerChangeService.getOfferChanges().subscribe({
      next: (changes) => {
        this.offerChanges = changes;
      },
      error: (error) => {
        console.error('Failed to load offer changes', error);
      }
    });
  }
}
