import { Injectable } from '@angular/core';
import * as $ from 'jquery';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor() { }

  getCourses(jwt?: string) {
    return $.ajax({
      url: environment.apiUrl + '/courses',
      method: 'GET',
      dataType: 'json',
      headers: (jwt) ? { jwt } : {},
      data: {}
    });
  }

}
