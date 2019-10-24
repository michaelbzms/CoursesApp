# CoursesApp
Τo Courses App είναι μία εφαρμογή για φοιτητές του τμήματος Πληροφορικής και Τηλεπικοινωνιών με την οποία οι τελευταίοι 
μπορούν να καταγράφουν και να παρακολουθούν τους βαθμούς τους στα μαθήματα του τμήματος και να βλέπουν τον μέσο όρο τους 
όπως και τα ECTs που έχουν μαζέψει. 

A simple app (in Greek) for keeping track of student's courses and grades. Supports admin accounts which have to be 
installed manually in the database (to add/modify/delete courses) and student accounts (to see and search for courses 
as well as submit their grades to them).

## Architecture

This project features a  **MySQL Database**, a **Java Restful Backend** and an **Angular Frontend** that consumes a REST API. 
This architecture follows the **MVC (Model-View-Controller) Architecture** where the Backend makes up the Control and 
Model part and the View has been moved to the client.

### DataBase 
In the _/Database/_ folder you will find the DataBase's schematic and scripts to create and populate (with courses) 
the MySQL database.

### BackEnd 
In the _/BackEnd/_ folder you will find the **Java** BackEnd code. This Backend uses **Java Restlets** as Control for
the REST API, which returns **JSON objects**. The **DAO pattern** has been used for the Model to manage the persistence 
of application data and has two different implementations: one using **Spring's JDBC Template** and one using **JPA 
(Hibernate)** (you can choose between them from the _app.properties_ file).

_Gradle_ has been used as the build tool of the Java application and is responsible for downloading all the application's
dependencies. 

_See the app.properties file for possible configuration options._

### FrontEnd
In the _/FrontEnd/_ folder you will find the **Angular** FrontEnd code comprising a **Single Page Application** as the 
View part of the whole application. This was built using _npm_ and the _angular CLI_.

## Additional Technologies / Features

### Authentication and Authorization
The authentication in this application is done with **Jason Web Tokens**, where we store a _user_ object. These tokens 
are created during the login, they are stored encrypted in the Angular Application (FrontEnd) and they are decrypted in 
the BackEnd on each HTTP Request should they be provided by the FrontEnd (as part of the HTTP Request's Headers).

The Authorization is done through a **filter** which will respond with an 403 HTTP Error for unauthorized HTTP Requests.
We determine if the user is authorized from the _user_ object in the Jason Web Token (if provided).

### Password Hashing
The passwords are not stored plaintext into the database. Instead, the salted **SHA256** hashing algorithm is used and 
only their salted hashes are stored.
