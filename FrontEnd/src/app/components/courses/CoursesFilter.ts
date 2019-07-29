import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'coursesFilter',
  pure: false
})
export class CoursesFilterPipe implements PipeTransform {

  transform(courses: object[], criteria: object): any {
    console.log('Running filter!');
    return courses.filter(c => {
      // Semester constraint
      // @ts-ignore
      if (criteria.hasOwnProperty('semester') && !isNaN(criteria.semester) && semesterValue !== 0 && c.semester !== semesterValue) {
        return false;
      }
      // Specification constraint
      // @ts-ignore
      // tslint:disable-next-line:max-line-length
      if (criteria.hasOwnProperty('specification') && !isNaN(criteria.specification) && criteria.specification !== 0 && c.E[criteria.specification] === false) {
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
