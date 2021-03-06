import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CoursesComponent } from './components/courses/courses.component';
import { ProfileComponent } from './components/profile/profile.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CourseComponent } from './components/course/course.component';
import { CoursesFilterPipe } from './components/courses/CoursesFilter';
import { NavbarService } from './services/navbar.service';
import { RouterModule } from '@angular/router';
import { NotFoundComponentComponent } from './components/not-found-component/not-found-component.component';
import { HttpClientModule } from '@angular/common/http';
import { CoursesmanagementComponent } from './components/coursesmanagement/coursesmanagement.component';
import { CoursemanagementComponent } from './components/coursemanagement/coursemanagement.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CoursesComponent,
    ProfileComponent,
    HomepageComponent,
    CourseComponent,
    CoursesFilterPipe,
    NotFoundComponentComponent,
    CoursemanagementComponent,
    CoursesmanagementComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot([
      {path: '', component: HomepageComponent},
      {path: 'courses', component: CoursesComponent},
      {path: 'coursesmanagement', component: CoursesmanagementComponent},
      {path: 'profile', component: ProfileComponent},
      {path: '**', component: NotFoundComponentComponent},
    ], {onSameUrlNavigation: 'reload'}),
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [NavbarService],
  bootstrap: [AppComponent]
})
export class AppModule { }
