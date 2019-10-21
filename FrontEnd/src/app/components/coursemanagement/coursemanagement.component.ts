import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {CourseService} from '../../services/course.service';
import {take} from 'rxjs/operators';
import {Toasts} from '../../utils/Toasts';

@Component({
  selector: 'app-coursemanagement',
  templateUrl: './coursemanagement.component.html',
  styleUrls: ['./coursemanagement.component.css']
})
export class CoursemanagementComponent implements OnInit {

  @Input() private user: any;
  @Input() private jwt: string;
  @Input() private course: any;
  @ViewChild('titleRef') titleReference;
  @ViewChild('semesterRef') semesterReference;
  @ViewChild('ectsRef') ectsReference;
  @ViewChild('categoryRef') categoryReference;
  @ViewChild('typeRef') typeReference;
  editing = false;

  constructor(private service: CourseService) { }

  ngOnInit() {}

  startEdit() {
    this.editing = true;
  }

  cancelEdit() {
    this.editing = false;
  }

  updateCourse() {
    const newTitle = this.titleReference.nativeElement.value;
    const newSemester = this.semesterReference.nativeElement.value;
    const newEcts = this.ectsReference.nativeElement.value;
    const newCategory = this.categoryReference.nativeElement.value;
    const newType = this.typeReference.nativeElement.value;
    this.editing = false;

    this.service.editCourse(this.jwt, this.course.id, newTitle, newSemester, newEcts, newCategory, newType)
        .pipe(take(1))
        .subscribe(results => {
          if (results.hasOwnProperty('error')) {
            alert('Backend Error: ' + results.message);
          } else {
            this.course.title = newTitle;
            this.course.semester = newSemester;
            this.course.ects = newEcts;
            this.course.category = newCategory;
            this.course.type = newType;
          }
        }, error => {
          alert('HTTP Error: ' + error);
        });
  }

  deleteCourse() {
    if (!confirm('Είστε σίγουροι ότι θέλετε να διαγράψετε αυτό το μάθημα;')) {
      return;
    }
    this.service.deleteCourse(this.jwt, this.course.id)
        .pipe(take(1))
        .subscribe(results => {
          if (results.hasOwnProperty('error')) {
            alert('Backend Error: ' + results.message);
          } else {
            Toasts.toast('Το μάθημα διαγράφηκε επιτυχώς!');
          }
        }, error => {
          alert('HTTP Error: ' + error);
        });
  }

  getCategory() {
    switch (this.course.category) {
      case 'core':
        return 'Κορμός';
      case 'A':
        return 'Κατεύθυνση Α';
      case 'B':
        return 'Κατεύθυνση Β';
      case 'general_education':
        return 'Γενικής Παιδείας';
      case 'free':
        return 'Ελεύθερο';
      case 'optional_lab':
        return 'Προαιρετικό Εργαστήριο';
      default:
        return 'Αγνωστο';
    }
  }

  getType() {
    switch (this.course.type) {
      case 'obligatory':
        return 'Υποχρεωτικό';
      case 'obligatory-by-choice':
        return 'Κατά Επιλογήν Υποχρεωτικό';
      case 'optional':
        return 'Προαιρετικό';
      case 'basic':
        return 'Βασικό';
      default:
        return 'Αγνωστο';
    }
  }

}
