import {Component, Input, OnInit} from '@angular/core';
import {environment} from '../../../environments/environment';
import {NavbarComponent} from '../navbar/navbar.component';
import * as $ from 'jquery';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: object;

  constructor() { }

  static validateEmail(email) {
    let re: RegExp;
    // tslint:disable-next-line:max-line-length
    re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

  registerStudent() {
    console.log('Registering...');
    const form = $('#registerForm');
    const email = form.find('input[name="email"]').val();
    const password1 = form.find('input[name="password1"]').val();
    // check input
    if (!HomepageComponent.validateEmail(email)) {
      alert('Λάθος email.');
      return;
    } else if (password1 !== form.find('input[name="password2"]').val()) {
      alert('Διαφορετικοί κωδικοί.');
      return;
    } else if (password1.length < 6) {
      alert('Πολύ μικρός κωδικός. Πρέπει να είναι τουλάχιστον 6 χαρακτήρες.');
      return;
    }
    $.ajax({
      url: environment.apiUrl + '/students',
      method: 'POST',
      dataType: 'json',
      headers: {},
      data: {
        email,
        password: password1,
        firstname: form.find('input[name="firstname"]').val(),
        lastname: form.find('input[name="lastname"]').val()
      }
    }).done(results => {
      console.log(results);
      if (results.hasOwnProperty('error')) {
        alert(results.message);
      } else {
        $('#registerForm').find('input').val('');
        alert('Επιτυχής εγγραφή!');
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert(textStatus + ':' + errorThrown);
    });

  }

}
