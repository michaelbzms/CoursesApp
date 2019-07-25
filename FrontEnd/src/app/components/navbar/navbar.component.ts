import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { environment } from '../../../environments/environment';
import * as $ from 'jquery';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  jwt: string;
  @Input() user: object;

  @Output() loggedInOrOut = new EventEmitter();
  @Output() selectedPage = new EventEmitter();

  public static getJWT(): string {
    return localStorage.getItem('jwt');
  }

  public static getUser(): object {
    console.log('user = ' + localStorage.getItem('user'));
    return JSON.parse(localStorage.getItem('user'));
  }

  static setSession(token, user) {
    localStorage.setItem('jwt', token);
    localStorage.setItem('user', JSON.stringify(user));
  }

  static unsetSession() {
    localStorage.removeItem('jwt');
    localStorage.removeItem('user');
  }

  static setUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  static unsetUser() {
    localStorage.removeItem('user');
  }

  constructor() {}

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

  login() {
    console.log('Loggin in...');
    $.ajax({
      url: environment.apiUrl + '/login',
      method: 'POST',
      dataType: 'json',
      headers: {},
      data: {
        email: $('#emailLogin').val(),
        password: $('#passwordLogin').val()
      }
    }).done(results => {
      console.log(results);
      if (results.hasOwnProperty('jwt') && results.hasOwnProperty('user')) {
        NavbarComponent.setSession(results.jwt, results.user);
        this.jwt = results.jwt;
        this.user = results.user;
        this.loggedInOrOut.emit(true);
        this.select_page('courses');  // redirect
      } else {
        alert('Error: ' + ((results.hasOwnProperty('message')) ? results.message : 'Unknown'));
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert(textStatus + ':' + errorThrown);
    });
  }

  logout() {
    NavbarComponent.unsetSession();
    this.jwt = null;
    this.user = null;
    this.loggedInOrOut.emit(false);
    if ($('#profile_page').hasClass('isSelected')) {
      this.select_page('homepage');  // redirect
    }
  }

  select_page(page: string) {
    console.log('Selected ' + page + ' page!');
    $('.nav-link').removeClass('isSelected');
    $('#' + page + '_page').addClass('isSelected');
    this.selectedPage.emit(page);
  }

}
