import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavbarService} from '../../services/navbar.service';
import {environment} from '../../../environments/environment';

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
  }

  login() {
    if (environment.useDummyData) {
      (document.getElementById('emailLogin') as HTMLInputElement).value = '';
      (document.getElementById('passwordLogin') as HTMLInputElement).value = '';
      document.getElementById('loginBtn').blur();
      const toast = document.getElementById('loginNotAllowedToast');
      if (!toast.classList.contains('show')) {
        toast.classList.add('show');
        setTimeout(() => {
          toast.classList.remove('show');
        }, 3000);  // must be 3000
      }
      return;
    }

    // console.log('email: ' + (document.getElementById('emailLogin') as HTMLInputElement).value);
    this.service.login((document.getElementById('emailLogin') as HTMLInputElement).value,
                       (document.getElementById('passwordLogin') as HTMLInputElement).value)
        .subscribe(results => {
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
        }, error => {
            alert('HTTP Error: ' + error);
        });
  }

  logout() {
    NavbarComponent.unsetSession();
    this.jwt = null;
    this.user = null;
    this.loggedInOrOut.emit(false);
    if (document.getElementById('profile_page').classList.contains('isSelected')) {
      this.select_page('homepage');  // redirect
    }
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
