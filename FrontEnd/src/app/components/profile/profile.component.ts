import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import * as $ from 'jquery';
import {ProfileService} from '../../services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: any;
  @Output() sessionChanged = new EventEmitter();

  static validateEmail(email) {
    let re: RegExp;
    // tslint:disable-next-line:max-line-length
    re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

  constructor(private service: ProfileService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
  }

  updateStudent() {
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
    if (email === this.user.email) {   // if email is the same then do not send it as a new one because backend checks if it is taken
      data = { firstname, lastname };
    } else {
      data = { email, firstname, lastname };
    }
    this.service.updateStudent(this.jwt, this.user.id, data)
      .done(results => {
        if (results.hasOwnProperty('error')) {
          alert(results.message);
        } else {
          this.user.email = email;
          this.user.firstName = firstname;
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
    const form = $('#updateForm');
    form.find('input[name="email"]').val(this.user.email);
    form.find('input[name="firstname"]').val(this.user.firstName);
    form.find('input[name="lastname"]').val(this.user.lastName);
  }

  changePassword() {
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
    this.service.updatePassword(this.jwt, this.user.id, form.find('input[name="oldpassword"]').val(), newpassword)
      .done(results => {
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
    this.service.deleteStudent(this.jwt, this.user.id)
      .done(results => {
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
