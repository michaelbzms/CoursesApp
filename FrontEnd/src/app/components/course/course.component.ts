import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import * as $ from 'jquery';

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
    const grade = $('#course_' + this.course.id).val();
    console.log('changed grade: ' + grade);
    if (grade === null || grade === '') {
      // @ts-ignore
      delete this.course.grade;
    } else {
      // @ts-ignore
      this.course.grade = grade;
    }
    this.gradesChanged.emit();
  }

  blur() {
    // @ts-ignore
    $('#course_' + this.course.id).blur();
  }

}
