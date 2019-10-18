import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-coursemanagement',
  templateUrl: './coursemanagement.component.html',
  styleUrls: ['./coursemanagement.component.css']
})
export class CoursemanagementComponent implements OnInit {

  @Input() private user: any;
  @Input() private jwt: string;
  @Input() private course: any;

  constructor() { }

  ngOnInit() {}

}
