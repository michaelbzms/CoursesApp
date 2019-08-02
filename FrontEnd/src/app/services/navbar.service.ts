import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import * as $ from 'jquery';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {

  constructor() { }

  login(email: string, password: string) {
    return $.ajax({
      url: environment.apiUrl + '/login',
      method: 'POST',
      dataType: 'json',
      headers: {},
      data: {
        email,
        password
      }
    });

  }
}
