import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {CourseService} from '../../services/course.service';
import {Toasts} from '../../utils/Toasts';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  @Input() private jwt: string;
  @Input() private user: object;
  @Input() private course: any;
  @Output() private gradesChanged = new EventEmitter();
  @ViewChild('gradeInputTag') gradeInputSelector: ElementRef;
  private clickedGrade = false;

  constructor(private service: CourseService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

  clickedGradeHolder() {
    this.clickedGrade = true;
    setTimeout(() => {
      // this hack allows it to be done after content is no longer hidden
      this.gradeInputSelector.nativeElement.focus();
    }, 0);
  }

  changeGrade() {
    this.clickedGrade = false;
    const gradeElem = document.getElementById('course_' + this.course.id) as HTMLInputElement;
    const gradeStr = gradeElem.value;
    const grade = parseFloat(gradeElem.value);
    if (gradeStr !== null && gradeStr !== '' && (isNaN(grade) || grade < 0.0 || grade > 10.0)) {
      gradeElem.value = this.course.hasOwnProperty('grade') ? this.course.grade : '';
      Toasts.toast('Οι βαθμοί πρέπει να είναι μεταξύ 0 και 10');
      return;
    }
    if ((grade === null || gradeStr.replace(/\s/g, '') === '')
         && this.course.hasOwnProperty('grade')) {                                   // if it had now it hasn't a grade
      delete this.course.grade;
      if (this.jwt !== null) {    // if logged in update backend
        this.service.updateGrade(this.jwt, this.course.id)
            .subscribe(results => {
              if (results.hasOwnProperty('error')) {
                alert('Backend Error: ' + results.message);
              }
            }, error => {
              alert('HTTP Error:' + error);
            });
      }
      this.gradesChanged.emit();
    } else if (grade !== null && gradeStr.replace(/\s/g, '') !== ''        // if the grade is not null
               && (!this.course.hasOwnProperty('grade') || this.course.grade !== grade)) {   // and it's not the same grade it had before
      this.course.grade = grade;
      if (this.jwt !== null) {    // if logged in update backend
        this.service.updateGrade(this.jwt, this.course.id, grade)
            .subscribe(results => {
              if (results.hasOwnProperty('error')) {
                alert('Backend Error: ' + results.message);
              }
            }, error => {
              alert('HTTP Error:' + error);
            });
      }
      this.gradesChanged.emit();
    }
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
      return 'below_okay_grade';
    } else if (this.course.grade < 8.0) {
      return 'okay_grade';
    } else if (this.course.grade < 9.0) {
      return 'good_grade';
    } else if (this.course.grade < 10.0) {
      return 'very_good_grade';
    } else {
      return 'perfect_grade';
    }
  }

  getBorderClassForGrade() {
    if (!this.course.hasOwnProperty('grade')) {
      return '';
    } else if (this.course.grade < 5.0) {
      return 'failed_course';
    } else if (this.course.grade < 6.0) {
      return 'passed_course_bad';
    } else if (this.course.grade < 7.0) {
      return 'passed_course_below_okay';
    } else if (this.course.grade < 8.0) {
      return 'passed_course_okay';
    } else if (this.course.grade < 9.0) {
      return 'passed_course_good';
    } else if (this.course.grade < 10.0) {
      return 'passed_course_very_good';
    } else {
      return 'passed_course_perfect';
    }
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

  getType() {
    switch (this.course.type) {
      case 'obligatory':
        return 'Υποχρεωτικό';
      case 'obligatory-by-choice':
        return 'Κατά Επιλογήν Υποχρεωτικό';
      case 'optional':
        return 'Προαιρετικό';
      case 'basic':
        return 'Βασικό';
      default:
        return 'Αγνωστο';
    }
  }

}
