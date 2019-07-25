import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {environment} from '../../../environments/environment';
import * as $ from 'jquery';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: object;
  @Output() sessionChanged = new EventEmitter();

  static validateEmail(email) {
    let re: RegExp;
    // tslint:disable-next-line:max-line-length
    re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

  constructor() { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

  updateStudent() {
    console.log('Updating...');
    const form = $('#updateForm');
    const email = form.find('input[name="email"]').val();
    const firstname = form.find('input[name="firstname"]').val();
    const lastname = form.find('input[name="lastname"]').val();
    // check input
    if (!ProfileComponent.validateEmail(email)) {
      alert('Λάθος email.');
      return;
    }
    let data = null;
    // @ts-ignore
    if (email === this.user.email) {   // if email is the same then do not send it as a new one because backend checks if it is taken
      data = { firstname, lastname };
    } else {
      data = { email, firstname, lastname };
    }
    $.ajax({
      // @ts-ignore
      url: environment.apiUrl + '/students/' + this.user.id,
      method: 'PUT',
      dataType: 'json',
      headers: { jwt: this.jwt },
      data
    }).done(results => {
      console.log(results);
      if (results.hasOwnProperty('error')) {
        alert(results.message);
      } else {
        // @ts-ignore
        this.user.email = email;
        // @ts-ignore
        this.user.firstName = firstname;
        // @ts-ignore
        this.user.lastName = lastname;
        NavbarComponent.unsetUser();
        NavbarComponent.setUser(this.user);
        this.sessionChanged.emit();
        alert('Επιτυχής ενημέρωση στοιχείων λογαριασμού.');
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert(textStatus + ':' + errorThrown);
    });
  }

  resetUpdateForm() {
    console.log('Resetting update form...');
    const form = $('#updateForm');
    // @ts-ignore
    form.find('input[name="email"]').val(this.user.email);
    // @ts-ignore
    form.find('input[name="firstname"]').val(this.user.firstName);
    // @ts-ignore
    form.find('input[name="lastname"]').val(this.user.lastName);
  }

  changePassword() {
    console.log('Changing password...');
    const form = $('#changePasswordForm');
    const newpassword = form.find('input[name="newpassword"]').val();
    // check input
    if (newpassword !== form.find('input[name="repassword"]').val()) {
      alert('Διαφορετικοί κωδικοί.');
      return;
    } else if (newpassword.length < 6) {
      alert('Πολύ μικρός κωδικός. Πρέπει να είναι τουλάχιστον 6 χαρακτήρες.');
      return;
    }
    $.ajax({
      // @ts-ignore
      url: environment.apiUrl + '/users/' + this.user.id,
      method: 'PUT',
      dataType: 'json',
      headers: { jwt: this.jwt },
      data: {
        oldpassword: form.find('input[name="oldpassword"]').val(),
        newpassword
      }
    }).done(results => {
      console.log(results);
      if (results.hasOwnProperty('error')) {
        alert(results.message);
      } else {
        $('#changePasswordForm').find('input').val('');
        alert('Επιτυχής αλλαγή κωδικού.');
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert(textStatus + ':' + errorThrown);
    });
  }

  deleteAccount() {
    if (!confirm('Είστε σίγουροι ότι θέλετε να διαγράψετε τον λογαριασμό σας μαζί με όλες τις πληροφορίες του;')) {
      return;
    }
    $.ajax({
      // @ts-ignore
      url: environment.apiUrl + '/students/' + this.user.id,
      method: 'DELETE',
      dataType: 'json',
      headers: { jwt: this.jwt },
      data: {}
    }).done(results => {
      console.log(results);
      if (results.hasOwnProperty('error')) {
        alert(results.message);
      } else {
        NavbarComponent.unsetSession();  // auto-logout
        this.sessionChanged.emit();
        alert('Επιτυχής διαγραφή λογαριασμού.');
        window.location.replace('/');  // in this case only redirect the old fashioned way
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert(textStatus + ':' + errorThrown);
    });
  }

}
