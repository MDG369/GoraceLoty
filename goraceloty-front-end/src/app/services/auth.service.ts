import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api';
  userId: number = 0
  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<{ token:string, userId: number }>(`${this.apiUrl}/login`, { username, password }, { headers })
      .pipe(
        map(response => {
          if (response && response.token) {
            sessionStorage.setItem('token', response.token);
            this.userId = response.userId;
            return true;
          }
          return false;
        })
      );
  }

  logout(): void {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem('token');
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }
}

