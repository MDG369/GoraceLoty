import { Component, OnInit } from '@angular/core';
import { OfferService } from '../services/offer.service';
import { HotelService } from '../services/hotel.service';
import { Offer } from '../entity/Offer';
import { Hotel } from '../entity/Hotel';
import { Transport } from '../entity/Transport';

@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})

export interface OffersTableView {
  offer: Offer;
  transport: Transport;
  hotel: Hotel;
}

export class CatalogComponent implements OnInit {

  offers: Offer[] = [];

  hotels: Hotel[] = [];
  transports: Transport[] = [];


  // Żeby wszystko wyświetlało się w tabelce można zrobić też coś typu
  offersTableView: OffersTableView[] = [];


  constructor(private offerService: OfferService, private hotelService: HotelService) {
  }
  ngOnInit() {
    // Tutaj pobierasz przy pomocy serwisu ofert wszystkie oferty, potem przy pomocy hotel service wszystkie hotele z tych ofert i transporty
    // przykładowo
    this.offerService.getOffers().subscribe(data => {
      this.offers = data;
      this.hotelService.getHotels().subscribe(data => {
        this.hotels = data;
        this.transportService.getTransports().subscribe(data => {
          this.transports = data;
          // tutaj wypełnić offersTableView tymi ofertami hotelami transportami (dodać ofertę i odpowiadające jej pozycje z hoteli i transportów)
          // Te rzeczy są tak zagnieżdżone bo inaczej nie zadziała (asynchroniczność)

        })
      })
    })
    // Jest możliwe że da sie to jakoś sprytniej zrobić
  }

  searchForOffers() {
    // tutaj szukanie ofert przy uzyciu offerService
    //
  }
}
