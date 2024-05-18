import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {AppComponent} from "./app.component";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import { CatalogComponent } from './catalog/catalog.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';


@NgModule({
  declarations: [AppComponent, CatalogComponent, LoginComponent, HomeComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
