DELETE FROM `CoursesAppDB`.`Students_has_Courses`;
DELETE FROM `CoursesAppDB`.`Courses`;

INSERT INTO `CoursesAppDB`.`Courses` (`idCourses`, `title`, `ects`, `semester`, `category`, `type`, `E1`, `E2`, `E3`, `E4`, `E5`, `E6`) VALUES 
(default, 'Εισαγωγή στον Προγραμματισμό', 7, 1, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Διακριτά Μαθηματικά', 7, 1, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Λογική Σχεδίαση', 6, 1, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Εργαστήριο Λογικής Σχεδίασης', 2, 1, 'optional_lab', 'optional', false, false, false, false, false, false),
(default, 'Εισαγωγή στην Πληροφορική και τις Τηλεπικοινωνίες', 2, 1, 'general_education', 'obligatory', false, false, false, false, false, false),
(default, 'Γραμμική Άλγεβρα', 6, 1, 'core', 'obligatory', false, false, false, false, false, false),

(default, 'Ανάλυση Ι', 8, 2, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Δομές Δεδομένων και Τεχνικές Προγραμματισμού', 7, 2, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Ηλεκτρομαγνητισμός, Οπτική και Σύγχρονη Φυσική', 8, 2, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Αρχιτεκτονική Υπολογιστών Ι', 7, 2, 'core', 'obligatory', false, false, false, false, false, false),

(default, 'Αντικειμενοστραφής Προγραμματισμός', 8, 3, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Ανάλυση ΙΙ', 8, 3, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Σήματα και Συστήματα', 6, 3, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Πιθανότητες και Στατιστική', 6, 3, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Εργαστήριο Κυκλωμάτων και Συστημάτων', 2, 3, 'optional_lab', 'optional', false, false, false, false, false, false),

(default, 'Σχεδίαση και Χρήση Βάσεων Δεδομένων', 7, 4, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Αλγόριθμοι και Πολυπλοκότητα', 8, 4, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Συστήματα Επικοινωνιών', 7, 4, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Δίκτυα Επικοινωνιών Ι', 6, 4, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Εργαστήριο Δικτύων Επικοινωνιών Ι', 2, 4, 'optional_lab', 'optional', false, false, false, false, false, false),

(default, 'Λειτουργικά Συστήματα', 6, 5, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Προγραμματισμός Συστήματος', 6, 6, 'core', 'obligatory', false, false, false, false, false, false),

(default, 'Project', 8, 7, 'core', 'obligatory', false, false, false, false, false, false),

(default, 'Πτυχιακή ή Πρακτική Ι', 8, 7, 'core', 'obligatory', false, false, false, false, false, false),
(default, 'Πτυχιακή ή Πρακτική ΙΙ', 8, 8, 'core', 'obligatory', false, false, false, false, false, false),

(default, 'Θεωρία Υπολογισμού', 6, 5, 'A', 'obligatory', false, true, false, false, false, false),
(default, 'Αριθμητική Ανάλυση', 6, 5, 'A', 'obligatory', true, false, false, false, false, false),
(default, 'Υλοποίηση Συστημάτων Βάσεων Δεδομένων', 6, 5, 'A', 'obligatory', false, true, true, false, false, false),
(default, 'Μεταγλωττιστές', 6, 5, 'A', 'obligatory-by-choice', false, false, true, false, false, false),

(default, 'Αρχές Γλωσσών Προγραμματισμού', 6, 5, 'A', 'basic', true, true, false, false, false, false),
(default, 'Τεχνητή Νοημοσύνη', 6, 5, 'A', 'basic', false, true, true, false, false, false),
(default, 'Τεχνικές Εξόρυξης Δεδομένων', 6, 5, 'A', 'basic', false, true, false, false, false, false),
(default, 'Επικοινωνία Ανθρώπου Μηχανής', 6, 7, 'A', 'basic', false, true, true, false, false, false),

(default, 'Δομή και Θεσμοί της Ευρωπαικής Ένωσης', 2, 7, 'general_education', 'obligatory', false, false, false, false, false, false),
(default, 'Διοίκηση Έργων', 2, 8, 'general_education', 'obligatory', false, false, false, false, false, false),

(default, 'Ανάλυση και Σχεδίαση Συστημάτων Λογισμικού', 6, 6, 'A', 'basic', false, false, true, false, false, false),
(default, 'Τεχνολογίες Εφαρμογών Διαδικτύου', 6, 8, 'A', 'basic', false, false, true, false, false, false),
(default, 'Παράλληλα Συστήματα', 6, 5, 'A', 'basic', false, false, true, true, false, false),
(default, 'Προστασία και Ασφάλεια Υπολογιστικών Συστημάτων', 6, 8, 'A', 'basic', false, false, true, false, false, false),
(default, 'Τεχνολογία Λογισμικού', 6, 8, 'A', 'basic', false, false, true, false, false, false),

(default, 'Γραφικά Ι', 6, 5, 'A', 'basic', true, false, false, false, false, false),
(default, 'Θεωρία Παιγνίων', 7, 5, 'free', 'optional', false, false, false, false, false, false);
