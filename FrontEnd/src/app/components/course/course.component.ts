import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import * as $ from 'jquery';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: object;
  @Input() course: object;
  @Output() gradesChanged = new EventEmitter();

  constructor() { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

  getPath(path: string) {
    switch (path) {
      case 'core':
        return 'Κορμός';
      case 'A':
        return 'Κατεύθυνση Α';
      case 'B':
        return 'Κατεύθυνση Β';
      default:
        return 'Αγνωστο';
    }
  }

  changeGrade() {
    // @ts-ignore
    const gradeElem = $('#course_' + this.course.id);
    const grade = gradeElem.val();
    if (grade < 0.0 || grade > 10.0) {
      // @ts-ignore
      gradeElem.val(this.course.hasOwnProperty('grade') ? this.course.grade : '');
      alert('Οι βαθμοί πρέπει να είναι μεταξύ 0 και 10.');
      return;
    }
    if ((grade === null || grade.replace(/\s/g, '') === '') && this.course.hasOwnProperty('grade')) {  // if it had now it hasn't a grade
      // @ts-ignore
      delete this.course.grade;
      if (this.jwt !== null) {    // if on session update backend
        $.ajax({
          // @ts-ignore
          url: environment.apiUrl + '/courses/' + this.course.id,
          method: 'POST',
          dataType: 'json',
          headers: { jwt: this.jwt },
          data: { }   // empty data means reset grade
        }).done(results => {
          if (results.hasOwnProperty('error')) {
            alert(results.message);
          }
        }).fail((jqXHR, textStatus, errorThrown) => {
          alert('>Error: ' + textStatus + ':' + errorThrown);
        });
      }
    } else if (grade !== null && grade.replace(/\s/g, '') !== '') {   // if the grade is not null
      // @ts-ignore
      this.course.grade = grade;
      if (this.jwt !== null) {    // if on session update backend
        $.ajax({
          // @ts-ignore
          url: environment.apiUrl + '/courses/' + this.course.id,
          method: 'POST',
          dataType: 'json',
          headers: {jwt: this.jwt},
          data: {grade}
        }).done(results => {
          if (results.hasOwnProperty('error')) {
            alert(results.message);
          }
        }).fail((jqXHR, textStatus, errorThrown) => {
          alert('>Error: ' + textStatus + ':' + errorThrown);
        });
      }
    }
    this.gradesChanged.emit();
  }

  blur() {
    // @ts-ignore
    $('#course_' + this.course.id).blur();
  }

}
