import { Injectable } from '@angular/core';
import * as $ from 'jquery';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomepageService {

  constructor() { }

  registerStudent(email: string, firstName: string, lastName: string, password: string) {
    return $.ajax({
      url: environment.apiUrl + '/students',
      method: 'POST',
      dataType: 'json',
      headers: {},
      data: {
        email,
        password,
        firstname: firstName,
        lastname: lastName
      }
    });
  }

}
