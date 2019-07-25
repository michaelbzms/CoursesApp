import {Component, Input, OnInit} from '@angular/core';
import * as $ from 'jquery';
import {NavbarComponent} from '../navbar/navbar.component';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: object;
  @Input() course: object;

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

}
