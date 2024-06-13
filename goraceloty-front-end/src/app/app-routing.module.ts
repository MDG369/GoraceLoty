import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";
import { OffersComponent } from './offers/offers.component';
import { OfferDetailsComponent } from './offers_details/offer.details.component';
import { ReservationTabComponent } from './reservation-tab/reservation-tab.component';
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./guards/AuthGuard";
import {OfferChangesComponent} from "./offers_changes/offers.changes.component";

export const routes: Routes = [
  { path: 'offers', component: OffersComponent, canActivate: [AuthGuard] },  // Path to access OffersComponent
  { path: '', redirectTo: '/offers', pathMatch: 'full' }, // Redirect to /offers by default
  { path: 'offer-details/:id', component: OfferDetailsComponent, canActivate: [AuthGuard]  },
  { path: 'reservations', component: ReservationTabComponent, canActivate: [AuthGuard] },
  { path: 'changes', component: OfferChangesComponent,canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule{

}
