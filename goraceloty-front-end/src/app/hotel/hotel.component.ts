import { Component, OnInit } from '@angular/core';
import { HotelService } from '../services/hotel.service';
import { Hotel } from '../entity/Hotel';

@Component({
  selector: 'app-transport-details',
  templateUrl: './hotel.component.html'
})
export class HotelDetailsComponent implements OnInit {
  hotels: Hotel[] = [];

  constructor(private transportService: HotelService) {}

  ngOnInit(): void {
    this.loadTransports();
  }

  loadTransports(): void {
    this.transportService.getHotels().subscribe({
      next: (data) => {
        this.hotels= data;
        console.log('Failed to load transports');
      },
      error: (error) => {
        console.error('Failed to load transports', error);
      }
    });
  }
}
