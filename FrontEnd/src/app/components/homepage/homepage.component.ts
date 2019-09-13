import {Component, Input, OnInit} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {HomepageService} from '../../services/homepage.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Customvalidators} from '../../utils/customvalidators';
import {Toasts} from '../../utils/Toasts';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  @Input() jwt: string;
  @Input() user: any;
  registerForm: FormGroup = null;

  constructor(private service: HomepageService) { }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();

    this.registerForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      newpassword: new FormControl('', [Validators.required, Validators.minLength(6)]),
      repassword: new FormControl('', [Validators.required]),
      firstname: new FormControl('', [Validators.required]),
      lastname: new FormControl('', [Validators.required])
    }, [Customvalidators.matchingPasswordsValidator]);
  }

  registerStudent() {
    const email = this.email.value;
    const password1 = this.newpassword.value;
    // check input
    if (!this.registerForm.valid) {
      alert('Invalid Register Form');
      return;
    }
    this.service.registerStudent(email, password1, this.firstname.value, this.lastname.value)
        .subscribe(results => {
          if (results.hasOwnProperty('error')) {
            alert('Backend Error: ' + results.message);
          } else {
            this.email.setValue('');
            this.newpassword.setValue('');
            this.repassword.setValue('');
            this.firstname.setValue('');
            this.lastname.setValue('');
            Toasts.toast('Επιτυχής εγγραφή');
          }
        }, error => {
          alert('HTTP Error: ' + error);
        });
  }

  get email() {
    return this.registerForm.get('email');
  }

  get newpassword() {
    return this.registerForm.get('newpassword');
  }

  get repassword() {
    return this.registerForm.get('repassword');
  }

  get firstname() {
    return this.registerForm.get('firstname');
  }

  get lastname() {
    return this.registerForm.get('lastname');
  }

}
