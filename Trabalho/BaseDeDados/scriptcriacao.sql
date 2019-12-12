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
  `nome` VARCHAR(64) NOT NULL,
  `codigo` VARBINARY(64) NOT NULL,
  `duracao` TIME NOT NULL,
  `categoria` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Administrador` (
  `nome` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Playlist` (
  `Conteudo_nome` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`Conteudo_nome`),
  CONSTRAINT `fk_Playlist_Conteudo1`
    FOREIGN KEY (`Conteudo_nome`)
    REFERENCES `mydb`.`Conteudo` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Comum`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Comum` (
  `nome` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `Conteudo_nome` VARCHAR(64) NOT NULL,
  `Comum_nome` VARCHAR(64) NOT NULL,
  `Comum_Conteudo_nome` VARCHAR(64) NOT NULL,
  `Playlist_Conteudo_nome` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`nome`, `Conteudo_nome`, `Comum_nome`, `Comum_Conteudo_nome`, `Playlist_Conteudo_nome`),
  INDEX `fk_Comum_Conteudo_idx` (`Conteudo_nome` ASC) VISIBLE,
  INDEX `fk_Comum_Comum1_idx` (`Comum_nome` ASC, `Comum_Conteudo_nome` ASC) VISIBLE,
  INDEX `fk_Comum_Playlist1_idx` (`Playlist_Conteudo_nome` ASC) VISIBLE,
  CONSTRAINT `fk_Comum_Conteudo`
    FOREIGN KEY (`Conteudo_nome`)
    REFERENCES `mydb`.`Conteudo` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comum_Comum1`
    FOREIGN KEY (`Comum_nome` , `Comum_Conteudo_nome`)
    REFERENCES `mydb`.`Comum` (`nome` , `Conteudo_nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comum_Playlist1`
    FOREIGN KEY (`Playlist_Conteudo_nome`)
    REFERENCES `mydb`.`Playlist` (`Conteudo_nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
