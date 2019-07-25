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
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert(textStatus + ':' + errorThrown);
    });
  }

}
