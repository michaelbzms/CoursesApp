import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {ProfileService} from '../../services/profile.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Customvalidators} from '../../utils/customvalidators';
import {Toasts} from '../../utils/Toasts';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: any;
  @Output() sessionChanged = new EventEmitter();
  updateForm: FormGroup = null;           // Reactive form
  changePasswordForm: FormGroup = null;   // ^^

  constructor(private service: ProfileService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();

    this.updateForm = new FormGroup({
      email: new FormControl(this.user ? this.user.email : '', [Validators.required, Validators.email]),
      firstname: new FormControl(this.user ? this.user.firstName : '', [Validators.required]),
      lastname: new FormControl(this.user ? this.user.lastName : '', [Validators.required])
    });

    this.changePasswordForm = new FormGroup({
      oldpassword: new FormControl('', [Validators.required]),
      newpassword: new FormControl('', [Validators.required, Validators.minLength(6)]),
      repassword: new FormControl('', [Validators.required])
    }, [Customvalidators.matchingPasswordsValidator] );
  }

  updateStudent() {
    const email = this.email.value;
    const firstname = this.firstname.value;
    const lastname = this.lastname.value;
    // check input
    if (!this.updateForm.valid) {
      alert('Invalid Update Form!');
      return;
    }
    let data = null;
    if (email === this.user.email) {   // if email is the same then do not send it as a new one because backend checks if it is taken
      data = { firstname, lastname };
    } else {
      data = { email, firstname, lastname };
    }
    this.service.updateStudent(this.jwt, this.user.id, data)
      .subscribe(results => {
        if (results.hasOwnProperty('error')) {
          alert('Backend Error: ' + results.message);
        } else {
          this.user.email = email;
          this.user.firstName = firstname;
          this.user.lastName = lastname;
          NavbarComponent.unsetUser();
          NavbarComponent.setUser(this.user);
          this.sessionChanged.emit();
          NavbarComponent.userChanged.next();
          Toasts.toast('Επιτυχής ενημέρωση στοιχείων λογαριασμού.');
        }
    }, error => {
      alert('HTTP Error: ' + error);
    });
  }

  resetUpdateForm() {
    this.email.setValue(this.user.email);
    this.firstname.setValue(this.user.firstName);
    this.lastname.setValue(this.user.lastName);
  }

  changePassword() {
    // check input
    if (!this.changePasswordForm.valid) {
      alert('Invalid ChangePassword Form!');
      return;
    }
    this.service.updatePassword(this.jwt, this.user.id, this.oldpassword.value, this.newpassword.value)
      .subscribe(results => {
        if (results.hasOwnProperty('error')) {
          alert('Backend Error: ' + results.message);
        } else {
          this.oldpassword.setValue('');
          this.newpassword.setValue('');
          this.repassword.setValue('');
          Toasts.toast('Επιτυχής αλλαγή κωδικού');
        }
    }, error => {
      alert('HTTP Error: ' + error);
    });
  }

  deleteAccount() {
    if (!confirm('Είστε σίγουροι ότι θέλετε να διαγράψετε τον λογαριασμό σας μαζί με όλες τις πληροφορίες του;')) {
      return;
    }
    this.service.deleteStudent(this.jwt, this.user.id)
      .subscribe(results => {
        if (results.hasOwnProperty('error')) {
          alert('Backend Error: ' + results.message);
        } else {
          NavbarComponent.unsetSession();  // auto-logout
          this.sessionChanged.emit();
          alert('Επιτυχής διαγραφή λογαριασμού.');
          window.location.replace('/');  // in this case only redirect the old fashioned way
        }
    }, error => {
      alert('HTTP Error: ' + error);
    });
  }

  get email() {
    return this.updateForm.get('email');
  }

  get firstname() {
    return this.updateForm.get('firstname');
  }

  get lastname() {
    return this.updateForm.get('lastname');
  }

  get newpassword() {
    return this.changePasswordForm.get('newpassword');
  }

  get oldpassword() {
    return this.changePasswordForm.get('oldpassword');
  }

  get repassword() {
    return this.changePasswordForm.get('repassword');
  }

}
