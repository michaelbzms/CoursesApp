import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {CoursesService} from '../../services/courses.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: object;
  courses: any[];
  avgGrade: number;
  totalEcts: number;

  // filter inputs:
  semesterFilter = 0;
  specificationFilter = 0;
  coreFilter = false;
  labFilter = false;
  AFilter = false;
  BFilter = false;
  GEFilter = false;
  freeFilter = false;
  obligatory = false;
  obligatoryByChoice = false;
  basic = false;
  optional = false;

  static roundUp(num, precision) {
    precision = Math.pow(10, precision);
    return Math.ceil(num * precision) / precision;
  }

  constructor(private service: CoursesService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    this.getCourses();
  }

  // tslint:disable-next-line:use-life-cycle-interface
  ngOnChanges(changes: SimpleChanges) {
    if (this.jwt !== null) {   // if logged in
      this.getCourses();
    }
  }

  getCourses() {
    this.service.getCourses(this.jwt)
    .done(results => {
      if (results.hasOwnProperty('error')) {
        alert(results.message);
      } else {
        this.courses = results.courses;
        this.calculateAVGandECTS();
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert('>Error: ' + jqXHR.status + ':' + textStatus + ' ' + errorThrown);
    });
  }

  calculateAVGandECTS() {
    // tslint:disable-next-line:variable-name
    let total_ects = 0;
    // tslint:disable-next-line:variable-name
    let sum_coef_grades = 0.0;
    this.courses.forEach((course) => {
      if (course.hasOwnProperty('grade') && course.grade >= 5) {
        total_ects += course.ects;
        sum_coef_grades += course.ects * course.grade;
      }
    });
    this.totalEcts = total_ects;
    this.avgGrade = (total_ects > 0) ? sum_coef_grades / total_ects : 0.0;
    this.avgGrade = CoursesComponent.roundUp(this.avgGrade, 2);
  }

  getClassNameForAVG(): string {
    if (this.avgGrade < 5.0) {
      return 'bad_grade';
    } else if (this.avgGrade < 6.0) {
      return 'bad_grade';
    } else if (this.avgGrade < 7.0) {
      return 'okay_grade';
    } else if (this.avgGrade < 8.0) {
      return 'good_grade';
    } else if (this.avgGrade < 9.0) {
      return 'very_good_grade';
    } else {
      return 'perfect_grade';
    }
  }

}
