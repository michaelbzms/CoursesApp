import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import * as $ from 'jquery';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor() { }

  updateGrade(jwt: string, courseId: number, grade?: number) {
    return $.ajax({
      url: environment.apiUrl + '/courses/' + courseId,
      method: 'POST',
      dataType: 'json',
      headers: { jwt },
      data: (grade) ? { grade } : { }
    });
  }

}
