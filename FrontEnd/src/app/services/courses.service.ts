import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
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

  submitNewCourse(title: string, semester: number, ects: number, category: string, type: string, jwt: string): Observable<any> {
    const httpParams = new HttpParams()
                      .append('title', title)
                      .append('semester', String(semester))
                      .append('ects', String(ects))
                      .append('category', category)
                      .append('category', type);
    const headers = new HttpHeaders({jwt});
    const options = {headers};
    return this.http.post(environment.apiUrl + '/courses', httpParams, options);
  }

}
