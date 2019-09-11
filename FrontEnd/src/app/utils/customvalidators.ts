import {FormGroup, ValidationErrors} from '@angular/forms';


export class Customvalidators {

  public static matchingPasswordsValidator(group: FormGroup): ValidationErrors | null {
    const newpassword = group.get('newpassword').value;
    const repassword = group.get('repassword').value;
    if (repassword !== null && repassword !== '' && newpassword !== repassword ) {
      return {mismatchingpasswords: true};
    }
    return null;
  }

}
