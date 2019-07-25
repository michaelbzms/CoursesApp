DELETE FROM `CoursesAppDB`.`Students_has_Courses`;
DELETE FROM `CoursesAppDB`.`Courses`;

INSERT INTO `CoursesAppDB`.`Courses` (`idCourses`, `title`, `ects`, `semester`, `path`, `type`, `specificpath`) VALUES 
(default, 'Εισαγωγή στον Προγραμματισμό', 7, 1, 'core', 'obligatory', NULL),
(default, 'Διακριτά Μαθηματικά', 7, 1, 'core', 'obligatory', NULL),
(default, 'Λογική Σχεδίαση', 6, 1, 'core', 'obligatory', NULL),
(default, 'Εργαστήριο Λογικής Σχεδίασης', 2, 1, 'core', 'obligatory-by-choice', NULL),
(default, 'Εισαγωγή στην Πληροφορική και τις Τηλεπικοινωνίες', 2, 1, 'core', 'obligatory', NULL),
(default, 'Γραμμική Άλγεβρα', 6, 1, 'core', 'obligatory', NULL),

(default, 'Ανάλυση Ι', 8, 2, 'core', 'obligatory', NULL),
(default, 'Δομές Δεδομένων και Τεχνικές Προγραμματισμού', 7, 2, 'core', 'obligatory', NULL),
(default, 'Ηλεκτρομαγνητισμός, Οπτική και Σύγχρονη Φυσική', 8, 2, 'core', 'obligatory', NULL),
(default, 'Αρχιτεκτονική Υπολογιστών Ι', 7, 2, 'core', 'obligatory', NULL),

(default, 'Αντικειμενοστραφής Προγραμματισμός', 8, 3, 'core', 'obligatory', NULL),
(default, 'Ανάλυση ΙΙ', 8, 3, 'core', 'obligatory', NULL),
(default, 'Σήματα και Συστήματα', 6, 3, 'core', 'obligatory', NULL),
(default, 'Πιθανότητες και Στατιστική', 6, 3, 'core', 'obligatory', NULL),
(default, 'Εργαστήριο Κυκλωμάτων και Συστημάτων', 2, 3, 'core', 'obligatory-by-choice', NULL),

(default, 'Σχεδίαση και Χρήση Βάσεων Δεδομένων', 7, 4, 'core', 'obligatory', NULL),
(default, 'Αλγόριθμοι και Πολυπλοκότητα', 8, 4, 'core', 'obligatory', NULL),
(default, 'Συστήματα Επικοινωνιών', 7, 4, 'core', 'obligatory', NULL),
(default, 'Δίκτυα Επικοινωνιών Ι', 6, 4, 'core', 'obligatory', NULL),
(default, 'Εργαστήριο Δικτύων Επικοινωνιών Ι', 2, 4, 'core', 'obligatory-by-choice', NULL),

(default, 'Λειτουργικά Συστήματα', 6, 5, 'core', 'obligatory', NULL),
(default, 'Προγραμματισμός Συστήματος', 6, 6, 'core', 'obligatory', NULL),

(default, 'Project', 8, 7, 'core', 'obligatory', NULL),

(default, 'Πτυχιακή ή Πρακτική Ι', 8, 7, 'core', 'obligatory', NULL),
(default, 'Πτυχιακή ή Πρακτική ΙΙ', 8, 8, 'core', 'obligatory', NULL),

(default, 'Θεωρία Υπολογισμού', 6, 5, 'A', 'obligatory-by-choice', NULL),
(default, 'Αριθμητική Ανάλυση', 6, 5, 'A', 'obligatory-by-choice', NULL),
(default, 'Υλοποίηση Συστημάτων Βάσεων Δεδομένων', 6, 5, 'A', 'obligatory-by-choice', NULL),
(default, 'Μεταγλωττιστές', 6, 5, 'A', 'obligatory-by-choice', NULL),

(default, 'Αρχές Γλωσσών Προγραμματισμού', 6, 5, 'A', 'basic', NULL),
(default, 'Τεχνητή Νοημοσύνη', 6, 5, 'A', 'basic', NULL),
(default, 'Τεχνικές Εξόρυξης Δεδομένων', 6, 5, 'A', 'basic', NULL),
(default, 'Επικοινωνία Ανθρώπου Μηχανής', 6, 7, 'A', 'basic', NULL),

(default, 'Δομή και Θεσμοί της Ευρωπαικής Ένωσης', 2, 7, 'core', 'obligatory', NULL),
(default, 'Διοίκηση Έργων', 2, 8, 'core', 'obligatory', NULL),

(default, 'Ανάλυση και Σχεδίαση Συστημάτων Λογισμικού', 6, 6, 'A', 'basic', NULL),
(default, 'Τεχνολογίες Εφαρμογών Διαδικτύου', 6, 8, 'A', 'basic', NULL),
(default, 'Παράλληλα Συστήματα', 6, 5, 'A', 'basic', NULL),
(default, 'Προστασία και Ασφάλεια Υπολογιστικών Συστημάτων', 6, 8, 'A', 'basic', NULL),
(default, 'Τεχνολογία Λογισμικού', 6, 8, 'A', 'basic', 'E3'),

(default, 'Γραφικά Ι', 6, 5, 'A', 'basic', NULL),
(default, 'Θεωρία Παιγνίων', 7, 5, 'core', 'basic', NULL);
