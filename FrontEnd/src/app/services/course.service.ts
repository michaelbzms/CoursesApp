import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) { }

  updateGrade(jwt: string, courseId: number, grade?: number): Observable<any> {
    const headers = new HttpHeaders({ jwt });
    const options = { headers };
    const httpParams = new HttpParams();
    if (grade) { httpParams.append('grade', String(grade)); }
    return this.http.post(environment.apiUrl + '/courses/' + courseId, httpParams, options);
  }

}
