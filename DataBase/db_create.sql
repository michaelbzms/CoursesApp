-- MySQL Script generated by MySQL Workbench
-- Mon Jul 29 20:52:26 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CoursesAppDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `CoursesAppDB` ;

-- -----------------------------------------------------
-- Schema CoursesAppDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CoursesAppDB` DEFAULT CHARACTER SET utf8 ;
USE `CoursesAppDB` ;

-- -----------------------------------------------------
-- Table `CoursesAppDB`.`Courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CoursesAppDB`.`Courses` ;

CREATE TABLE IF NOT EXISTS `CoursesAppDB`.`Courses` (
  `idCourses` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(128) NOT NULL,
  `ects` INT NOT NULL,
  `semester` INT NOT NULL,
  `category` ENUM('core', 'A', 'B', 'optional_lab', 'free', 'general_education') NOT NULL,
  `type` ENUM('obligatory', 'obligatory-by-choice', 'basic', 'optional') NOT NULL,
  `E1` TINYINT NOT NULL DEFAULT 0,
  `E2` TINYINT NOT NULL DEFAULT 0,
  `E3` TINYINT NOT NULL DEFAULT 0,
  `E4` TINYINT NOT NULL DEFAULT 0,
  `E5` TINYINT NOT NULL DEFAULT 0,
  `E6` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idCourses`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CoursesAppDB`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CoursesAppDB`.`Users` ;

CREATE TABLE IF NOT EXISTS `CoursesAppDB`.`Users` (
  `idUsers` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(128) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `isAdmin` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idUsers`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `CoursesAppDB`.`Users` (`email` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `CoursesAppDB`.`Students`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CoursesAppDB`.`Students` ;

CREATE TABLE IF NOT EXISTS `CoursesAppDB`.`Students` (
  `idStudents` INT NOT NULL,
  `firstname` VARCHAR(128) NOT NULL,
  `lastname` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`idStudents`),
  CONSTRAINT `fk_Students_Users1`
    FOREIGN KEY (`idStudents`)
    REFERENCES `CoursesAppDB`.`Users` (`idUsers`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Students_Users1_idx` ON `CoursesAppDB`.`Students` (`idStudents` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `CoursesAppDB`.`Students_has_Courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `CoursesAppDB`.`Students_has_Courses` ;

CREATE TABLE IF NOT EXISTS `CoursesAppDB`.`Students_has_Courses` (
  `idStudents` INT NOT NULL,
  `idCourses` INT NOT NULL,
  `grade` DOUBLE NULL,
  PRIMARY KEY (`idStudents`, `idCourses`),
  CONSTRAINT `fk_Students_has_Courses_Students1`
    FOREIGN KEY (`idStudents`)
    REFERENCES `CoursesAppDB`.`Students` (`idStudents`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Students_has_Courses_Courses1`
    FOREIGN KEY (`idCourses`)
    REFERENCES `CoursesAppDB`.`Courses` (`idCourses`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Students_has_Courses_Courses1_idx` ON `CoursesAppDB`.`Students_has_Courses` (`idCourses` ASC) VISIBLE;

CREATE INDEX `fk_Students_has_Courses_Students1_idx` ON `CoursesAppDB`.`Students_has_Courses` (`idStudents` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
