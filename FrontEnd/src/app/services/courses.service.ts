import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private http: HttpClient) { }

  getCourses(jwt?: string): Observable<any> {
    if (jwt) {
      const headers = new HttpHeaders({jwt});
      const options = {headers};
      return this.http.get(environment.apiUrl + '/courses', options);
    } else {
      return this.http.get(environment.apiUrl + '/courses');
    }
  }

}
