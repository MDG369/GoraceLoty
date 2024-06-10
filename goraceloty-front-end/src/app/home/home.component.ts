import { Component } from '@angular/core';
import { HotelService } from '../services/hotel.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  title = 'goraceloty-front-end';
  hotelsString: string = 'none'
  constructor(private hotelService: HotelService) {
  }

}
