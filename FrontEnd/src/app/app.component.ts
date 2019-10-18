import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from './components/navbar/navbar.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  jwt: string;      // Jason Web Token got from logging in
  user: object;
  currentPage: string;

  constructor(private router: Router) {}

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    this.currentPage = '';
  }

  getCurrentPage(page) {
    this.currentPage = page;
    this.router.navigateByUrl('/' + (page === 'homepage' ? '' : page));
  }

  loggedInOrOutEvent(inOrOut: boolean) {
    if (inOrOut) {   // login
      this.jwt = NavbarComponent.getJWT();
      this.user = NavbarComponent.getUser();
    } else {         // logout
      this.jwt = null;
      this.user = null;
    }
  }

  sessionChangedEvent() {
    this.user = NavbarComponent.getUser();
  }

}
