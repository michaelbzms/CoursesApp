import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {CourseService} from '../../services/course.service';
import {Toasts} from '../../utils/Toasts';

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
    const gradeElem = document.getElementById('course_' + this.course.id) as HTMLInputElement;
    const gradeStr = gradeElem.value;
    const grade = parseFloat(gradeElem.value);
    if (isNaN(grade) || grade < 0.0 || grade > 10.0) {
      gradeElem.value = this.course.hasOwnProperty('grade') ? this.course.grade : '';
      Toasts.toast('Οι βαθμοί πρέπει να είναι μεταξύ 0 και 10');
      return;
    }
    if ((grade === null || gradeStr.replace(/\s/g, '') === '') && this.course.hasOwnProperty('grade')) {  // if it had now it hasn't a grade
      delete this.course.grade;
      if (this.jwt !== null) {    // if on session update backend
        this.service.updateGrade(this.jwt, this.course.id)
            .subscribe(results => {
              if (results.hasOwnProperty('error')) {
                alert('Backend Error: ' + results.message);
              }
            }, error => {
              alert('HTTP Error:' + error);
            });
      }
    } else if (grade !== null && gradeStr.replace(/\s/g, '') !== '') {   // if the grade is not null
      this.course.grade = grade;
      if (this.jwt !== null) {    // if on session update backend
        this.service.updateGrade(this.jwt, this.course.id, grade)
            .subscribe(results => {
              if (results.hasOwnProperty('error')) {
                alert('Backend Error: ' + results.message);
              }
            }, error => {
              alert('HTTP Error:' + error);
            });
      }
    }
    this.gradesChanged.emit();
  }

  blur() {
    document.getElementById('course_' + this.course.id).blur();
  }

  getClassForGrade() {
    if (!this.course.hasOwnProperty('grade')) {
      return '';
    } else if (this.course.grade < 5.0) {
      return 'failing_grade';
    } else if (this.course.grade < 6.0) {
      return 'bad_grade';
    } else if (this.course.grade < 7.0) {
      return 'okay_grade';
    } else if (this.course.grade < 8.0) {
      return 'good_grade';
    } else if (this.course.grade < 9.0) {
      return 'very_good_grade';
    } else {
      return 'perfect_grade';
    }
  }

}
