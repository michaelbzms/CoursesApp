import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from './components/navbar/navbar.component';

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
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    this.currentPage = '';
  }

  getCurrentPage(page) {
    this.currentPage = page;
  }

  loggedInEvent(inOrOut: boolean) {
    console.log('Detected login or logout.. re-fetching JWT and user from local storage')
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

}
