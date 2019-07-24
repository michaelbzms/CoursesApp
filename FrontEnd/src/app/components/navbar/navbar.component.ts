import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { environment } from '../../../environments/environment';

import * as $ from 'jquery';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  jwt: string;
  user: object;
  @Output() loggedIn = new EventEmitter();
  @Output() selectedPage = new EventEmitter();

  constructor() {}

  ngOnInit() {
    this.jwt = null;
    this.user = null;
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
      },
      statusCode: {
        404: () => {
          alert('Error 404');
        }
      }
    }).done(results => {
      console.log(results);
      if (results.hasOwnProperty('jwt') && results.hasOwnProperty('user')) {
        this.jwt = results.jwt;
        this.user = results.user;
      } else {
        alert('Error: ' + ((results.hasOwnProperty('message')) ? results.message : 'Unknown'));
      }
    });
    this.loggedIn.emit({jwt: this.jwt, user: this.user});
  }

  selected_page(page: string) {
    console.log('Selected ' + page + ' page!');
    $('.nav-link').removeClass('isSelected');
    $('#' + page + '_page').addClass('isSelected');
    this.selectedPage.emit(page);
  }

}
