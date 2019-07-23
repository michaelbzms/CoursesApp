import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  jwt: string;
  @Output() loggedIn = new EventEmitter();
  @Output() selectedPage = new EventEmitter();

  constructor() {}

  ngOnInit() {
    this.jwt = null;
  }

  login() {
    this.jwt = /* sth */ 'token';
    this.loggedIn.emit(this.jwt);
  }

  selected_page(page: string) {
    console.log('Selected ' + page + ' page!');
    $('.nav-link').removeClass('isSelected');
    $('#' + page + '_page').addClass('isSelected');
    this.selectedPage.emit(page);
  }

}
