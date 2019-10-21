import {Component, OnDestroy, OnInit} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {CoursesService} from '../../services/courses.service';
import {take} from 'rxjs/operators';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-coursesmanagement',
  templateUrl: './coursesmanagement.component.html',
  styleUrls: ['./coursesmanagement.component.css']
})
export class CoursesmanagementComponent implements OnInit, OnDestroy {

  static courseDeletedEvent = new Subject();
  private user: any;
  private jwt: string;
  private courses: any[];
  private newCourseForm: FormGroup = null;   // Reactive form
  private triedSubmittingForm = false;

  private logInOrOutSubscription;
  private courseDeletionSubscription;

  constructor(private service: CoursesService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    this.logInOrOutSubscription = NavbarComponent.logInOrOutEvent.subscribe((val) => {
      this.jwt = NavbarComponent.getJWT();
      this.user = NavbarComponent.getUser();
    });

    this.getCourses();

    this.newCourseForm = new FormGroup({
      title: new FormControl('', [Validators.required]),
      ects: new FormControl('', [Validators.required]),
      semester: new FormControl('', [Validators.required]),
      category: new FormControl('', [Validators.required]),
      type: new FormControl('', [Validators.required])
    });

    this.courseDeletionSubscription = CoursesmanagementComponent.courseDeletedEvent.subscribe((courseId) => {
      for (let i = 0 ; i < this.courses.length ; i++) {
        if (this.courses[i].id === courseId) {
          this.courses.splice(i, 1);
        }
      }
    });
  }

  ngOnDestroy() {
    this.logInOrOutSubscription.unsubscribe();
    this.courseDeletionSubscription.unsubscribe();
  }

  getCourses() {
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
  }

  submitNewCourse() {
    if (!this.newCourseForm.valid) {
      this.triedSubmittingForm = true;
      console.log('Invalid form' + this.triedSubmittingForm);
      setTimeout(() => {
        this.triedSubmittingForm = false;
      }, 3000);
      return;
    }
    this.service.submitNewCourse(this.jwt, this.title.value, this.semester.value, this.ects.value,
                                 this.category.value, this.type.value)
      .pipe(take(1))
      .subscribe(results => {
        if (results.hasOwnProperty('error')) {
          alert('Backend Error: ' + results.message);
        } else {
          // re-fetch all courses
          this.getCourses();  // To improve: fetch only new one instead of all again?
          // reset form
          this.title.setValue('');
          this.semester.setValue('');
          this.ects.setValue('');
          this.category.setValue('');
          this.type.setValue('');
        }
      }, error => {
        alert('HTTP Error: ' + error);
      });
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
