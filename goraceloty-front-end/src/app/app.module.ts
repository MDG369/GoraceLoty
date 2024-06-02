import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AppComponent} from "./app.component";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {OffersComponent} from "./offers/offers.component";
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [AppComponent, OffersComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    BrowserModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
