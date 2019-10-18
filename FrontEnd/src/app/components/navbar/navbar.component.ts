import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {NavbarService} from '../../services/navbar.service';
import {environment} from '../../../environments/environment';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Toasts} from '../../utils/Toasts';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  static logInOrOutEvent = new Subject();
  static userChanged = new Subject();

  private jwt: string;
  @Input() private user: any;
  @Output() private loggedInOrOut = new EventEmitter();
  @Output() private selectedPage = new EventEmitter();
  private loginForm: FormGroup = null;    // Reactive Form
  private invalidLoginForm = false;

  private userChangedSubscription;

  public static getJWT(): string {
    return localStorage.getItem('jwt');
  }

  public static getUser(): object {
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

  constructor(private service: NavbarService) {}

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });
    this.userChangedSubscription = NavbarComponent.userChanged.subscribe(() => {
      this.user = NavbarComponent.getUser();
    });
  }

  ngOnDestroy() {
    this.userChangedSubscription.unsubscribe();
  }

  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }

  login() {
    if (environment.useDummyData) {
      this.email.setValue('');
      this.password.setValue('');
      document.getElementById('loginBtn').blur();
      Toasts.toast('Δεν μπορείτε να συνδεθείτε σε offline mode');
      return;
    }
    if (!this.loginForm.valid) {
      this.invalidLoginForm = true;
      return;
    } else {
      this.invalidLoginForm = false;
    }
    this.service.login(this.email.value, this.password.value)
        .subscribe(results => {
          console.log(results);
          if (results.hasOwnProperty('jwt') && results.hasOwnProperty('user')) {
            NavbarComponent.setSession(results.jwt, results.user);
            this.jwt = results.jwt;
            this.user = results.user;
            this.loggedInOrOut.emit(true);
            localStorage.setItem('reset_courses', 'true');
            NavbarComponent.logInOrOutEvent.next(true);
            if (!(window.location.pathname === '/courses' || window.location.pathname === '/courses/')) {
              this.select_page('courses');          // redirect
            }
            // clear form
            this.email.setValue('');
            this.password.setValue('');
          } else {
            alert('Error: ' + ((results.hasOwnProperty('message')) ? results.message : 'Unknown'));
          }
        }, error => {
            alert('HTTP Error: ' + error);
        });
  }

  logout() {
    NavbarComponent.unsetSession();
    this.jwt = null;
    this.user = null;
    this.loggedInOrOut.emit(false);
    localStorage.setItem('reset_courses', 'true');   // reset
    NavbarComponent.logInOrOutEvent.next(false);
    this.select_page('homepage');    // redirect
    // clear form
    this.email.setValue('');
    this.password.setValue('');
  }

  select_page(page: string) {
    const links = document.getElementsByClassName('nav-link');
    for (let i = 0; i < links.length; i++) {
      links.item(i).classList.remove('isSelected');
    }
    if (page !== 'homepage') {
      document.getElementById(page + '_page').classList.add('isSelected');
    }
    this.selectedPage.emit(page);
  }

}
