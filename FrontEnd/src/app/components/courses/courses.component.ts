import {Component, Input, OnInit} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {environment} from '../../../environments/environment';
import * as $ from 'jquery';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: object;
  courses: object[];
  avgGrade: number;
  totalEcts: number;

  constructor() { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    this.getCourses();
  }

  getCourses() {
    $.ajax({
      // @ts-ignore
      url: environment.apiUrl + '/courses',
      method: 'GET',
      dataType: 'json',
      headers: (this.jwt !== null) ? { jwt: this.jwt } : {},
      data: {}
    }).done(results => {
      console.log(results);
      if (results.hasOwnProperty('error')) {
        alert(results.message);
      } else {
        this.courses = results.courses;
        this.calculateAVGandECTS();
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert(textStatus + ':' + errorThrown);
    });
  }

  calculateAVGandECTS() {
    // tslint:disable-next-line:variable-name
    let total_ects = 0;
    // tslint:disable-next-line:variable-name
    let sum_coef_grades = 0.0;
    this.courses.forEach((course) => {
      // @ts-ignore
      if (course.hasOwnProperty('grade') && course.grade >= 5) {
        // @ts-ignore
        total_ects += course.ects;
        // @ts-ignore
        sum_coef_grades += course.ects * course.grade;
      }
    });
    this.totalEcts = total_ects;
    this.avgGrade = (total_ects > 0) ? sum_coef_grades / total_ects : 0.0;
  }

}
