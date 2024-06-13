import { Component, OnInit } from '@angular/core';
import { OfferChangeService } from '../services/offer.changes.service';
import { OfferChange } from '../entity/OfferChange';
import {ChangesMessage} from "../entity/ChangesMessage";
import {ChangesService} from "../services/changes.service";
import {OfferReservation} from "../entity/OfferReservation";

@Component({
  selector: 'app-offer-changes',
  templateUrl: './offers.changes.component.html',
  styleUrls: ['./offers.changes.component.css']
})
export class OfferChangesComponent implements OnInit {
  offerChanges: OfferChange[] = [];
  private changesSubscription: any;

  constructor(private offerChangeService: OfferChangeService, private changesService: ChangesService) {}

  ngOnInit(): void {
    this.changesSubscription = this.changesService.getChangesObservable().subscribe((changes: ChangesMessage) => {
      this.loadOfferChanges();
    });
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
