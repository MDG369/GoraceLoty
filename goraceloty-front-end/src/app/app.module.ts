import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AppComponent} from "./app.component";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {TransportComponent } from './components/transport/transport.component';
import {TransportService } from './services/transport.service';


@NgModule({
  declarations: [AppComponent,
    TransportComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserModule
  ],
  providers: [TransportService],
  bootstrap: [AppComponent]
})
export class AppModule { }
