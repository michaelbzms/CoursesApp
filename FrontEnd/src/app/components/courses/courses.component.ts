import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {
  @Input() jwt: string;

  constructor() { }

  ngOnInit() {
    this.jwt = null;
  }

}
