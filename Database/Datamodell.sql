SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`User` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(45) NOT NULL ,
  `Email` VARCHAR(45) NOT NULL ,
  `HashedPassword` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Group`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Group` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`GroupGroup`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`GroupGroup` (
  `MasterID` INT NOT NULL ,
  `SlaveID` INT NOT NULL ,
  INDEX `fk_Group_has_Group_Group1_idx` (`SlaveID` ASC) ,
  INDEX `fk_Group_has_Group_Group_idx` (`MasterID` ASC) ,
  PRIMARY KEY (`SlaveID`, `MasterID`) ,
  CONSTRAINT `fk_Group_has_Group_Group`
    FOREIGN KEY (`MasterID` )
    REFERENCES `mydb`.`Group` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_Group_Group1`
    FOREIGN KEY (`SlaveID` )
    REFERENCES `mydb`.`Group` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`GroupUser`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`GroupUser` (
  `GroupID` INT NOT NULL ,
  `UserID` INT NOT NULL ,
  INDEX `fk_Group_has_User_User1_idx` (`UserID` ASC) ,
  INDEX `fk_Group_has_User_Group1_idx` (`GroupID` ASC) ,
  PRIMARY KEY (`GroupID`, `UserID`) ,
  CONSTRAINT `fk_Group_has_User_Group1`
    FOREIGN KEY (`GroupID` )
    REFERENCES `mydb`.`Group` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_User_User1`
    FOREIGN KEY (`UserID` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Appointment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Appointment` (
  `ID` INT NOT NULL ,
  `CreatorUserID` INT NOT NULL ,
  `Title` VARCHAR(45) NOT NULL ,
  `Start` DATETIME NOT NULL ,
  `End` DATETIME NOT NULL ,
  `Description` VARCHAR(255) NOT NULL ,
  `IsMeeting` TINYINT(1) NOT NULL DEFAULT False ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`MeetingPoint`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`MeetingPoint` (
  `ID` INT NOT NULL ,
  `Name` VARCHAR(45) NULL ,
  `Capacity` INT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`GroupAppointment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`GroupAppointment` (
  `GroupID` INT NOT NULL ,
  `AppointmentID` INT NOT NULL ,
  INDEX `fk_Group_has_Appointment_Appointment1_idx` (`AppointmentID` ASC) ,
  INDEX `fk_Group_has_Appointment_Group1_idx` (`GroupID` ASC) ,
  PRIMARY KEY (`GroupID`, `AppointmentID`) ,
  CONSTRAINT `fk_Group_has_Appointment_Group1`
    FOREIGN KEY (`GroupID` )
    REFERENCES `mydb`.`Group` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_Appointment_Appointment1`
    FOREIGN KEY (`AppointmentID` )
    REFERENCES `mydb`.`Appointment` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`UserAppointment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`UserAppointment` (
  `UserID` INT NOT NULL ,
  `AppointmentID` INT NOT NULL ,
  `IsGoing` TINYINT(1) NULL DEFAULT NULL ,
  `alarmTime` DATETIME NULL DEFAULT NULL ,
  INDEX `fk_User_has_Appointment_Appointment1_idx` (`AppointmentID` ASC) ,
  INDEX `fk_User_has_Appointment_User1_idx` (`UserID` ASC) ,
  PRIMARY KEY (`AppointmentID`, `UserID`) ,
  CONSTRAINT `fk_User_has_Appointment_User1`
    FOREIGN KEY (`UserID` )
    REFERENCES `mydb`.`User` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Appointment_Appointment1`
    FOREIGN KEY (`AppointmentID` )
    REFERENCES `mydb`.`Appointment` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`AppointmentMeetingPoint`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`AppointmentMeetingPoint` (
  `AppointmentID` INT NOT NULL ,
  `MeetingPointID` INT NULL ,
  PRIMARY KEY (`MeetingPointID`, `AppointmentID`) ,
  INDEX `fk_Appointment_has_MeetingPoint_MeetingPoint1_idx` (`MeetingPointID` ASC) ,
  INDEX `fk_Appointment_has_MeetingPoint_Appointment1_idx` (`AppointmentID` ASC) ,
  CONSTRAINT `fk_Appointment_has_MeetingPoint_Appointment1`
    FOREIGN KEY (`AppointmentID` )
    REFERENCES `mydb`.`Appointment` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_has_MeetingPoint_MeetingPoint1`
    FOREIGN KEY (`MeetingPointID` )
    REFERENCES `mydb`.`MeetingPoint` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mydb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
