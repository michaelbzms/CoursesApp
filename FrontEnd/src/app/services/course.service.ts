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
    let httpParams = new HttpParams();
    if (grade) { httpParams = httpParams.append('grade', String(grade)); }
    return this.http.post(environment.apiUrl + '/courses/' + courseId, httpParams, options);
  }

  deleteCourse(jwt: string, courseId: number): Observable<any> {
    const headers = new HttpHeaders({ jwt });
    const options = { headers };
    return this.http.delete(environment.apiUrl + '/courses/' + courseId, options);
  }

  editCourse(jwt: string, courseId: number, title: string, semester: number, ects: number, category: string, type: string)
    : Observable<any> {
    const headers = new HttpHeaders({ jwt });
    const options = { headers };
    const httpParams = new HttpParams()
      .append('title', title)
      .append('semester', String(semester))
      .append('ects', String(ects))
      .append('category', category)
      .append('type', type);
    return this.http.put(environment.apiUrl + '/courses/' + courseId, httpParams, options);
  }

}
