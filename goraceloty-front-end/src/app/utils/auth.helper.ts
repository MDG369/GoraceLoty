import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthHelper {
  private tokenKey = 'token';

  constructor(
    private authenticationClient: AuthService,
    private router: Router
  ) {
  }

  public login(username: string, password: string): void {
    this.authenticationClient.login(username, password).subscribe((token) => {
      localStorage.setItem(this.tokenKey, token);
      this.router.navigate(['/']);
    });
  }

  public logout() {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }

  public isLoggedIn(): boolean {
    let token = localStorage.getItem(this.tokenKey);
    return token!=null && token.length > 0;
  }

  public getToken(): string | null {
    return this.isLoggedIn() ? localStorage.getItem(this.tokenKey):null;
  }

}
