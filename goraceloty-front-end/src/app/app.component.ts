import { Component } from '@angular/core';
import {HotelService} from "./services/hotel.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'goraceloty-front-end';
  hotelsString: string = 'none'
  constructor(private hotelService: HotelService) {
  }
  // setHotelsString(){
  //   this.hotelService.getHotels().subscribe(data => {this.hotelsString = data; console.log(data)});
  // }
}
