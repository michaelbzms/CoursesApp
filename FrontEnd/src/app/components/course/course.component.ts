import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import * as $ from 'jquery';
import {CourseService} from '../../services/course.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: object;
  @Input() course: any;
  @Output() gradesChanged = new EventEmitter();

  constructor(private service: CourseService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

  getCategory() {
    // @ts-ignore
    switch (this.course.category) {
      case 'core':
        return 'Κορμός';
      case 'A':
        return 'Κατεύθυνση Α';
      case 'B':
        return 'Κατεύθυνση Β';
      case 'general_education':
        return 'Γενικής Παιδείας';
      case 'free':
        return 'Ελεύθερο';
      case 'optional_lab':
        return 'Προαιρετικό Εργαστήριο';
      default:
        return 'Αγνωστο';
    }
  }

  changeGrade() {
    const gradeElem = $('#course_' + this.course.id);
    const grade = gradeElem.val();
    if (grade < 0.0 || grade > 10.0) {
      gradeElem.val(this.course.hasOwnProperty('grade') ? this.course.grade : '');
      alert('Οι βαθμοί πρέπει να είναι μεταξύ 0 και 10.');
      return;
    }
    if ((grade === null || grade.replace(/\s/g, '') === '') && this.course.hasOwnProperty('grade')) {  // if it had now it hasn't a grade
      delete this.course.grade;
      if (this.jwt !== null) {    // if on session update backend
        this.service.updateGrade(this.jwt, this.course.id)
          .done(results => {
            if (results.hasOwnProperty('error')) {
              alert(results.message);
            }
        }).fail((jqXHR, textStatus, errorThrown) => {
          alert('>Error: ' + textStatus + ':' + errorThrown);
        });
      }
    } else if (grade !== null && grade.replace(/\s/g, '') !== '') {   // if the grade is not null
      this.course.grade = grade;
      if (this.jwt !== null) {    // if on session update backend
        this.service.updateGrade(this.jwt, this.course.id, grade)
          .done(results => {
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
    $('#course_' + this.course.id).blur();
  }

}
