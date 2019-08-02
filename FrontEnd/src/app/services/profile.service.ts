import { Injectable } from '@angular/core';
import * as $ from 'jquery';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor() { }

  updateStudent(jwt: string, userId: number, updateData: any) {
    return $.ajax({
      url: environment.apiUrl + '/students/' + userId,
      method: 'PUT',
      dataType: 'json',
      headers: { jwt },
      data: updateData
    });
  }

  updatePassword(jwt: string, userId: number, oldPassword: string, newPassword: string) {
    return $.ajax({
      url: environment.apiUrl + '/users/' + userId,
      method: 'PUT',
      dataType: 'json',
      headers: { jwt },
      data: {
        oldpassword: oldPassword,
        newpassword: newPassword
      }
    });
  }

  deleteStudent(jwt: string, userId: number) {
    return $.ajax({
      url: environment.apiUrl + '/students/' + userId,
      method: 'DELETE',
      dataType: 'json',
      headers: { jwt },
      data: {}
    });
  }

}
