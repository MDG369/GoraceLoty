import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AppComponent} from "./app.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {OffersComponent} from "./offers/offers.component";
import {OfferDetailsComponent} from './offers_details/offer.details.component';
import {FormsModule} from '@angular/forms';
import {TransportDetailsComponent} from "./transport/transport.component";
import {BookingModalComponent} from "./booking-modal/booking-modal.component";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";

import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {ReservationTabComponent} from './reservation-tab/reservation-tab.component';
import {RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {JwtInterceptor, JwtModule} from "@auth0/angular-jwt";
import {AuthGuard} from "./guards/AuthGuard";
import {AuthService} from "./services/auth.service";
import {LoginComponent} from "./login/login.component";

@NgModule({
  declarations: [AppComponent,
    OffersComponent,
    OfferDetailsComponent,
    TransportDetailsComponent,
    ReservationTabComponent,
    LoginComponent
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
    MessagesModule,
    BrowserAnimationsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('token'); // Provide the function to retrieve the JWT token
        }
      }
    })
  ],
  bootstrap: [AppComponent],
  providers: [MessageService,
    AuthService,
    AuthGuard,
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ],

})
export class AppModule {
}
