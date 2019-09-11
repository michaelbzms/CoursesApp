/* tslint:disable:triple-equals */
import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'coursesFilter',
})
export class CoursesFilterPipe implements PipeTransform {

  transform(courses: any, semester: number, specification: number, core: boolean, lab: boolean,
            A: boolean, B: boolean, GE: boolean, free: boolean, obligatory: boolean, obligatoryByChoice: boolean,
            basic: boolean, optional: boolean): any {
    return courses.filter(c => {
      // Semester constraint
      if (semester != 0 && c.semester != semester) {
        return false;
      }
      // Specification constraint
      if ((specification == 1 && !c.E1) || (specification == 2 && !c.E2) ||
          (specification == 3 && !c.E3) || (specification == 4 && !c.E4) ||
          (specification == 5 && !c.E5) || (specification == 6 && !c.E6)) {
        return false;
      }
      // Category constraints
      if (core !== false || lab !== false || A !== false || B !== false || GE !== false || free !== false) {
        if (core === false && c.category === 'core' ) { return false; }
        if (lab === false && c.category === 'optional_lab' ) { return false; }
        if (A === false && c.category === 'A' ) { return false; }
        if (B === false && c.category === 'B' ) { return false; }
        if (GE === false && c.category === 'general_education' ) { return false; }
        if (free === false && c.category === 'free' ) { return false; }
      }
      // Type constraints
      if (obligatory !== false || obligatoryByChoice !== false || basic !== false || optional !== false) {
        if (obligatory === false && c.type === 'obligatory' ) { return false; }
        if (obligatoryByChoice === false && c.type === 'obligatory-by-choice' ) { return false; }
        if (basic === false && c.type === 'basic' ) { return false; }
        if (optional === false && c.type === 'optional' ) { return false; }
      }
      return true;
    });

  }

}
