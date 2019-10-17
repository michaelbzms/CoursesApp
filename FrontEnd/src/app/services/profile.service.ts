import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  updateStudent(jwt: string, userId: number, updateData: {firstname, lastname, email?}): Observable<any> {
    const headers = new HttpHeaders({jwt});
    const options = {headers};
    let httpParams = new HttpParams()
      .append('firstname', updateData.firstname)
      .append('lastname', updateData.lastname);
    if (updateData.email) { httpParams = httpParams.append('email', updateData.email); }
    return this.http.put(environment.apiUrl + '/students/' + userId, httpParams, options);
  }

  updatePassword(jwt: string, userId: number, oldPassword: string, newPassword: string): Observable<any> {
    const headers = new HttpHeaders({jwt});
    const options = {headers};
    const httpParams = new HttpParams()
      .append('oldpassword', oldPassword)
      .append('newpassword', newPassword);
    return this.http.put(environment.apiUrl + '/users/' + userId, httpParams, options);
  }

  deleteStudent(jwt: string, userId: number): Observable<any> {
    const headers = new HttpHeaders({jwt});
    const options = {headers};
    return this.http.delete(environment.apiUrl + '/students/' + userId, options);
  }

}
