import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";
import { CatalogComponent } from './catalog/catalog.component';
import {AuthGuard} from './guards/AuthGuard'
import { LoginComponent } from './login/login.component';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
  {
    path: '', component: HomeComponent
  },
  { path: 'login', component: LoginComponent, pathMatch: 'full'},
  {path: 'catalog', component: CatalogComponent},

];
@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule{

}
