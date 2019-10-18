import {Component, OnDestroy, OnInit} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {CoursesService} from '../../services/courses.service';
import {take} from 'rxjs/operators';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-coursesmanagement',
  templateUrl: './coursesmanagement.component.html',
  styleUrls: ['./coursesmanagement.component.css']
})
export class CoursesmanagementComponent implements OnInit, OnDestroy {

  private user: any;
  private jwt: string;
  private courses: any[];
  private newCourseForm: FormGroup = null;   // Reactive form

  private logInOrOutSubscription;

  constructor(private service: CoursesService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    this.logInOrOutSubscription = NavbarComponent.logInOrOutEvent.subscribe((val) => {
      this.jwt = NavbarComponent.getJWT();
      this.user = NavbarComponent.getUser();
    });

    this.service.getCourses()                // do not provide jwt -> we are just an admin. don't need grades
        .pipe(take(1))
        .subscribe(results => {
          if (results.hasOwnProperty('error')) {
            alert('Backend Error: ' + results.message);
          } else {
            this.courses = results.courses;
          }
        }, error => {
          alert('HTTP Error: ' + error);
        });

    this.newCourseForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      ects: new FormControl('', [Validators.required]),
      semester: new FormControl('', [Validators.required]),
      category: new FormControl('', [Validators.required]),
      type: new FormControl('', [Validators.required])
    });
  }

  ngOnDestroy() {
    this.logInOrOutSubscription.unsubscribe();
  }

  get title() {
    return this.newCourseForm.get('title');
  }

  get semester() {
    return this.newCourseForm.get('semester');
  }

  get ects() {
    return this.newCourseForm.get('ects');
  }

  get category() {
    return this.newCourseForm.get('category');
  }

  get type() {
    return this.newCourseForm.get('type');
  }


}
