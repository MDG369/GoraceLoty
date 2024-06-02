import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";
import { OffersComponent } from './offers/offers.component';

export const routes: Routes = [
  { path: 'offers', component: OffersComponent },  // Path to access OffersComponent
  { path: '', redirectTo: '/offers', pathMatch: 'full' }  // Redirect to /offers by default
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule{

}
