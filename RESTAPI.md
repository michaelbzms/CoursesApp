# REST API 

This is the summary of the REST API provided by the BackEnd. Input should be URL-encoded whilst the output is in JSON format.
Java Classes Names used in the output below mean their JSON object counterparts. The Jason Web Token "jwt" input is meant 
to be on the HTTP Headers while the rest input parameters and Query or Body parameters depending on the method.


| Symbols | Meaning |
| --- | --- |
| name: JSon_output | "name" is the field of the single JSON object returned which has the JSon_output as value
| ∈ { ... }  | to denote all the possible values of a parameter  |
| [ ... ] ή [ ... : condition ]  |  to denote optional parameters or parameters when a condition is met |


# REST API End-Points

#### /login
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| POST   | email, password | jwt:login token and user: User object on success (or error) | performs authentication check and returns login token and user object on success

#### /users/{id}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| PUT    | jwt, oldpassword, newpassword | success/error message | updates user with uri's id password if the old password given is correct (must be the same student)

#### /students
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | jwt   | students: JSon array of all students | returns all the students (must be admin)
| POST   | email, password, firstname, lastname | success/error message | registers a new student

#### /students/{id}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | jwt   | student: student JSon Object or 404 | returns student with uri's id (must be the same student or admin)
| PUT    | jwt, email, firstname, lastname    | success/error message | updates student's info (must be the same student)
| DELETE | jwt   | success/error message | deletes student with uri's id (must be the same student or admin)

#### /courses
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | -     | courses: JSon array of all courses | returns all courses
| GET    | jwt   | courses: JSon array of all courses where a course has also a grade field if the student with given jwt has submitted a grade for it | returns all courses and their grades if student with given jwt has submitted a grade (must be student)
| POST   | jwt, title, semester, ects, category, type | success/error message | submits a new course (must be admin)

#### /courses/{id}
| Method | Input | Output | Action |
| ------ | ----- | ------ | ------ |
| GET    | -     | course: JSon object of Course with uri's id or 404 | returns the course with uri's id
| GET    | jwt   | course: JSon object of Course with uri's id and jwt's student's grade if submitted or 404 | returns the course with uri's id and its grade if it exists (must be student)
| POST   | jwt, [grade] | success/error message | submits given grade for course with uri's id or removes existing grade if not given or given null (must be student)
| PUT    | jwt, title, semester, ects, category, type | success/error message | updates course with uri's id (must be admin)
| DELETE | jwt     | success/error message | removes course with uri's id (must be admin)

In the above:  
category ∈ {'core', 'A', 'B', 'optional_lab', 'free', 'general_education' }  
type ∈ {'obligatory', 'obligatory-by-choice', 'basic', 'optional' }
