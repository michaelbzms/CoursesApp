import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'coursesFilter'
})
export class CoursesFilterPipe implements PipeTransform {

  transform(value: any, filterCriteria: object): any {
    return value.filter(v => {
      // TODO
      return true;
    });
  }

}
