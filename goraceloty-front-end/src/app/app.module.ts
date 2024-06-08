import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AppComponent} from "./app.component";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {OffersComponent} from "./offers/offers.component";
import { OfferDetailsComponent } from './offers_details/offer.details.component';
import { FormsModule } from '@angular/forms';
import {TransportDetailsComponent} from "./transport/transport.component";
import {BookingModalComponent} from "./booking-modal/booking-modal.component";
import {HotelDetailsComponent} from "./hotel/hotel.component";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";

import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import { ReservationTabComponent } from './reservation-tab/reservation-tab.component';
import { RxStompService, rxStompServiceFactory } from '@stomp/ng2-stompjs';
@NgModule({
  declarations: [AppComponent,
    OffersComponent,
    OfferDetailsComponent,
    TransportDetailsComponent,
    HotelDetailsComponent,
    ReservationTabComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    BrowserModule,
    BookingModalComponent,
    ToastModule,
    MessageModule,
    MessagesModule
  ],
  bootstrap: [AppComponent],
  providers: [MessageService,
    {
    provide: RxStompService,
    useFactory: rxStompServiceFactory
  }
  ],

})
export class AppModule { }
