// import { Component, OnInit } from '@angular/core';
// import { FormControl, FormGroup,Validators } from '@angular/forms';
// import { OfferService } from '../services/offer.service';
// import { HotelService } from '../services/hotel.service';
// import { TransportService } from '../services/transport.service';
// import { Offer } from '../entity/Offer';
// import { Hotel } from '../entity/Hotel';
// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { Transport } from '../entity/Transport';
// import { combineLatest } from 'rxjs';
// import { map, switchMap } from 'rxjs/operators';
//
// @Component({
//   selector: 'app-catalog',
//   templateUrl: './catalog.component.html',
//   styleUrl: './catalog.component.css'
// })
//
// export class CatalogComponent implements OnInit {
//   offers: Offer[] = [];
//   searchForm: FormGroup | undefined;
//   constructor(
//     private offerService: OfferService,
//   ) {
//   }
//
//   ngOnInit(): void {
//     this.initForm();
//   }
//
//   private initForm(): void {
//     this.searchForm = new FormGroup({
//       city: new FormControl('Zakynthos (ZTH)'),
//       dateStart: new FormControl('2024-05-29'),
//       dateEnd: new FormControl('2024-06-05'),
//       numOfPeople: new FormControl(2)
//     });
//   }
//
//   // onSubmit(): void {
//   //   const value = this.searchForm.value;
//   //   const { city, dateStart, dateEnd, numOfPeople } = value;
//   //   this.offerService.getOffers(city, dateStart, dateEnd, numOfPeople).subscribe(offers => {
//   //     this.offers = offers;
//   //   });
//   //}
// }
