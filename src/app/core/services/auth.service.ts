import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(this.getUserFromToken());
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, { email, password })
      .pipe(
        tap(response => {
          if (response && response.token) {
            localStorage.setItem(environment.tokenKey, response.token);
            this.currentUserSubject.next(this.getUserFromToken());
          }
        })
      );
  }

  logout() {
    localStorage.removeItem(environment.tokenKey);
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem(environment.tokenKey);
    return token != null && !this.jwtHelper.isTokenExpired(token);
  }

  private getUserFromToken(): any {
    const token = localStorage.getItem(environment.tokenKey);
    if (token) {
      return this.jwtHelper.decodeToken(token);
    }
    return null;
  }
} 