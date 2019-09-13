import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any> {
    const httpParams = new HttpParams()
      .append('email', email)
      .append('password', password);
    return this.http.post(environment.apiUrl + '/login', httpParams);
  }
}
