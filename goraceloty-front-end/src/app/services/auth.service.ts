import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}
  public login(username: string, password: string): Observable<string> {
    let headers = new HttpHeaders().append('Authorization', 'Basic ' + btoa(username + ":" + password));
    const httpOptions = {
      headers: headers
    };
    return this.http.get<string>(
      'http://localhost:8080' + '/login',
      httpOptions
    );
  }
}
