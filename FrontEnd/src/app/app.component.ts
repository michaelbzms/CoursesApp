import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  jwt: string;      // Jason Web Token got from logging in
  user: object;
  currentPage: string;

  constructor() {}

  ngOnInit() {
    this.jwt = null;
    this.user = null;
    this.currentPage = '';
  }

  getJWTandUser(jwtAndUser) {
    this.jwt = jwtAndUser.jwt;
    this.user = jwtAndUser.user;
  }

  getCurrentPage(page) {
    this.currentPage = page;
  }

}
