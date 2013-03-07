SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `sids` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `sids` ;

-- -----------------------------------------------------
-- Table `sids`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `hashedPassword` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`group`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`group` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`group_group`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`group_group` (
  `masterId` INT NOT NULL ,
  `slaveId` INT NOT NULL ,
  INDEX `fk_Group_has_Group_Group1_idx` (`slaveId` ASC) ,
  INDEX `fk_Group_has_Group_Group_idx` (`masterId` ASC) ,
  PRIMARY KEY (`slaveId`, `masterId`) ,
  CONSTRAINT `fk_Group_has_Group_Group`
    FOREIGN KEY (`masterId` )
    REFERENCES `sids`.`group` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_Group_Group1`
    FOREIGN KEY (`slaveId` )
    REFERENCES `sids`.`group` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`group_user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`group_user` (
  `groupId` INT NOT NULL ,
  `userId` INT NOT NULL ,
  INDEX `fk_Group_has_User_User1_idx` (`userId` ASC) ,
  INDEX `fk_Group_has_User_Group1_idx` (`groupId` ASC) ,
  PRIMARY KEY (`groupId`, `userId`) ,
  CONSTRAINT `fk_Group_has_User_Group1`
    FOREIGN KEY (`groupId` )
    REFERENCES `sids`.`group` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_User_User1`
    FOREIGN KEY (`userId` )
    REFERENCES `sids`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`appointment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`appointment` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `creatorUserId` INT NOT NULL ,
  `title` VARCHAR(45) NOT NULL ,
  `start` DATETIME NOT NULL ,
  `end` DATETIME NOT NULL ,
  `description` VARCHAR(255) NOT NULL ,
  `isMeeting` TINYINT(1) NOT NULL DEFAULT False ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`meetingpoint`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`meetingpoint` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  `capacity` INT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`group_appointment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`group_appointment` (
  `groupId` INT NOT NULL ,
  `appointmentId` INT NOT NULL ,
  INDEX `fk_Group_has_Appointment_Appointment1_idx` (`appointmentId` ASC) ,
  INDEX `fk_Group_has_Appointment_Group1_idx` (`groupId` ASC) ,
  PRIMARY KEY (`groupId`, `appointmentId`) ,
  CONSTRAINT `fk_Group_has_Appointment_Group1`
    FOREIGN KEY (`groupId` )
    REFERENCES `sids`.`group` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_has_Appointment_Appointment1`
    FOREIGN KEY (`appointmentId` )
    REFERENCES `sids`.`appointment` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`user_appointment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`user_appointment` (
  `userId` INT NOT NULL ,
  `appointmentId` INT NOT NULL ,
  `isGoing` TINYINT(1) NULL DEFAULT NULL ,
  `alarmTime` DATETIME NULL ,
  INDEX `fk_User_has_Appointment_Appointment1_idx` (`appointmentId` ASC) ,
  INDEX `fk_User_has_Appointment_User1_idx` (`userId` ASC) ,
  PRIMARY KEY (`appointmentId`, `userId`) ,
  CONSTRAINT `fk_User_has_Appointment_User1`
    FOREIGN KEY (`userId` )
    REFERENCES `sids`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Appointment_Appointment1`
    FOREIGN KEY (`appointmentId` )
    REFERENCES `sids`.`appointment` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sids`.`appointment_meetingpoint`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `sids`.`appointment_meetingpoint` (
  `appointmentId` INT NOT NULL ,
  `meetingpointId` INT NULL ,
  PRIMARY KEY (`meetingpointId`, `appointmentId`) ,
  INDEX `fk_Appointment_has_MeetingPoint_MeetingPoint1_idx` (`meetingpointId` ASC) ,
  INDEX `fk_Appointment_has_MeetingPoint_Appointment1_idx` (`appointmentId` ASC) ,
  CONSTRAINT `fk_Appointment_has_MeetingPoint_Appointment1`
    FOREIGN KEY (`appointmentId` )
    REFERENCES `sids`.`appointment` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointment_has_MeetingPoint_MeetingPoint1`
    FOREIGN KEY (`meetingpointId` )
    REFERENCES `sids`.`meetingpoint` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `sids` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
