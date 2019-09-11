import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {CoursesService} from '../../services/courses.service';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {

  constructor(private service: CoursesService) { }
  static globalCourses: any[];
  @Input() jwt: string;
  @Input() user: object;
  courses: any[] = null;
  avgGrade: number;
  totalEcts: number;

  // filter inputs:
  semesterFilter = 0;
  specificationFilter = 0;
  coreFilter = false;
  labFilter = false;
  AFilter = false;
  BFilter = false;
  GEFilter = false;
  freeFilter = false;
  obligatory = false;
  obligatoryByChoice = false;
  basic = false;
  optional = false;

  static roundUp(num, precision) {
    precision = Math.pow(10, precision);
    return Math.ceil(num * precision) / precision;
  }

  ngOnInit() {
    this.jwt = NavbarComponent.getJWT();
    this.user = NavbarComponent.getUser();
    if (CoursesComponent.globalCourses == null) {
      console.log('Loading courses...');
      this.getCourses();
      CoursesComponent.globalCourses = this.courses;
    } else {
      console.log('Restoring courses from static...');
      this.courses = CoursesComponent.globalCourses;
      this.calculateAVGandECTS();
    }
  }

  // tslint:disable-next-line:use-life-cycle-interface
  ngOnChanges(changes: SimpleChanges) {
    if (this.jwt !== null) {   // if logged in
      this.getCourses();
    }
  }

  getCourses() {
    if (environment.useDummyData) {
      this.courses = [
        {id: 1, title: 'Εισαγωγή στον Προγραμματισμό', ects: 7, semester: 1, category: 'core', type: 'obligatory',
         E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 2, title: 'Διακριτά Μαθηματικά', ects: 7, semester: 1, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 3, title: 'Λογική Σχεδίαση', ects: 6, semester: 1, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 4, title: 'Εργαστήριο Λογικής Σχεδίασης', ects: 2, semester: 1, category: 'optional_lab', type: 'optional',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 5, title: 'Εισαγωγή στην Πληροφορική και τις Τηλεπικοινωνίες', ects: 2, semester: 1, category: 'general_education',
          type: 'obligatory', E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 6, title: 'Γραμμική Άλγεβρα', ects: 7, semester: 1, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 7, title: 'Ανάλυση Ι', ects: 8, semester: 2, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 8, title: 'Δομές Δεδομένων και Τεχνικές Προγραμματισμού', ects: 7, semester: 2, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 9, title: 'Ηλεκτρομαγνητισμός, Οπτική και Σύγχρονη Φυσική', ects: 8, semester: 2, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 10, title: 'Αρχιτεκτονική Υπολογιστών Ι', ects: 7, semester: 2, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 11, title: 'Αντικειμενοστραφής Προγραμματισμός', ects: 8, semester: 3, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 12, title: 'Ανάλυση ΙI', ects: 8, semester: 3, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 13, title: 'Σήματα και Συστήματα', ects: 6, semester: 3, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 14, title: 'Πιθανότητες και Στατιστική', ects: 6, semester: 3, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 15, title: 'Εργαστήριο Κυκλωμάτων και Συστημάτων', ects: 2, semester: 3, category: 'optional_lab', type: 'optional',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 16, title: 'Σχεδίαση και Χρήση Βάσεων Δεδομένων', ects: 7, semester: 4, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 17, title: 'Αλγόριθμοι και Πολυπλοκότητα', ects: 8, semester: 4, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 18, title: 'Συστήματα Επικοινωνιών', ects: 7, semester: 4, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 19, title: 'Δίκτυα Επικοινωνιών Ι', ects: 6, semester: 4, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 20, title: 'Εργαστήριο Δικτύων Επικοινωνιών Ι', ects: 2, semester: 4, category: 'optional_lab', type: 'optional',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 21, title: 'Λειτουργικά Συστήματα', ects: 6, semester: 5, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 22, title: 'Προγραμματισμός Συστήματος', ects: 6, semester: 6, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 23, title: 'Project', ects: 8, semester: 7, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 24, title: 'Πτυχιακή / Πρακτική Ι', ects: 8, semester: 7, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 25, title: 'Πτυχιακή / Πρακτική ΙΙ', ects: 8, semester: 8, category: 'core', type: 'obligatory',
          E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 26, title: 'Θωρία Υπολογισμού', ects: 6, semester: 5, category: 'A', type: 'obligatory',
          E1: false, E2: true, E3: false, E4: false, E5: false, E6: false },
        {id: 27, title: 'Αριθμητική Ανάλυση', ects: 6, semester: 5, category: 'A', type: 'obligatory',
          E1: true, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 28, title: 'Υλοποίηση Συστημάτων Βάσεων Δεδομένων', ects: 6, semester: 5, category: 'A', type: 'obligatory',
          E1: false, E2: true, E3: true, E4: false, E5: false, E6: false },
        {id: 29, title: 'Μεταγλωττιστές', ects: 6, semester: 5, category: 'A', type: 'obligatory-by-choice',
          E1: false, E2: false, E3: true, E4: false, E5: false, E6: false },

        {id: 30, title: 'Αρχές Γλωσσών Προγραμματισμού', ects: 6, semester: 5, category: 'A', type: 'basic',
          E1: true, E2: true, E3: false, E4: false, E5: false, E6: false },
        {id: 31, title: 'Τεχνητή Νοημοσύνη', ects: 6, semester: 5, category: 'A', type: 'basic',
          E1: false, E2: true, E3: true, E4: false, E5: false, E6: false },
        {id: 32, title: 'Τεχνικές Εξόρυξης Δεδομένων', ects: 6, semester: 5, category: 'A', type: 'basic',
          E1: false, E2: true, E3: false, E4: false, E5: false, E6: false },
        {id: 33, title: 'Επικοινωνία Ανθρώπου Μηχανής', ects: 6, semester: 7, category: 'A', type: 'basic',
          E1: false, E2: true, E3: true, E4: false, E5: false, E6: false },

        {id: 34, title: 'Δομή και Θεσμοί της Ευρωπαικής Ένωσης', ects: 2, semester: 7, category: 'general_education',
          type: 'obligatory', E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },
        {id: 35, title: 'Διοίκηση Έργων', ects: 2, semester: 8, category: 'general_education',
          type: 'obligatory', E1: false, E2: false, E3: false, E4: false, E5: false, E6: false },

        {id: 36, title: 'Ανάλυση και Σχεδίαση Συστημάτων Λογισμικού', ects: 6, semester: 6, category: 'A', type: 'basic',
          E1: false, E2: false, E3: true, E4: false, E5: false, E6: false },
        {id: 37, title: 'Τεχνολογίες Εφαρμογών Διαδικτύου', ects: 6, semester: 8, category: 'A', type: 'basic',
          E1: false, E2: false, E3: true, E4: false, E5: false, E6: false },
        {id: 38, title: 'Παράλληλα Συστήματα', ects: 6, semester: 5, category: 'A', type: 'basic',
          E1: false, E2: false, E3: true, E4: true, E5: false, E6: false },
        {id: 49, title: 'Προστασία και Ασφάλεια Υπολογιστικών Συστημάτων', ects: 6, semester: 8, category: 'A', type: 'basic',
          E1: false, E2: false, E3: true, E4: false, E5: false, E6: false },
        {id: 50, title: 'Τεχνολογία Λογισμικού', ects: 6, semester: 8, category: 'A', type: 'basic',
          E1: false, E2: false, E3: true, E4: false, E5: false, E6: false },

        {id: 51, title: 'Θεωρία Παιγνίων', ects: 7, semester: 5, category: 'free', type: 'optional',
          E1: false, E2: false, E3: true, E4: false, E5: false, E6: false },
        {id: 52, title: 'Γραφικά Ι', ects: 6, semester: 5, category: 'A', type: 'basic',
          E1: true, E2: false, E3: true, E4: false, E5: false, E6: false }
      ];
      this.calculateAVGandECTS();
      return;
    }
    ///////////////////////
    this.service.getCourses(this.jwt)
    .done(results => {
      if (results.hasOwnProperty('error')) {
        alert(results.message);
      } else {
        this.courses = results.courses;
        this.calculateAVGandECTS();
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      alert('>Error: ' + jqXHR.status + ':' + textStatus + ' ' + errorThrown);
    });
  }

  calculateAVGandECTS() {
    // tslint:disable-next-line:variable-name
    let total_ects = 0;
    // tslint:disable-next-line:variable-name
    let sum_coef_grades = 0.0;
    this.courses.forEach((course) => {
      if (course.hasOwnProperty('grade') && course.grade >= 5) {
        total_ects += course.ects;
        sum_coef_grades += course.ects * course.grade;
      }
    });
    this.totalEcts = total_ects;
    this.avgGrade = (total_ects > 0) ? sum_coef_grades / total_ects : 0.0;
    this.avgGrade = CoursesComponent.roundUp(this.avgGrade, 2);
  }

  getClassNameForAVG(): string {
    if (this.avgGrade === 0.0) {
      return '';
    } else if (this.avgGrade < 5.0) {
      return 'failing_grade';
    } else if (this.avgGrade < 6.0) {
      return 'bad_grade';
    } else if (this.avgGrade < 7.0) {
      return 'okay_grade';
    } else if (this.avgGrade < 8.0) {
      return 'good_grade';
    } else if (this.avgGrade < 9.0) {
      return 'very_good_grade';
    } else {
      return 'perfect_grade';
    }
  }

}
