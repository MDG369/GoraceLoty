import { Component, OnInit } from '@angular/core';
import { OfferService} from '../services/offer.service';
import { Offer } from '../entity/Offer';
import { Router } from '@angular/router';
import {filter, Observable} from "rxjs";
import {OfferChange} from "../entity/OfferChange";

@Component({
  selector: 'app-offers',
  templateUrl: './offers.changes.component.html',
  standalone: true,
  styleUrls: ['./offers.changes.component.css']
})
export class OffersChangesComponent implements OnInit {
  // @ts-ignore
  private offerChanges: Observable<OfferChange[]>;

  constructor(private offerChangeService: OfferService) {}

  ngOnInit() {
    this.offerChanges = this.offerChangeService.getOfferChanges();
  }
}
