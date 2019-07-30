import {Pipe, PipeTransform} from '@angular/core';


@Pipe({
  name: 'coursesFilter',
})
export class CoursesFilterPipe implements PipeTransform {

  transform(courses: any, semester: number, specification: number, core: boolean, lab: boolean,
            A: boolean, B: boolean, GE: boolean, free: boolean, obligatory: boolean, obligatoryByChoice: boolean,
            basic: boolean, optional: boolean): any {
    console.log('Running filter!');
    console.log(courses);
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
