import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CoursesComponent } from './components/courses/courses.component';
import { ProfileComponent } from './components/profile/profile.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { FormsModule } from '@angular/forms';
import { CourseComponent } from './components/course/course.component';
import { CoursesFilterPipe } from './components/courses/CoursesFilter';
import { NavbarService } from './services/navbar.service';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CoursesComponent,
    ProfileComponent,
    HomepageComponent,
    CourseComponent,
    CoursesFilterPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
  ],
  providers: [NavbarService],
  bootstrap: [AppComponent]
})
export class AppModule { }
