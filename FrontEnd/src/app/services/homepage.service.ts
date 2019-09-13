import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomepageService {

  constructor(private http: HttpClient) { }

  registerStudent(email: string, firstName: string, lastName: string, password: string): Observable<any> {
    const httpParams = new HttpParams()
      .append('email', email)
      .append('password', password)
      .append('firstname', firstName)
      .append('lastname', lastName);
    return this.http.post(environment.apiUrl + '/students', httpParams);
  }

}
