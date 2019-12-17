-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Conteudo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Conteudo` (
  `nome_Conteudo` VARCHAR(64) NOT NULL,
  `path_Conteudo` VARCHAR(64) NOT NULL,
  `artista_Conteudo` VARCHAR(64) NOT NULL,
  `categoria_Conteudo` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`nome_Conteudo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Administrador` (
  `nome_Administrador` VARCHAR(64) NOT NULL,
  `email_Administrador` VARCHAR(64) NOT NULL,
  `password_Administrador` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`nome_Administrador`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Comum`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Comum` (
  `nome_Comum` VARCHAR(64) NOT NULL,
  `email_Comum` VARCHAR(64) NOT NULL,
  `password_Comum` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`nome_Comum`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`MyConteudo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`MyConteudo` (
  `nome_Comum` VARCHAR(64) NOT NULL,
  `nome_MyConteudo` VARCHAR(64) NOT NULL,
  `categoria_MyConteudo` VARCHAR(64) NOT NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Amigo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Amigo` (
  `nome_Comum` VARCHAR(64) NOT NULL,
  `nome_Amigo` VARCHAR(64) NOT NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PotAmigo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PotAmigo` (
  `nome_Comum` VARCHAR(64) NOT NULL,
  `nome_PotAmigo` VARCHAR(64) NOT NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Playlist` (
  `nome_Playlist` VARCHAR(64) NOT NULL,
  `nome_Comum` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`nome_Playlist`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ConteudoPlaylist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ConteudoPlaylist` (
  `nome_Comum` VARCHAR(64) NOT NULL,
  `nome_Playlist` VARCHAR(64) NOT NULL,
  `nome_ContPlay` VARCHAR(64) NOT NULL,
  `categoria_ContPlay` VARCHAR(64) NOT NULL)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
