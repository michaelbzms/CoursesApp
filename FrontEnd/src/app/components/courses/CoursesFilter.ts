import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'coursesFilter',
})
export class CoursesFilterPipe implements PipeTransform {

  transform(courses: any, semester: number, specification: number): any {
    console.log('Running filter!');
    console.log(courses);
    console.log(semester);
    console.log(specification);
    return courses.filter(c => {
      // Semester constraint
      // tslint:disable-next-line:triple-equals
      if (semester != 0 && c.semester != semester) {
        console.log('bad semester:' + c.semester);
        return false;
      }
      // Specification constraint
      // tslint:disable-next-line:triple-equals
      if (specification != 0 && c.E[specification - 1] == false) {
        console.log('bad specification');
        return false;
      }
      // Category constraints
      // TODO
      // Type constraints
      // TODO
      return true;
    });

  }

}
